package comp1110.ass2;

import java.util.Arrays;
import java.util.Objects;

public class Game {
    /**
     * Authors: Sam Mason-Cox, Hamish Hunter, Jinhao Lu
     * The Game class represents an actual game of Catan Dice, and is used to simulate theoretical boards.
     */

    // TODO: confirm the "instantiateStructureNetwork" methods work as intended
    //       (I might need to build an @Override string method for each structure type, then print out each element in the network array)
    public Road[] roadNetwork = new Road[17]; // All array sizes are +1 for their root structure
    public Knight[] knightNetwork = new Knight[7];
    public Building[] cityNetwork = new Building[5];
    public Building[] settlementNetwork = new Building[7];
    public String boardState; // string representation of board (this can be read by javaFX)

    //Object to hold the player's resources
    public Resource playerResource;

    /**
     * Instantiate the game in it's default state.
     * This instantiates the entire network connection structure of roads, knights cities and settlements.
     * Also comes with the default player resources
     */
    public Game()
    {
        this(""); // constructor chain to instantiate default board state
        playerResource = new Resource(new int[]{1,1,1,1,1,1});
    }

    /**
     * Instantiate the game from a given board state
     *
     * @param board_state (String) a string encoding of a board state
     */
    public Game(String board_state)
    {
        // Instantiate default board state
        this.instantiateRoadNetwork();
        this.instantiateCityNetwork();
        this.instantiateKnightNetwork();
        this.instantiateSettlementNetwork();
        this.boardState = board_state;

        // Apply the board_state changes to the default board
        if (!Objects.equals(board_state, "")) updateBoardState(board_state);
    }

    /**
     * Update the Game network structure from a given string encoded board state.
     * This will BUILD every structure listed in the encoded board state.
     * (it will NOT remove any existing structures, use setBuilt(structure, false) to unbuild structures)
     * Note: this method does NOT handle "" case.
     *
     * @param board_state (String) the string encoded board state, a series of built structures separated by commas
     */
    public void updateBoardState(String board_state)
    {
        // For each structure in the string encoding, build it.
        String[] states = board_state.split(",");
        for (String state : states)
            setBuilt(state, true);
    }

    /**
     * Add the new structure to the boardState string
     *
     * @param structure: new structure built
     */
    public void updateBoardStateString(String structure)
    {
        if (boardState.length() != 0)
        {
            boardState += ",";
        }
        boardState += structure;
    }


    /** When changing a joker to a knight, change the game's board state to reflect this.
     * The method should only change jokers to knights, and will do nothing if the joker could
     *  not be found (ie. unbuilt or invalid number)
     * Note: J = Joker, an unused knight which can be swapped with. K = Knight, it's already been used and cannot be swapped with.
     * @param id the numerical ID of the joker that needs to be changed. ex. "J4" has an ID of 4*/
    public void SwapJokerString(int id)
    {
        // Variables for clarity
        String joker  = "J"+id;
        String knight = "K"+id;

        // If the board state contains the target joker, insert the knight in the targets position instead.
        if (boardState.contains(joker))
        {
            String[] splitInsert = boardState.split(joker,2);
            boardState = splitInsert[0] + knight + splitInsert[1];
        }
    }

    /**
     * check whether the board contains certain structure
     * @param structure Structure to be checked for
     * @return The result of whether the structure is present
     */
    public boolean containStructure(String structure)
    {
        String[] split = boardState.split(",");
        return Arrays.asList(split).contains(structure);
    }

    /**
     * Given a string encoded structure, set it's status to "built" or "unbuilt" based on the boolean input.
     *
     * @param structure (String) the string encoding of a structure, eg. "R5", or "C12"
     * @param built     (boolean) set if the structure should be built or unbuilt
     */
    public void setBuilt(String structure, boolean built)
    {
        // Get structure from encoding and build it
        Structure struct = this.getStructure(structure);
        if (built) struct.buildStructure();
        else struct.unbuildStructure(); // set built value

        // If structure is a used knight, set its status to used/traded/swapped (note: joker's 'J' are unused)
        if (structure.charAt(0) == 'K')
        {
            Knight knight = (Knight) struct;
            knight.setSwap(true);
        }
    }

    /**
     * Given an encoded string input for a structure, return that structure's actual object reference.
     * This method returns null if structure wasn't found, or input was invalid
     * Note: This method relies on the input being well-formed (so check before calling)
     *
     * Usage:
     * getStructure( "R5" )  = The 6th buildable road, marked as "R5" on the assignment's README.md
     * getStructure( "C12" ) = The 2nd buildable city marked as "12" on the assignment's README.md
     *
     * @param structure (String) The string encoding of a structure
     */
    public Structure getStructure(String structure)
    {
        String[] split = structure.split("", 2); // Split into letter, then numbers.
        if (Objects.equals(split[1], "")) return null; // don't handle invalid inputs, but don't crash on single letter inputs

        String letter = split[0]; // The first letter of the encoded string
        int index = Integer.parseInt(split[1]); // The index numbers after the first letter

        return switch (letter) {
            case "R" -> this.roadNetwork[index];
            case "K", "J" -> this.knightNetwork[index]; // Returns knight/joker regardless of if it's been swapped yet
            case "S" -> Building.getBuildingFromValue(index, Structure.structureType.SETTLEMENT, this);
            case "C" -> Building.getBuildingFromValue(index, Structure.structureType.CITY, this);
            default -> null; // Invalid structure letters
        };
    }

