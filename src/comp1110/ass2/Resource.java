package comp1110.ass2;

import java.util.Arrays;

   /**
     * Authors: Sam Mason-Cox, Hamish Hunter, Jinhao Lu
     */


public class Resource {
    /**
     * The Resource class controls how much of a resource an object holds.
     * This could be the player's current resources, or the resource cost of a structure.
     * <p>
     * Ordering: Ore, Grain, Wool, Timber, Brick, Gold
     *
     * @field resource - (int[6]) Holds the quantity of resources as an array.
     */

    // List of resources this player has or building costs.
    public int[] resource;

    /** Default resource constructor. Instantiates all values at 0 */
    public Resource() { this.resource = new int[6]; }

    /**
     * The Resource constructor defines given resources for this object
     * Resource ordering is same as resource_state, that is: Ore, Grain, Wool, Timber, Brick, Gold
     * Instantiated resources will always have gold, just set it to 0 for buildings. (that way computation is not array size dependent)
     *
     * @param Ore    (int) The amount of ore specified for this resource
     * @param Grain  (int) etc
     * @param Wool   (int)
     * @param Timber (int)
     * @param Brick  (int)
     * @param Gold   (int)
     */
    public Resource(int Ore, int Grain, int Wool, int Timber, int Brick, int Gold) {
        this(new int[] {Ore,Grain,Wool,Timber,Brick,Gold});
    }

    /**
     * Constructor that turns an int array into a resource object
     * This constructor assumes the array is in the order: Ore, Grain, Wool, Timber, Brick, Gold
     *
     * @param intArray (int[]) the array to convert into resource
     */
    public Resource(int[] intArray) {
        this.resource = intArray.clone(); // shallow copy lets me parse by value instead of reference
    }

    /**
     * Constructor that turns an enumerated resourceType into a resource object with one item of that type
     *
     * @param type (resourceType) the type of resource to convert to a resource object with a single entry
     */
    public Resource(resourceType type) {
        this(switch (type) {
            case ORE    -> new int[] {1, 0, 0, 0, 0, 0};
            case GRAIN  -> new int[] {0, 1, 0, 0, 0, 0};
            case WOOL   -> new int[] {0, 0, 1, 0, 0, 0};
            case TIMBER -> new int[] {0, 0, 0, 1, 0, 0};
            case BRICK  -> new int[] {0, 0, 0, 0, 1, 0};
            case GOLD   -> new int[] {0, 0, 0, 0, 0, 1}; // GOLD case not used for knights
            default -> new int[6];
        });
    }


    /**
     * Constructor to define a resource object from a structure type.
     *
     * @param type (structureType) Set the resource value to the cost of this structure type
     */
    public Resource(Structure.structureType type) {
        this(switch (type) {
            case ROAD       -> new int[]{0, 0, 0, 1, 1, 0};
            case KNIGHT     -> new int[]{1, 1, 1, 0, 0, 0};
            case SETTLEMENT -> new int[]{0, 1, 1, 1, 1, 0};
            case CITY       -> new int[]{3, 2, 0, 0, 0, 0};
        });
    }

    // Below are getter methods for individual resources
    public enum resourceType {ORE, GRAIN, WOOL, TIMBER, BRICK, GOLD}

    public int getResource(resourceType type) {
        return switch (type) {
            case ORE    -> this.resource[0];
            case GRAIN  -> this.resource[1];
            case WOOL   -> this.resource[2];
            case TIMBER -> this.resource[3];
            case BRICK  -> this.resource[4];
            case GOLD   -> this.resource[5];
        };
    }

    // Below are methods to manipulate two resource objects, by adding, subtracting and comparing them.
    // Unfortunately, I have to use custom methods as java does not allow me to override binary operators like + < and =

    /**
     * (Subtract Operator) Method to subtract all resource values (used for subtracting entire building costs).
     * Usage: this.subtract(that) = this - that
     *
     * @param resource (Resource) the resource object to subtract
     */
    public void subtract(Resource resource) {
        for (int i = 0; i < 6; i++) {
            this.resource[i] -= resource.resource[i];
        }
    }

