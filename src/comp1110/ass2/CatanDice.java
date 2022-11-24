package comp1110.ass2;

import java.util.*;

public class CatanDice {
    /**
     * Authors: Sam, Harry, Hamish
     * @version Final
     */

    /**
     * Check if the string encoding of a board state is well-formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well-formed.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     * a board state, false otherwise.
     */
    public static boolean isBoardStateWellFormed(String board_state) {
        if (Objects.equals(board_state, "")) return true;// special empty case
        String[] states = board_state.split(","); // Split by comma separated values

        for (String state : states) {
            //Case for if a state in the middle of a board state is empty
            if (Objects.equals(state, ""))
                return false;
            String[] words = state.split("", 2); /* Split into two parts, the first letter,
            and the rest of the string */
            String letter = words[0]; // The first letter of the state
            String strNumbers = words[1]; // Everything after the first letter (should be numbers)

            // The amount of digits for the number segment must be 1 or 2
            if (strNumbers.length() != 1 && strNumbers.length() != 2) return false;

            // Check the string numbers can be successfully converted to int
            try {
                Integer.parseInt(strNumbers);
            } catch (NumberFormatException var) {
                return false;
            } // "var" isn't needed, but catch won't work without an instance variable


            int intNumber = Integer.parseInt(strNumbers); // now knowing conversion works, convert to int
            //Case for if the number for strNumbers begins with a 0 then another digit
            if (strNumbers.length() > 1)
                if (strNumbers.charAt(0) == '0')
                    intNumber = 0;


            // Check letter cases and then valid ranges for structures
            if (!switch (letter) {
                // Road and Knight/Joker simply check for valid integer ranges
                case "R" -> 0 <= intNumber && intNumber <= 15;
                case "K", "J" -> 1 <= intNumber && intNumber <= 6;

                // arrayList's have the ".contains" method to simplify checking against multiple variables
                case "S" -> Arrays.asList(3, 4, 5, 7, 9, 11).contains(intNumber);
                case "C" -> Arrays.asList(7, 12, 20, 30).contains(intNumber);
                default -> false;
            }) return false; // If any of the above are false, return it
        }
        return true; // Iff all items in the for loop pass the check, return true.
    }

