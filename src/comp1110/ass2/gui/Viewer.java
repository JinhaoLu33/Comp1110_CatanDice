package comp1110.ass2.gui;

import comp1110.ass2.CatanDice;
import comp1110.ass2.Resource;
import comp1110.ass2.Structure;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Viewer {
    /**
     * @author: Hamish Hunter, Jinhao Lu
     * Contains an overwhelming majority of the GUI and game sequence backend.
     */

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";
    private static final String RESOURCE_FILE = "assets/Resources/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group resources = new Group();
    private final Group scores = new Group();
    private final Group finalScores = new Group();
    private final Group map = new Group();//background
    private final Group structures = new Group();//structures
    private TextField boardTextField;

    private final Group roll = new Group();
    private final Group DiceButtons = new Group();


    static private boolean gameStart = true;
    static private boolean roll1 = true;
    static private boolean roll2_3 = false;
    static private boolean roll3 = false;

    private int counter = 0;
    private int Turns = 1;
    private int remainingRolls = 3;
    private boolean finish = true;
    private boolean[] diceState = {true, true, true, true, true, true};
    private int[] diceMatchResource = {0, 0, 0, 0, 0, 0};
    private int[] score = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    //Number to keep count of the joker number
    private int jokerCounter = 1;

    /*Adds a visual counter for how many rolls are remaining in the turn
     * Initialised as a class member to allow for altering across methods
     */
    private Label rollsRemaining = new Label("Rolls Remaining: " + (remainingRolls - counter));

    /*Adds a visual counter for how many turns are remaining in the game
     * Initialised as a class member to allow for altering across methods
     */
    private Label turnsRemaining = new Label("Turns Remaining: " + (16 - Turns));

    private boolean buildTime = false;

    //Button to roll the selected dice
    private Button Roll = new Button("Roll");

    //Button to end the dice roll phase
    private Button finishRoll = new Button("Finish Roll");

    //Cover over the dice icons after the rolling phase is complete
    private ImageView diceCover;


    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param board_state The string representation of the board state.
     */
    void displayState(String board_state) {
        // FIXME Task 5: implement the state viewer
        structures.getChildren().clear();//reset the structures group
        if (CatanDice.isBoardStateWellFormed(board_state)) {
            String[] states = board_state.split(",");
            for (var s : states) {
                Image structure = new Image(Game.class.getResource(URI_BASE + s + ".png").toString());//use s to call the png file
                ImageView viewStructure = new ImageView(structure);
                viewStructure.setLayoutY(30);
                structures.getChildren().add(viewStructure);
            }
        }
    }

    /**
     * build the road by typing the number and hit road button.
     * update boardState and resource
     *
     * @param roadNum The string representation of the road number.
     * @param board   Using a board object to manipulate the boardState and resourceState
     */
    void buildRoad(String roadNum, comp1110.ass2.Game board) {
        //check buildTime and current turn
        if (buildTime && Turns < 16) {
            //check build constraint
            if (CatanDice.canDoAction("build " + "R" + roadNum, board.boardState, board.playerResource.resource)) {
                //backend update
                board.updateBoardStateString("R" + roadNum); //update boardState
                board.playerResource.subtract(new Resource(Structure.structureType.ROAD)); //subtract the resource after build
                //build gui
                Image structure = new Image(Objects.requireNonNull(Game.class.getResource(URI_BASE + "R" + roadNum + ".png")).toString());//use R+roadNum to call the png file
                ImageView viewStructure = new ImageView(structure); //add corresponded image to imageView
                viewStructure.setLayoutY(30); //leave some blank space at top
                structures.getChildren().add(viewStructure);
                showResources(board.playerResource.resource); //update resource in the viewer
                score[Turns] += 1; //update score for current turn
            }
        }
    }

    /**
     * build the structures by hitting the corresponding button.
     * update boardState and resource
     *
     * @param sType The string representation of the road number. Will be filled by hitting the button
     * @param board Using a Game object to manipulate the boardState and resourceState
     */
    void buildStructure(String sType, comp1110.ass2.Game board) {
        //check buildTime and current turn
        if (buildTime && Turns < 16) {
            switch (sType) {
                //build settlement
                case "S" -> {
                    for (int i = 0; i < 12; i++) {
                        String newBoardState = sType + i; //generate a settlement String
                        //check build constraint
                        if (CatanDice.canDoAction("build " + "S" + i, board.boardState, board.playerResource.resource)
                        ) {
                            //backend update
                            board.updateBoardStateString(newBoardState); //update boardState
                            board.playerResource.subtract(new Resource(Structure.structureTypeFromString(sType))); //subtract the resource after build
                            //build gui
                            Image structure = new Image(Game.class.getResource(URI_BASE + newBoardState + ".png").toString());//use s to call the png file
                            ImageView viewStructure = new ImageView(structure);
                            viewStructure.setLayoutY(30);
                            structures.getChildren().add(viewStructure);
                            showResources(board.playerResource.resource);
                            score[Turns] += i;
                            return;
                        } else
                            System.out.println("Cannot Build Settlement!");
                    }
                }
                //build city
                case "C" -> {
                    for (int i = 0; i < 31; i++) {
                        String newBoardState = sType + i; //generate a city String\
                        //check build constraint
                        if (CatanDice.canDoAction("build " + "C" + i, board.boardState, board.playerResource.resource)) {
                            //backend update
                            board.updateBoardStateString(newBoardState);
                            board.playerResource.subtract(new Resource(Structure.structureTypeFromString(sType)));
                            //build gui
                            Image structure = new Image(Game.class.getResource(URI_BASE + newBoardState + ".png").toString());//use s to call the png file
                            ImageView viewStructure = new ImageView(structure);
                            viewStructure.setLayoutY(30);
                            structures.getChildren().add(viewStructure);
                            showResources(board.playerResource.resource);
                            score[Turns] += i;
                            return;
                        }

                    }
                }
                //build joker
                case "J" -> {
                    String newBoardState = "J" + jokerCounter; //generate a joker String
                    //check build constraint
                    if (CatanDice.isBoardStateWellFormed(newBoardState) && CatanDice.canDoAction("build " + "J" + jokerCounter, board.boardState, board.playerResource.resource)
                            && (!Arrays.asList(board.boardState.split(",")).contains(newBoardState) && !Arrays.asList(board.boardState.split(",")).contains("K" + jokerCounter))) {
                        //backend update
                        board.updateBoardStateString(newBoardState);
                        board.playerResource.subtract(new Resource(Structure.structureTypeFromString(sType)));
                        System.out.println(board.boardState);
                        //build gui
                        //Joker is green before the swap, once it is a knight, it turns red
                        Image structure = new Image(Game.class.getResource(URI_BASE + newBoardState + ".png").toString());//use s to call the png file
                        ImageView viewStructure = new ImageView(structure);
                        viewStructure.setLayoutY(30);
                        structures.getChildren().add(viewStructure);
                        showResources(board.playerResource.resource);
                        score[Turns] += jokerCounter;
                        jokerCounter++;
                    }
                }
            }
        }
    }

    /**
     * trade resource with 2 golds by hitting the button and select the needed resource
     *
     * @param num   needed resource 0->ore 1->grain 2->wool 3->timber 4->brick
     * @param board Using a Game object to manipulate the boardState and resourceState
     */
    void Trade(String num, comp1110.ass2.Game board) {
        if (CatanDice.canDoAction("trade " + num, board.boardState, board.playerResource.resource) && Turns < 16 && buildTime) {
            board.playerResource.addOne(Integer.parseInt(num), 1);
            board.playerResource.subtractOne(5, 2);
        }
    }

    /**
     * Swap resource 1 with resource 2 using the
     * designated joker
     *
     * @param num1  Index of the resource to be switched
     * @param num2  Index of the resource to be gained
     * @param board The game board
     */

    void Swap(String num1, String num2, comp1110.ass2.Game board) {
        //check swap constraint
        if (CatanDice.canDoAction("swap " + num1 + " " + num2, board.boardState, board.playerResource.resource) && Turns < 16 && buildTime) {
            board.playerResource.addOne(Integer.parseInt(num2), 1);
            board.playerResource.subtractOne(Integer.parseInt(num1), 1);
            System.out.println(num1 + num2);
            int jokerNum = Integer.parseInt(num2) + 1; // joker num is 1 greater than the resource num
            if (board.boardState.contains("J" + jokerNum)) {
                //update the boardState string
                board.SwapJokerString(jokerNum);
                //Turns the joker to a knight on the GUI
                Image structure = new Image(Game.class.getResource(URI_BASE + "K" + jokerNum + ".png").toString());//use s to call the png file
                ImageView viewStructure = new ImageView(structure);
                viewStructure.setLayoutY(30);
                structures.getChildren().add(viewStructure);
            } else {
                //update the boardState string
                board.SwapJokerString(6);
                //Turns the joker to a knight on the GUI
                Image structure = new Image(Game.class.getResource(URI_BASE + "K6" + ".png").toString());//use s to call the png file
                ImageView viewStructure = new ImageView(structure);
                viewStructure.setLayoutY(30);
                structures.getChildren().add(viewStructure);
            }
        }
    }


    /**
     * Initialize the map background
     */
    void catanBackground() {
        Image backgroundImage = new Image(Game.class.getResource(URI_BASE + "board.jpg").toString());
        ImageView background = new ImageView(backgroundImage);
        background.setLayoutY(30);
        this.map.getChildren().add(background);
    }

    /**
     * Create a basic text field for input and a refresh button.
     *
     * @param board The game board
     */
    private void makeControls(comp1110.ass2.Game board) {
        Label boardLabel = new Label("Build:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(50);
        Button Road = new Button("Road");
        Button Switch = new Button(" Switch");
        Button Settlement = new Button("Settlement");
        Button City = new Button("City");
        Button Joker = new Button("Joker");
        Button Finish = new Button("Finish");

        //set methods buildRoad and buildStructure to the buttons: Road, Settlement, City, Joker
        Road.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buildRoad(boardTextField.getText(), board);
            }
        });
        Settlement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buildStructure("S", board);
            }
        });
        City.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buildStructure("C", board);
            }
        });
        Joker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buildStructure("J", board);
            }
        });

        //Use a finish button to reset the resourceState, set roll counter to 3, update the score and move to the next turn
        Finish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //can only finish at buildTime
                if (buildTime) {
                    finish = true;
                    buildTime = false;
                    roll1 = true;
                    roll2_3 = false;
                    //update resourceState
                    board.playerResource.resetResource();
                    showResources(board.playerResource.resource);
                    Arrays.fill(diceState, true);
                    //if no actions, score -2
                    if (score[Turns] == 0) {
                        score[Turns] = -2;
                    }
                    //reset counter and update turn
                    counter = 0;
                    Turns += 1;
                    //Resets the roll button text
                    Roll.setText("Roll");
                    //Re adds the roll buttons
                    roll.getChildren().add(finishRoll);
                    roll.getChildren().add(Roll);
                    //Re-adds the roll counter
                    rollsRemaining.setText("Rolls Remaining: " + String.valueOf(remainingRolls - counter));
                    root.getChildren().add(rollsRemaining);
                    //Removes the dice cover
                    roll.getChildren().remove(diceCover);
                    //show scores at the end of each turn
                    showScores();
                    showFinalScores();
                }
            }
        });

        //add all the buttons to a vBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(boardLabel, Road, boardTextField, Settlement, City, Joker, Finish);
        vBox.setSpacing(10);
        vBox.setLayoutX(1290);
        vBox.setLayoutY(78);

        controls.getChildren().addAll(vBox);
    }


    /**
     * Creates a text field that represents the player's resources
     *
     * @param resource_state: int[] object holding the player's resources
     */
    public void showResources(int[] resource_state) {
        resources.getChildren().clear();
        root.getChildren().remove(resources);
        Label resourceLabel = new Label("Resources:");
        resourceLabel.setFont(new Font(20));
        //playerTextField = new TextField();

        VBox vb = new VBox();
        vb.getChildren().addAll(resourceLabel);
        vb.setSpacing(10);
        vb.setLayoutX(1400);
        vb.setLayoutY(660);

        //use a for loop to set each resource to the text
        for (int i = 0; i < resource_state.length; i++) {
            Text res = new Text(Resource.resourceType.values()[i].toString() + ": " + resource_state[i]);
            vb.getChildren().add(res);
            res.setFont(new Font(16));
        }

        resources.getChildren().add(vb);
        root.getChildren().add(resources);
    }

    /**
     * Show the scores for each turns in the background score area
     */
    public void showScores() {
        scores.getChildren().clear();
        root.getChildren().remove(scores);

        //create 15 labels for 15 turns
        Label[] labels = {new Label(String.valueOf(this.score[1])), new Label(String.valueOf(this.score[2])),
                new Label(String.valueOf(this.score[3])), new Label(String.valueOf(this.score[4])), new Label(String.valueOf(this.score[5])),
                new Label(String.valueOf(this.score[6])), new Label(String.valueOf(this.score[7])), new Label(String.valueOf(this.score[8])),
                new Label(String.valueOf(this.score[9])), new Label(String.valueOf(this.score[10])), new Label(String.valueOf(this.score[11])),
                new Label(String.valueOf(this.score[12])), new Label(String.valueOf(this.score[13])), new Label(String.valueOf(this.score[14])),
                new Label(String.valueOf(this.score[15]))};
        for (var l : labels) {
            l.setFont(new Font(35));
        }

        //calculate the position of each scores
        for (int i = 0; i < 15; i++) {
            if (i < 5) {
                labels[i].setLayoutX(983 + i * 52);
                labels[i].setLayoutY(82);
            } else if (i == 5) {
                labels[i].setLayoutX(983 + (i - 1) * 52);
                labels[i].setLayoutY(140);
            } else if (i < 11) {
                labels[i].setLayoutX(1191 - (i - 6) * 52);
                labels[i].setLayoutY(192);
            } else if (i == 11) {
                labels[i].setLayoutX(983);
                labels[i].setLayoutY(244);
            } else {
                labels[i].setLayoutX(983 + (i - 12) * 52);
                labels[i].setLayoutY(294);
            }
        }

        Label turnLabel = new Label();
        //stop at turn15
        if (Turns < 16) {
            turnLabel.setText("Turn" + Turns);
        } else {
            turnLabel.setText("END");
            roll.getChildren().clear();

        }
        turnLabel.setFont(Font.font(40));
        turnLabel.setLayoutX(1490);
        turnLabel.setLayoutY(200);

        scores.getChildren().addAll(labels);
        scores.getChildren().add(turnLabel);
        root.getChildren().add(scores);
    }

    /**
     * show the sum of scores in each turn
     */
    public void showFinalScores() {
        //remove the previous sum
        finalScores.getChildren().clear();
        root.getChildren().remove(finalScores);
        int finalScore = 0;
        for (int i = 1; i < score.length; i++) {
            finalScore += score[i];
        }
        Label lableF = new Label(String.valueOf(finalScore));
        lableF.setFont(new Font(35));
        HBox hbf = new HBox();
        hbf.setLayoutX(1170);
        hbf.setLayoutY(294);
        hbf.getChildren().add(lableF);
        //add the sum in current turn
        finalScores.getChildren().addAll(hbf);
        root.getChildren().add(finalScores);
    }

    /**
     * Performs the turn start sequence of initialising the player's resources
     * by performing three dice rolls and giving the player the option of what
     * resources to pick each time
     * Also handles most of the button control actions
     *
     * @param board: The game object itself that you pass on the resource
     *               values to.
     */
    public void turnStart(comp1110.ass2.Game board) {
        //Sets the position of the dice cover, which is just a chopped png of the board
        diceCover = new ImageView(new Image(Game.class.getResource(URI_BASE + "DiceCover.png").toString()));
        diceCover.setX(905);
        diceCover.setY(400);

        //Sets the location of the roll buttons
        Roll.setLayoutX(970);
        Roll.setLayoutY(550);

        finishRoll.setLayoutX(1020);
        finishRoll.setLayoutY(550);
        //Initialises and places the trade and swap buttons
        Button Trade = new Button("Trade");
        Trade.setLayoutX(1400);
        Trade.setLayoutY(550);
        ChoiceBox TradeBox = new ChoiceBox(FXCollections.observableArrayList("ORE", "GRAIN", "WOOL", "TIMBER", "BRICK"));
        TradeBox.setLayoutX(1450);
        TradeBox.setLayoutY(550);

        Button Swap = new Button("Swap");
        Swap.setLayoutX(1200);
        Swap.setLayoutY(550);
        ChoiceBox SwapBox1 = new ChoiceBox(FXCollections.observableArrayList("ORE", "GRAIN", "WOOL", "TIMBER", "BRICK", "GOLD"));
        SwapBox1.setLayoutX(1120);
        SwapBox1.setLayoutY(550);
        ChoiceBox SwapBox2 = new ChoiceBox(FXCollections.observableArrayList("ORE", "GRAIN", "WOOL", "TIMBER", "BRICK", "GOLD"));
        SwapBox2.setLayoutX(1250);
        SwapBox2.setLayoutY(550);

        //Writes the positions of the turns and rolls remaining
        rollsRemaining.setLayoutX(1200);
        rollsRemaining.setLayoutY(600);

        roll.getChildren().addAll(Roll, finishRoll, Trade, Swap, TradeBox, SwapBox1, SwapBox2);
        root.getChildren().add(rollsRemaining);

        //Int array holding what value of each resource is currently present

//      board.playerResource.resetResource();
        //String array to represent the random dice roll;
        String[] randSelection = new String[]{"Ore.png", "Grain.png", "Wool.png", "Timber.png", "Bricks.png", "Gold.png"};
        //Initial ordering and setup of resources
        ImageView[] resourcesInitial = new ImageView[6];
        resourcesInitial[0] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + "Ore.png").toString()));
        resourcesInitial[1] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + "Grain.png").toString()));
        resourcesInitial[2] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + "Wool.png").toString()));
        resourcesInitial[3] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + "Timber.png").toString()));
        resourcesInitial[4] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + "Bricks.png").toString()));
        resourcesInitial[5] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + "Gold.png").toString()));

        for (int i = 0; i < resourcesInitial.length; i++) {
            resourcesInitial[i].setX(950 + i * 120);
            resourcesInitial[i].setY(400);
            roll.getChildren().add(resourcesInitial[i]);
        }

        System.out.println("In roll 1");
        //First Dice roll
        ImageView[] resourcesFirstRoll = new ImageView[6];

        System.out.println("exiting roll 1");

        System.out.println("In second roll");
        boolean diceChoices[] = {false, false, false, false, false, false};
        //Now lets the player choose which dice they wish to roll
        Label rollPick = new Label("Select Dice to roll");
        rollPick.setFont(new Font(20));
        rollPick.setLayoutX(1200);
        rollPick.setLayoutY(600);

        //Produces a button on top of each resource icon
        Button diceButtons[] = new Button[6];

        for (int i = 0; i < 6; i++) {
            diceButtons[i] = new Button("");
            diceButtons[i].setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 100px; " +
                            "-fx-min-height: 100px; " +
                            "-fx-max-width: 100px; " +
                            "-fx-max-height: 100px;" +
                            "-fx-border-color: transparent"
            );
            diceButtons[i].setOpacity(0);
            diceButtons[i].setLayoutX((950 + i * 120));
            diceButtons[i].setLayoutY(400);
            DiceButtons.getChildren().add(diceButtons[i]);
        }
        /*
         * Event handling of the dice buttons. Can be turned on or off
         * and should do nothing on the first roll or during the build time.
         */
        diceButtons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (roll2_3) {
                    if (!diceState[0]) {
                        diceState[0] = true;
                        System.out.println(true);

                        diceButtons[0].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #ff0000;"
                        );
                        diceButtons[0].setOpacity(0.3);

                        board.playerResource.subtractOne(diceMatchResource[0], 1);
                    } else {
                        diceState[0] = false;
                        diceButtons[0].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[0].setOpacity(0);
                        board.playerResource.addOne(diceMatchResource[0], 1);
                    }
                    Roll.setText("Roll");
                }
            }
        });

        diceButtons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (roll2_3) {
                    if (!diceState[1]) {
                        diceState[1] = true;
                        diceButtons[1].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #ff0000;"
                        );

                        diceButtons[1].setOpacity(0.3);
                        board.playerResource.subtractOne(diceMatchResource[1], 1);

                    } else {
                        diceState[1] = false;
                        diceButtons[1].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[1].setOpacity(0);
                        board.playerResource.addOne(diceMatchResource[1], 1);
                    }
                    Roll.setText("Roll");
                }
            }
        });

        diceButtons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (roll2_3) {
                    if (!diceState[2]) {
                        diceState[2] = true;

                        diceButtons[2].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #ff0000;"
                        );
                        diceButtons[2].setOpacity(0.3);
                        board.playerResource.subtractOne(diceMatchResource[2], 1);
                    } else {
                        diceState[2] = false;
                        diceButtons[2].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[2].setOpacity(0);
                        board.playerResource.addOne(diceMatchResource[2], 1);
                    }
                    Roll.setText("Roll");
                }
            }
        });

        diceButtons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (roll2_3) {
                    if (!diceState[3]) {
                        diceState[3] = true;

                        diceButtons[3].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #ff0000;"
                        );

                        diceButtons[3].setOpacity(0.3);
                        board.playerResource.subtractOne(diceMatchResource[3], 1);
                    } else {
                        diceState[3] = false;
                        diceButtons[3].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[3].setOpacity(0);
                        board.playerResource.addOne(diceMatchResource[3], 1);
                    }
                }
                Roll.setText("Roll");
            }
        });

        diceButtons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (roll2_3) {
                    if (!diceState[4]) {
                        diceState[4] = true;

                        diceButtons[4].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #ff0000;"
                        );
                        diceButtons[4].setOpacity(0.3);
                        board.playerResource.subtractOne(diceMatchResource[4], 1);
                    } else {
                        diceState[4] = false;
                        diceButtons[4].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[4].setOpacity(0);
                        board.playerResource.addOne(diceMatchResource[4], 1);
                    }
                }
                Roll.setText("Roll");
            }
        });

        diceButtons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (roll2_3) {
                    if (!diceState[5]) {
                        diceState[5] = true;

                        diceButtons[5].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #ff0000;"
                        );

                        diceButtons[5].setOpacity(0.3);
                        board.playerResource.subtractOne(diceMatchResource[5], 1);
                        System.out.println(diceMatchResource[5]);
                    } else {
                        diceState[5] = false;
                        diceButtons[5].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[5].setOpacity(0);
                        board.playerResource.addOne(diceMatchResource[5], 1);
                    }
                }
                Roll.setText("Roll");
            }
        });
        /*
         * Event handling regarding the roll and finish roll button
         */
        Roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (counter < 3 && finish && Turns < 16) {
                    Random rand = new Random();
                    //Resets the resource values to 0
//                    for (int i = 0; i < 6; i++)
//                        resource_state[i] = 0;
//                    board.playerResource.resetResource();
                    for (int i = 0; i < 6; i++) {
                        if (diceState[i]) {
                            int face = rand.nextInt(6);
                            diceMatchResource[i] = face;
                            //Increase the one rolled
                            board.playerResource.addOne(face, 1);
                            resourcesFirstRoll[i] = new ImageView(new Image(Game.class.getResource(RESOURCE_FILE + randSelection[face]).toString()));
                            resourcesFirstRoll[i].setX(950 + i * 120);
                            resourcesFirstRoll[i].setY(400);

                            roll.getChildren().remove(resourcesInitial[i]);
                            roll.getChildren().add(resourcesFirstRoll[i]);
                        }

                        diceState[i] = false;
                        diceButtons[i].setOpacity(0);
                    }
                    //Sets the button text to skip after the first roll
                    Roll.setText("Skip");
                    showResources(board.playerResource.resource);
                    roll1 = false;
                    roll2_3 = true;
                    System.out.println(roll2_3);
                    counter += 1;
                    //Updates the roll counter;
                    rollsRemaining.setText("Rolls Remaining: " + (3 - counter));
                    root.getChildren().remove(rollsRemaining);
                    root.getChildren().add(rollsRemaining);

                    if (counter == 3) {
                        //Hides the dice till the next roll
                        roll.getChildren().add(diceCover);
                        //Safeguard to prevent null-pointer exception
                        if (!buildTime) {
                            roll.getChildren().remove(finishRoll);
                            roll.getChildren().remove(Roll);
                            root.getChildren().remove(rollsRemaining);
                        }
                        Arrays.fill(diceState, true);
                        buildTime = true;
                        roll2_3 = false;
                    }
                } else {
                    counter = 0;
                    finish = false;
                }

            }
        });

        finishRoll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Hides the dice till the next roll
                roll.getChildren().add(diceCover);

                /*Hides the roll buttons whilst it is build time
                The if statement makes sure we don't attempt to remove the buttons
                when they are already removed*/

                if (!buildTime && Turns < 16) {
                    roll.getChildren().remove(finishRoll);
                    roll.getChildren().remove(Roll);
                    root.getChildren().remove(rollsRemaining);
                    roll2_3 = false;
                    buildTime = true;
                    //Resets all the buttons to blank
                    for (int i = 0; i < 6; i++) {
                        diceState[i] = false;
                        diceButtons[i].setStyle("-fx-background-radius: 5em; " +
                                "-fx-min-width: 100px; " +
                                "-fx-min-height: 100px; " +
                                "-fx-max-width: 100px; " +
                                "-fx-max-height: 100px;" +
                                "-fx-background-color: #000000;"
                        );
                        diceButtons[i].setOpacity(0);
                    }
                }
            }
        });

        //set action to Trade and Swap buttons
        Trade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String tradeString = (String) TradeBox.getValue();
                String tradeNum = null;
                switch (tradeString) {
                    case "ORE" -> tradeNum = "0";
                    case "GRAIN" -> tradeNum = "1";
                    case "WOOL" -> tradeNum = "2";
                    case "TIMBER" -> tradeNum = "3";
                    case "BRICK" -> tradeNum = "4";
                }
                Trade(tradeNum, board);
                showResources(board.playerResource.resource);
            }
        });

        Swap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String swap1 = (String) SwapBox1.getValue();
                String swap2 = (String) SwapBox2.getValue();
                String swapNum1 = null;
                String swapNum2 = null;

                switch (swap1) {
                    case "ORE" -> swapNum1 = "0";
                    case "GRAIN" -> swapNum1 = "1";
                    case "WOOL" -> swapNum1 = "2";
                    case "TIMBER" -> swapNum1 = "3";
                    case "BRICK" -> swapNum1 = "4";
                    case "GOLD" -> swapNum1 = "5";
                }
                switch (swap2) {
                    case "ORE" -> swapNum2 = "0";
                    case "GRAIN" -> swapNum2 = "1";
                    case "WOOL" -> swapNum2 = "2";
                    case "TIMBER" -> swapNum2 = "3";
                    case "BRICK" -> swapNum2 = "4";
                    case "GOLD" -> swapNum2 = "5";
                }
                Swap(swapNum1, swapNum2, board);
                showResources(board.playerResource.resource);
            }
        });

        root.getChildren().add(roll);
        root.getChildren().add(DiceButtons);
    }


    /**
     * Call this method in the game class
     *
     * @param primaryStage
     * @param game game object
     * @throws Exception
     */
    public void show(Stage primaryStage, comp1110.ass2.Game game) throws Exception {
        primaryStage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        this.catanBackground();


        root.getChildren().add(map);
        root.getChildren().add(structures);
        root.getChildren().add(controls);

        game.playerResource.resetResource();
        turnStart(game);
        makeControls(game);

        showResources(game.playerResource.resource);
        showScores();
        showFinalScores();

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