    /**
     * Reset a game to its default state without having to instantiate another game object
     */
    public void clearBoardState()
    {
        for (Structure r : roadNetwork)
        {
            r.unbuildStructure();
        }
        for (Knight k : knightNetwork)
        {
            k.unbuildStructure();
            k.setSwap(false);
        } // Knight has extra defaults
        for (Structure c : cityNetwork)
        {
            c.unbuildStructure();
        }
        for (Structure s : settlementNetwork)
        {
            s.unbuildStructure();
        }
    }

    /**
     * Instantiate the entire default road network structure for the board.
     * This outlines how the roads are structured through the use of "previousRoad"
     */
    private void instantiateRoadNetwork()
    {
        /** How the "root" road works:
         *    The "root" road refers to the pre-built black road shown on the Catan Dice Game's board.
         *    When referring to a specific road, such as "build R6" we can just run "build(roadNetwork[6])". (pseudocode)
         *    However, this only works if R0 refers to the normal R0 road, which means there is no place to put the root road before it.
         *    Therefore, I opted to put the root road at the end of the array to preserve the simplified index references.
         *
         *    This "root" idea is now abstracted to the structure class to include root structures for all sub-classes.
         *    A root structure is defined as a structure that is always built, but contains no value or cost.
         */
        // Roads from root & 0-11
        Road root = new Road();
        root.setRoot();
        roadNetwork[16] = root; // root  road
        roadNetwork[0] = new Road(roadNetwork[16]); // first road (connected to the root)
        for (int i = 1; i <= 11; i++)
        {
            // If managing roads 2 or 5, set their previous road to 2 index behind, rather than 1.
            roadNetwork[i] = new Road((i == 2 || i == 5) ? roadNetwork[i - 2] : roadNetwork[i - 1]);
        }

        // Roads from 12-15
        roadNetwork[12] = new Road(roadNetwork[7]); // road 12
        for (int i = 13; i <= 15; i++)
        {
            roadNetwork[i] = new Road(roadNetwork[i - 1]);
        }
    }

    /**
     * Instantiate the entire default knight network structure for the board.
     * This outlines how the knights are structured through the use of "previousKnight"
     */
    private void instantiateKnightNetwork()
    {

        // Define resources for knights to trade
        Resource[] resourceList = new Resource[]{
                new Resource(Resource.resourceType.ORE),
                new Resource(Resource.resourceType.GRAIN),
                new Resource(Resource.resourceType.WOOL),
                new Resource(Resource.resourceType.TIMBER),
                new Resource(Resource.resourceType.BRICK),
                new Resource() // wildcard
        };

        // Knights from 1-6
        Knight root = new Knight();
        root.setRoot();
        knightNetwork[0] = root; // root knight (there is no "0" knight, they start at 1)
        knightNetwork[1] = new Knight(1, resourceList[0], knightNetwork[0]); // first knight
        for (int i = 2; i < 7; i++)
        {
            knightNetwork[i] = new Knight(i , resourceList[i-1], knightNetwork[i - 1]);
            //System.out.println(i-1 + "-knight : " + Arrays.toString(resourceList[i - 2].resource) + " cost: "+ Arrays.toString(knightNetwork[i].cost.resource)); // debug
        }
        //knightNetwork[5].setWildcard(true);
        knightNetwork[6].setWildcard(true); // set wildcard on last knight
    }

    /**
     * Instantiate the entire default city network structure for the board.
     * This outlines how the cities are structured through the use of "previousRoad" and "previousBuilding"
     */
    private void instantiateCityNetwork()
    {

        int[] pointList = new int[]{12, 20, 30}; // Define point values for the cities
        Road[] roadList = new Road[]{roadNetwork[4], roadNetwork[13], roadNetwork[15]}; // Define previous roads for each city

        // Cities from 1-4
        Building root = new Building();
        root.setRoot();
        cityNetwork[4] = root; // root city
        cityNetwork[0] = new Building(7, Structure.structureType.CITY, roadNetwork[1], cityNetwork[4]); // first city
        for (int i = 1; i < 4; i++)
        {
            cityNetwork[i] = new Building(pointList[i - 1], Structure.structureType.CITY, roadList[i - 1], cityNetwork[i - 1]);
        }
    }

    /**
     * Instantiate the entire default settlement network structure for the board.
     * This outlines how the settlements are structured through the use of "previousRoad" and "previousBuilding"
     */
    private void instantiateSettlementNetwork()
    {

        int[] pointList = new int[]{4, 5, 7, 9, 11}; // Define point values for the settlements
        Road[] roadList = new Road[]{roadNetwork[2], roadNetwork[5], roadNetwork[7],
                roadNetwork[9], roadNetwork[11]}; // Define previous roads for each settlement

        // Settlements from 1-6
        Building root = new Building();
        root.setRoot();
        settlementNetwork[6] = root; // root settlement
        settlementNetwork[0] = new Building(3, Structure.structureType.SETTLEMENT,
                roadNetwork[16], settlementNetwork[6]); // first settlement
        for (int i = 1; i < 6; i++)
        {
            settlementNetwork[i] = new Building(pointList[i - 1],
                    Structure.structureType.SETTLEMENT, roadList[i - 1], settlementNetwork[i - 1]);
        }
    }

    /**
     * Debugging: Print the entire structure network.
     * Feel free to change any sys.out parameters for your own testing.
     */
    public void printNetwork()
    {
        System.out.println("Printing Network: ");
        for (Road r : this.roadNetwork)
        {
            System.out.println("" + r.previousRoad + r.isBuilt());
        }
        for (Knight k : this.knightNetwork)
        {
            System.out.println(k);
        }
        for (Building c : this.cityNetwork)
        {
            System.out.println(c);
        }
        for (Building s : this.settlementNetwork)
        {
            System.out.println(s);
        }
        System.out.println("Finished printing network");
    }


}