    /**
     * Check if the string encoding of a player action is well-formed.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     * a board state, false otherwise.
     */
    public static boolean isActionWellFormed(String action) {

        // Completed: Task #4
        //Split action string into action type, and then positions
        //Split at each space, if no space is present, the move is invalid
        if (!action.contains(" ")) {
            return false;
        }
        String[] actionParts = action.split(" ");
        if (actionParts.length == 1) {
            return false;
        }

        //Build moves and trade moves should only contain two strings, whilst swap needs three
        switch (actionParts[0]) {
            case ("build"):
                if (actionParts.length != 2) {
                    return false;
                }
                //Use the same logic for the buildings as done in isBoardStateWellFormed
                //Splits the second string into its letter and coordinate
                String[] buildAction = actionParts[1].split("", 2);
                String letter = buildAction[0]; // The first letter of the state
                String strNumbers = buildAction[1]; // Everything after the first letter (should be numbers)

                // The amount of digits for the number segment must be 1 or 2
                if (!(strNumbers.length() == 1 || strNumbers.length() == 2)) {
                    return false;
                }

                // Check the string numbers can be successfully converted to int

                try {
                    Integer.parseInt(strNumbers);
                } catch (NumberFormatException ex) {
                    return false;
                } // the "ex" instance variable isn't needed, but catch won't work without one


                int intNumber = Integer.parseInt(strNumbers); // now knowing conversion works, convert to int
                //Case for if the number for strNumbers begins with a 0 then another digit
                if (strNumbers.length() > 1)
                    if (strNumbers.charAt(0) == '0')
                        intNumber = 0;


                // Check letter cases and then valid ranges for structures
                switch (letter) {
                    // Road and Knight/Joker simply check for valid integer ranges
                    case "R" -> {
                        if (0 <= intNumber && intNumber <= 15) break;
                        else return false;
                    }
                    case "K", "J" -> {
                        if (1 <= intNumber && intNumber <= 6) {
                            System.out.println(intNumber);
                        } else {
                            System.out.println("Returning false at k");
                            return false;
                        }
                    }
                    case "S" -> {
                        if (!Arrays.asList(3, 4, 5, 7, 9, 11).contains(intNumber))
                            return false;
                    }
                    case "C" -> {
                        if (!Arrays.asList(7, 12, 20, 30).contains(intNumber))
                            return false;
                    }
                    default -> {
                        return false;
                    }
                }

                break;
            case ("trade"):
                if (actionParts.length != 2) {
                    return false;
                }
                //int representing the second string of the action
                int tradeInt;
                try {
                    Integer.parseInt(actionParts[1]);
                }
                //Should this fail, the move is invalid
                catch (NumberFormatException ex) {
                    return false;
                }
                tradeInt = Integer.parseInt(actionParts[1]);
                //Index out of range for the move
                if (tradeInt < 0 || tradeInt > 5) {
                    return false;
                }

                break;
            case ("swap"):
                if (actionParts.length != 3) {
                    return false;
                }
                int[] swapInts = new int[2];
                for (int i = 0; i < 2; i++) {
                    try {
                        Integer.parseInt(actionParts[1]);
                    }
                    //Should this fail, the move is invalid
                    catch (NumberFormatException ex) {
                        return false;
                    }
                    swapInts[i] = Integer.parseInt(actionParts[i + 1]);

                    //Index out of range for the move
                    if (swapInts[i] < 0 || swapInts[i] > 5) {
                        return false;
                    }
                }

                break;
            default: //If the initial string is none of these, the action is invalid
                return false;
        }
        return true;
    }