    public void subtractOne(int resource, int number) {
        if (this.resource[resource] != 0) {
            this.resource[resource] -= number;
        } else {
            this.resource[resource] = 0;
        }
    }

    /**
     * (Addition Operator) Method to add all resource values (used for adding dice rolls to player's resources).
     * Usage: this.add(that) = this + that
     *
     * @param resource (Resource) the resource object to add
     */
    public void add(Resource resource) {
        for (int i = 0; i < 6; i++) {
            this.resource[i] += resource.resource[i];
        }
    }

    public void addOne(int resource, int number) {
        this.resource[resource] += number;
    }

    /**
     * (Greater-Than-or-Equal-to Operator) Method to compare resource values.
     * This is used to check if the player's resource is greater than or equal to the building cost (if true, they can build it)
     * Usage: this.greaterThanEqualTo(that) = this >= that
     *
     * @param resource (Resource) the resource object to compare
     * @return Returns true if all the resource values of this are greater than or equal to that
     */
    public boolean greaterThanEqualTo(Resource resource) {
        for (int i = 0; i < 6; i++) {
            if (this.resource[i] < resource.resource[i]) return false;
        }
        return true;
    }

       /**
        * return the needed type of resources for the type of structure
        * @param type: the type of structure need to be built(single letter)
        * @return Return an array with the needed amount of resource
        */
    public int[] resourceNeed(String type){
        int[] resourceNeed = {0,0,0,0,0,0};
        for (int i = 0; i < 6; i++) {
            int[] costFromType = new Resource(Structure.structureTypeFromString(type)).resource;
            if (this.resource[i] < costFromType[i])
                resourceNeed[i]+=1;
        }
        return resourceNeed;
    }


    /**
     * Method that calculates a new resource object that incorporates potential resources from
     * swaps and trades. Method prioritises trades over swaps.
     *
     * @param board_state current state of the board. MUST BE A CLONE OF THE BOARDSTATE!
     * @param desired The desired type of resource to add to the resource object
     * @return The edited resource object with new values.
     */

    public Resource resourceWithTS(String board_state, resourceType desired,resourceType swapped,int amount)
    {
        //Function acts recursively
        if(amount < 1) {
            System.out.println(Arrays.toString(this.resource));
            return this;
        }
        Game board = new Game(board_state);
        int jokerNum = switch (desired){
            case ORE -> 1;
            case GRAIN -> 2;
            case WOOL -> 3;
            case TIMBER -> 4;
            case BRICK -> 5;
            case GOLD -> 6;
        };
        int swapNum = switch (swapped){
            case ORE -> 1;
            case GRAIN -> 2;
            case WOOL -> 3;
            case TIMBER -> 4;
            case BRICK -> 5;
            case GOLD -> 6;
        };

        if (this.getResource(resourceType.GOLD) >= 2)
        {
            this.addOne(jokerNum - 1, 1);
            this.subtractOne(5,2);
            amount --;
            System.out.println(amount);
        }
        //Now looks to see if any more swaps need to be done
        if(amount > 0 && this.getResource(resourceType.GOLD) < 2)
        {
            if(board_state.contains("J"+jokerNum))
            {

                this.addOne(jokerNum-1,1);
                //For now, will subtract the resource index 1 above the desired amount
                this.subtractOne(swapNum-1,1);
                //System.out.println(Arrays.toString(pResource.resource));
                board.SwapJokerString(jokerNum);//joker becomes knight after swap
                amount--;
            }

        }
        if (this.getResource(resourceType.GOLD) >= 2)
        {
            this.addOne(jokerNum - 1, 1);
            this.subtractOne(5,2);
            amount --;
            System.out.println(amount);
        }
        else amount --;
        return this.resourceWithTS(board_state,desired,swapped,amount);
    }

       /**
        * reset the resource to 0
        */
       public void resetResource(){
           Arrays.fill(resource, 0);
       }

   }


