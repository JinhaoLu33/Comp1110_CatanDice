package comp1110.ass2;

   /**
     * Authors: Sam Mason-Cox, Hamish Hunter, Jinhao Lu
     */


public class Structure {
    /**
     * The Structure class is a superclass for all structure types, and defines any behaviour
     * that is similar within these subclass'
     *
     * @field value  - (int) holds the amount of points this structure is worth
     * @field built  - (bool) signifies if the player has built the structure or not (all false by default)
     * @field type   - (enum structureType) what type of structure this is (knight, road, settlement, city)
     * @field cost   - (resource) The resource cost of a particular structure
     * @field root   - (boolean) Define if this structure is a "root" structure or not.
     *
     * @NOTICE: we can either declare coordinates in the structure class, or make a position class and let structure
     *   inherit from that. The road rotation can also be done inside the road class, or the aforementioned position class idea.
     *   For now, I will ignore coordinates and focus on working code. -Sam M.
     * @field xCoordinate - (int) The x-coordinate, in pixel measurement, of where this structure is in javaFX
     * @field yCoordinate - (int) The y-coordinate, in pixel measurement, of where this structure is in javaFX
     *
     * Each structure has a few different pieces of functionality that need to be added, such as its
     * personal resource cost, previously built structure, or what resource it trades. This functionality is contained
     * inside subclass' that inherit from the Structure class.
     *
     * The "Knight" subclass handles unique knight behaviour.
     * The "Road" subclass handles unique road behaviour.
     * The "Building" subclass handles unique settlement and city behaviour.
     **/

    /**
     * The structureType enumerated class contains potential structures that exist in this game.
     * @field ROAD
     * @field KNIGHT
     * @field SETTLEMENT
     * @field CITY
     */
    public enum structureType { ROAD, KNIGHT, SETTLEMENT, CITY }

    /**
     * Returns the structure type from either a letter or a string,
     * Saves the effort of running an if sequence whenever it is necessary.
     *
     * @param type: String representing the structure type.
     * @return The equivalent structureType object given the string, or
     * null if the string is invalid.
     */
    public static structureType structureTypeFromString(String type)
    {
        return switch (type.toLowerCase()){
            case "r", "road"       -> structureType.ROAD;
            case "j", "k", "knight"     -> structureType.KNIGHT;
            case "s", "settlement" -> structureType.SETTLEMENT;
            case "c", "city"       -> structureType.CITY;
            default -> null;
        };
    }

    // What type of structure this is
    public final Structure.structureType type;

    // The amount of points this structure is worth
    public int value;

    // If the structure has been built or not
    public boolean built;

    // The resource cost of this structure
    public Resource cost;

    // Is this structure the root of the structure tree?
    public boolean root;

    /**
     * The default constructor for the Structure class.
     *
     * This constructor sets all values to 0 (including value and cost), and structure type to null.
     * It is advised that you do NOT use this constructor.
     */
    public Structure(){
        this.value = 0;
        this.built = false;
        this.type = null;
        this.cost = new Resource();
        this.root = false;
    }

    /**
     * Constructor for the Structure class that specifies values from only the given structure type.
     *
     * @param value (int) The amount of points this structure is worth
     * @param type (structureType) What type of structure this is (ROAD, KNIGHT, SETTLEMENT, CITY)
     */
    public Structure(int value, structureType type){
        this.value = value;
        this.type = type;
        this.cost = new Resource(type);
    }

    /**
     * Constructor for the Structure class that allows us to specify ALL of its values for specific situations.
     *
     * @param value (int) The amount of points this structure is worth
     * @param built (bool) If the player has built this structure or not
     * @param type  (structureType) What type of structure this is (ROAD, KNIGHT, SETTLEMENT, CITY)
     * @param cost  (Resource) The resource cost of the structure
     * @param root  (boolean) Is this structure the root of its structure tree?
     */
    public Structure(int value, boolean built, structureType type, Resource cost, boolean root){
        this.value = value;
        this.built = built;
        this.type = type;
        this.cost = cost;
        this.root = root;
    }

    /** set this structure as a root structure. This sets all values to 0 or null, but is in a built state. */
    public void setRoot(){
        this.cost = new Resource();
        this.value = 0;
        this.built = true;
        this.root = true;
    }

    /**
     * Set the cost of this structure manually.
     * This method is only used for unit tests, as the costs of structures never changes.

     * @param cost (resource) The resource cost to apply to this structure.
     */
    private void setCost(Resource cost){
        this.cost = cost;
    }

    /** Check if the structure has been built
     * @return (boolean) true if the structure is currently built, false otherwise. */
    public boolean isBuilt(){ return this.built; }

    /** Set the structure to a built state. */
    public void buildStructure(){
        this.built = true;
    }

    /** Set the structure to an unbuilt state.
     * Unbuild is only used to change a board state without instantiating a new Game object*/
    public void unbuildStructure(){ this.built = false; }

    /**
     * Check if the structure can be built based on the player's resources and current board structure layout.
     * This only checks if it can be built in a single turn, WITHOUT considering knight trades.
     *
     * NOTE: Tasks 7 and 8 both instantiate a new Game object, so do not use those methods to check if a
     *       structure can be built or not. Instead, use this method as it combines both tasks' functionality
     *       without creating new class objects.
     *
     * @param resources (Resource) the resources the player currently has access to (excluding knights).
     * @return (boolean) true if the structure can be built with current resources and board state (excluding knights).
     */
    public boolean canBuild(Resource resources){
        // this functionality is basically replaced by the combination of checkBuildConstraints and checkResources.

        if (this instanceof Building building){ // Check if city/settlement
            return building.previousBuilding.isBuilt() // Check previous city/settlement is built
                    && building.previousRoad.isBuilt() // Check connecting road is built
                    && resources.greaterThanEqualTo(this.cost); // Check player has enough resources
        } // Repeat for other subclass structure types

        if (this instanceof Knight knight) {
            return knight.previousKnight.isBuilt() && resources.greaterThanEqualTo(this.cost);
        }

        if (this instanceof Road road){
            System.out.println("previous road built?: "+road.previousRoad.isBuilt());
            return road.previousRoad.isBuilt() && resources.greaterThanEqualTo(this.cost);
        }

        return false; // base case
    }
}