    /**
     * Roll the specified number of dice and add the result to the
     * resource state.
     * <p>
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice:         The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *                        roll will be added to.
     *                        <p>
     *                        This method does not return any value. It should update the given
     *                        resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {

        // Define player and dice resources
        Resource playerResource = new Resource(resource_state);
        Resource diceResource = new Resource();
        Random random = new Random();

        // Roll dice n times
        for (int n = 0; n < n_dice; n++) {
            // Roll the dice and increment the resource that was rolled
            int diceRoll = random.nextInt(6);
            diceResource.resource[diceRoll] += 1;
            resource_state[diceRoll] += 1;
        }

        // Add the two Resource objects and update the original int[] resource_state
        playerResource.add(diceResource);
    }

    /**
     * Check if the specified structure can be built next, given the
     * current board state. This method should check that the build
     * meets the constraints described in section "Structure Constraints"
     * of the README file.
     *
     * @param structure:   The string representation of the structure to
     *                     be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     * otherwise.
     */
    public static boolean checkBuildConstraints(String structure, String board_state) {

        // Instantiate board based on the board state, then get the target structure's class object
        Game board = new Game(board_state);
        Structure gameStructure = board.getStructure(structure);

        // If City or Settlement, check if previous building and the previous connecting road is built
        if (gameStructure instanceof Building building) {
            return building.previousBuilding.isBuilt() && building.previousRoad.isBuilt();
        }

        // If Knight, check if previous knight is built
        if (gameStructure instanceof Knight knight) {
            return knight.previousKnight.isBuilt();
        }

        // If Road, check if previous connecting road is built
        if (gameStructure instanceof Road road) {
            return road.previousRoad.isBuilt();
        }
        return false; // base case
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, without considering trades or swaps.
     *
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
     * The ordering of resource_state appears to be ore, grain, wool, timber, bricks, gold
     */
    public static boolean checkResources(String structure, int[] resource_state) {
        // Create Resource object from given player resource
        Resource resourceState = new Resource(resource_state);

        // For each Structure type, return if the player's current resources are greater than the structure cost.
        return switch (structure.charAt(0)) {
            case 'K', 'J' -> resourceState.greaterThanEqualTo(new Resource(Structure.structureType.KNIGHT));
            case 'R' -> resourceState.greaterThanEqualTo(new Resource(Structure.structureType.ROAD));
            case 'C' -> resourceState.greaterThanEqualTo(new Resource(Structure.structureType.CITY));
            case 'S' -> resourceState.greaterThanEqualTo(new Resource(Structure.structureType.SETTLEMENT));
            default -> false;
        };
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, considering also trades and/or swaps.
     * This method needs access to the current board state because the
     * board state encodes which Knights are available to perform swaps.
     *
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
                                                         String board_state,
                                                         int[] resource_state) {
        String type = String.valueOf(structure.charAt(0)); //get the structure type in a single string form
        Game board = new Game(board_state);
        Resource resource = new Resource(resource_state);

        //return true if the resource is already enough
        if (checkResources(structure, resource_state)) {
            return true;
        }
        int[] resourceNeed = resource.resourceNeed(type); //check the needed resource

        //using the for loop to check the needed number of each kind of resource and try to trade or swap it
        for (int i = 0; i < resourceNeed.length; i++) {
            if (resourceNeed[i] > 0) {
                if (resource.resource[5] >= 2) { //try trade
                    resource.resource[5] -= 2;
                    resource.resource[i] += 1;
                } else if (board.containStructure("J6")) { //try swap with J6
                    resource.resource[i] += 1;
                    board.SwapJokerString(6);
                } else if (board.containStructure("J" + (i + 1))) { //try normal swap
                    resource.resource[i] += 1;
                    board.SwapJokerString(i + 1);
                }
            }
        }
        return checkResources(structure, resource.resource); // check whether the resource is enough again
        // FIXME: Task #12
    }

    /**
     * Same purpose as the function above but instead returns the String[]
     * that allows for the boolean to pass.
     * This method needs access to the current board state because the
     * board state encodes which Knights are available to perform swaps.
     *
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return String[] holding the actions performed if the check was successful,
     * otherwise, returns null
     */
    public static String[] checkResourcesWithTradeAndSwapActions(String structure,
                                                                 String board_state,
                                                                 int[] resource_state) {
        ArrayList<String> actions = new ArrayList<>();
        String type = String.valueOf(structure.charAt(0)); //get the structure type in a single string form
        Game board = new Game(board_state);
        Resource resource = new Resource(resource_state);

        //return true if the resource is already enough
        if (checkResources(structure, resource_state)) {
            return null;
        }
        int[] resourceNeed = resource.resourceNeed(type); //check the needed resource

        //using the for loop to check the needed number of each kind of resource and try to trade or swap it
        for (int i = 0; i < resourceNeed.length; i++) {
            if (resourceNeed[i] > 0) {
                if (resource.resource[5] >= 2) { //try trade
                    resource.resource[5] -= 2;
                    resource.resource[i] += 1;
                    actions.add("trade " + i);
                } else if (board.containStructure("J6")) { //try swap with J6
                    resource.resource[i] += 1;
                    board.SwapJokerString(6);
                    //Doesn't have what resource will be swapped, will need to be added between "swap" and i
                    actions.add("swap " + i);
                } else if (board.containStructure("J" + (i + 1))) { //try normal swap
                    resource.resource[i] += 1;
                    board.SwapJokerString(i + 1);
                    actions.add("swap " + i);
                }
            }
        }
        if (checkResources(structure, resource.resource)) // check whether the resource is enough again
        {
            return actions.toArray(new String[0]);
        } else return null;
    }

    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action:         String representation of the action to check.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     */
    public static boolean canDoAction(String action, String board_state, int[] resource_state) {

        // Default checks before we can instantiate variables. Check if board and action are well-formed
        if (!isBoardStateWellFormed(board_state) || !isActionWellFormed(action)) return false;

        // Construct a resource object out of the int[] (shallow copy as to not change the given values)
        Resource playerResource = new Resource(resource_state.clone());

        // Split the input action string into parts, the build/trade/swap part,
        // and the subsequent parameters for those actions
        String[] actionParts = action.split(" ");

        System.out.println("canDoActionSingle board state: " + board_state);

        // Now check conditions for a valid action
        return switch (actionParts[0]) {
            case "build" -> !board_state.contains(actionParts[1]) // Check: structure is not already built
                    && checkBuildConstraints(actionParts[1], board_state)
                    && checkResources(actionParts[1], resource_state);

            case "trade" -> playerResource.getResource(Resource.resourceType.GOLD) >= 2; // Check: required gold amount is met

            case "swap" -> (board_state.contains("J" + (Integer.parseInt(actionParts[2]) + 1)) ||
                    board_state.contains("J6")) // Check: target knight is built and swap available (J not K)
                    && playerResource.resource[Integer.parseInt(actionParts[1])] >= 1; // Check: player has the resource required to swap
            default -> false;
        };
    }

    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions:        The sequence of (string representations of) actions.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action sequence is executable, false otherwise.
     */
    public static boolean canDoSequence(String[] actions,
                                        String board_state,
                                        int[] resource_state) {
        if (board_state == null || actions == null || resource_state == null) {
            return false;
        }


        Resource pResource = new Resource(resource_state); //Construct a resource object out of the int[]
        //Resource pResource = new Resource((resource_state.clone())); //Construct a resource object out of the int[]

        Game board = new Game(board_state); //Construct a game object out of the string

        for (var action : actions) {
            String[] actionParts = action.split(" ");
            //String[] boardStates = board.boardState.split(",");

            if (canDoAction(action, board.boardState, pResource.resource)) {
                //trade condition
                if (actionParts[0].equals("trade")) {
                    //System.out.println(Arrays.toString(pResource.resource));
                    pResource.addOne(Integer.parseInt(actionParts[1]), 1);//add the swapped resource
                    pResource.subtractOne(5, 2);//subtract 2 golds
                    //System.out.println(Arrays.toString(pResource.resource));
                }
                //swap condition
                else if (actionParts[0].equals("swap")) {
                    if (!(pResource.resource[Integer.parseInt(actionParts[1])] == 0)) {
                        int jokerNum = Integer.parseInt(actionParts[2]) + 1;//add one to get jokers number

                        System.out.println("pResource before swap: " + Arrays.toString(pResource.resource));
                        pResource.subtractOne(Integer.parseInt(actionParts[1]), 1);//subtract the resource used for swap
                        pResource.addOne(Integer.parseInt(actionParts[2]), 1);//add the swapped resource
                        System.out.println("pResource after  swap: " + Arrays.toString(pResource.resource));

                        board.SwapJokerString(jokerNum);//joker becomes knight after swap

                    } else {
                        System.out.println("No resources for swap");
                        return false;
                    }
                }
                //build condition
                else {
                    //Structure structure = board.getStructure(actionParts[1]);
                    if (checkBuildConstraints(actionParts[1], board.boardState)
                            && checkResources(actionParts[1], pResource.resource)) {
                        System.out.println("Current Board: " + board.boardState);
                        board.updateBoardStateString(actionParts[1]); //update the structure string
                        System.out.println("Updated Board: " + board.boardState);


                        // changed a constructor, so I had to update this so it didn't fail the tests.
                        String structType = String.valueOf(actionParts[1].charAt(0));
                        Resource structureCost = new Resource(Structure.structureTypeFromString(structType));
                        System.out.println("Structure Type: " + structType
                                + ", Player Resource: " + Arrays.toString(pResource.resource)
                                + ", Structure Cost: " + Arrays.toString(structureCost.resource));
                        pResource.subtract(structureCost);
                        System.out.println("Player Resource after subtracting: " + Arrays.toString(pResource.resource));

                    } else {
                        System.out.println("failed build & resource constraint check");
                        return false;
                    }
                }
            } else {

                System.out.println("Failed:canDoAction");
                return false;
            }
        }

        return true; // COMPLETED: Task #11

    }


    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     * <p>
     * Note that on the Island One map, there is a unique path to every
     * structure.
     *
     * @param target_structure: The string representation of the structure
     *                          to reach.
     * @param board_state:      The string representation of the board state.
     * @return An array of string representations of the roads along the
     * path.
     */
    public static String[] pathTo(String target_structure, String board_state) {

        // Apply the game and get the target structure's reference
        Game game = new Game(board_state);
        Structure target = game.getStructure(target_structure);

        // Convert target into road or building to access it's "previousRoad" field.
        Road parent = (target instanceof Road) ? ((Road) target).previousRoad : ((Building) target).previousRoad;

        // Traverse upwards through the tree by checking each parent node's "built" field, then store each node.
        ArrayList<String> path = new ArrayList<>();
        while (!parent.isBuilt()) {
            // Get the index of each road structure and store it in the list
            int roadId = Arrays.asList(game.roadNetwork).indexOf(parent);
            path.add("R" + roadId);

            // traverse to parent node and repeat loop
            parent = parent.previousRoad;
        }

        // We stored roads from target-to-root, so reverse list to get root-to-target, then return list as an array
        Collections.reverse(path);
        return path.toArray(new String[0]);
    }

    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as building other structures if
     * needed to reach the target structure or to satisfy the build order
     * constraints.
     * <p>
     * However, the plan must not have redundant actions. This means it
     * must not build any other structure that is not necessary to meet
     * the building constraints for the target structure, and it must not
     * trade or swap for resources if those resources are not needed.
     * <p>
     * If there is no valid build plan for the target structure from the
     * specified state, return null.
     *
     * @param target_structure: The string representation of the structure
     *                          to be built.
     * @param board_state:      The string representation of the board state.
     * @param resource_state:   The available resources.
     * @return An array of string representations of player actions. If
     * there exists no valid build plan for the target structure,
     * the method should return null.
     */
    public static String[] buildPlan(String target_structure,
                                     String board_state,
                                     int[] resource_state) {
        //Create a resource object for ease of use of the resource state
        Resource playerResources = new Resource(resource_state);

        //Create a game object that uses the copy of the board, such that it won't modify the game
        //however will account for modifications in the actionPlan instance

        Game board = new Game(String.valueOf(board_state));

        ArrayList<String> actionPlan = new ArrayList<>();

        //Check if the object can already be built
        if (canDoAction("build " + target_structure, board.boardState, playerResources.resource)) {
            actionPlan.add("build " + target_structure);
            return actionPlan.toArray(new String[0]);
        }
        //Checks if there will be any resources to even build it in the first place with the current board
        if (!checkResourcesWithTradeAndSwap(target_structure, (board.boardState),
                new Resource(playerResources.resource).resource))
            return null;
            //Checks if it can be done with a trade or swap and its build constraints are true
        else if (checkResourcesWithTradeAndSwap(target_structure, (board.boardState),
                new Resource(playerResources.resource).resource)
                && checkBuildConstraints(target_structure, board.boardState)) {
            String[] swapsTrades = checkResourcesWithTradeAndSwapActions(target_structure, (board.boardState),
                    new Resource(playerResources.resource).resource);
            //Add either the trade or swap to the action list
            for (String sT : swapsTrades) {
                String[] sTparts = sT.split(" ");
                if (sTparts[0].equals("trade")) {
                    actionPlan.add(sT);
                } else if (sTparts[0].equals("swap")) {
                    //Takes from the max value resource
                    int maxValue = Arrays.stream(playerResources.resource).max().getAsInt();
                    int maxInt = 0;

                    for (int i = 0; i < 6; i++) {
                        if (playerResources.resource[i] == maxValue)
                            maxInt = i;
                    }
                    actionPlan.add(sTparts[0] + " " + maxInt + " " + sTparts[1]);
                }
            }
            actionPlan.add("build " + target_structure);
            //For the context of build constraints, the cloned board builds the structure
            board.setBuilt(target_structure, true);
        } else {

            //Initialise a variable to find the necessary roads to build to the desired structure
            //Function will first try to build the roads, then fulfill the building constraints
            String[] roadPlan = pathTo(target_structure, board_state);
            //System.out.println(Arrays.toString(roadPlan));
            for (String s : roadPlan) {
                //Attempts to build each road to the structure
                if (canDoAction("build " + s, (board.boardState), playerResources.resource))
                    actionPlan.add("build " + s);

                    //Sees if it is possible with a trade or swap
                else if (checkResourcesWithTradeAndSwap(s, (board.boardState),
                        new Resource(playerResources.resource).resource)) {
                    String[] swapsTrades = checkResourcesWithTradeAndSwapActions(target_structure,
                            (board.boardState), new Resource(playerResources.resource).resource);
                    //For now, the resources that will be swapped will be those un-needed for building the roads or the
                    //desired structure
                    //Add either the trade or swap to the action list
                    for (String sT : swapsTrades) {
                        String[] sTparts = sT.split(" ");
                        if (sTparts[0].equals("trade")) {
                            actionPlan.add(sT);
                        } else if (sTparts[0].equals("swap")) {
                            //Takes from the max value resource
                            int maxValue = Arrays.stream(playerResources.resource).max().getAsInt();
                            int maxInt = 0;

                            for (int i = 0; i < 6; i++) {
                                if (playerResources.resource[i] == maxValue)
                                    maxInt = i;
                            }
                            actionPlan.add(sTparts[0] + " " + maxInt + " " + sTparts[1]);
                        }
                    }
                    actionPlan.add("build " + s);
                } else
                    return null;

            }
            //Now add in the build constraints
            if (checkBuildConstraints(target_structure, board.boardState)) {
                System.out.println("Roads have been built, now in constraints zone");
                if (canDoAction("build " + target_structure, board.boardState, playerResources.resource)) {
                    actionPlan.add("build " + target_structure);
                    //For the context of build constraints, the cloned board builds the structure
                    board.setBuilt(target_structure, true);
                } else if (checkResourcesWithTradeAndSwap(target_structure, board.boardState, resource_state)) {
                    String[] swapsTrades = checkResourcesWithTradeAndSwapActions(target_structure, (board.boardState),
                            new Resource(playerResources.resource).resource);
                    //For now, the resources that will be swapped will be those un-needed for building the roads or the
                    //desired structure
                    //Add either the trade or swap to the action list
                    for (String sT : swapsTrades) {
                        String[] sTparts = sT.split(" ");
                        if (sTparts[0].equals("trade")) {
                            actionPlan.add(sT);
                        } else if (sTparts[0].equals("swap")) {
                            //Takes from the max value resource
                            int maxValue = Arrays.stream(playerResources.resource).max().getAsInt();
                            int maxInt = 0;

                            for (int i = 0; i < 6; i++) {
                                if (playerResources.resource[i] == maxValue)
                                    maxInt = i;
                            }
                            actionPlan.add(sTparts[0] + " " + maxInt + " " + sTparts[1]);
                        }
                    }
                    actionPlan.add("build " + target_structure);
                    //For the context of build constraints, the cloned board builds the structure
                    board.setBuilt(target_structure, true);
                } else
                    return null;
            }
            //Needs to add the rest


        }
        System.out.println(actionPlan);
        String[] actionArr = actionPlan.toArray(new String[0]);
        System.out.println(Arrays.toString(actionArr));
        return actionPlan.toArray(new String[0]); // FIXME: Task #14
    }


}

