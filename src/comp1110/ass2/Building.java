package comp1110.ass2;

import gittest.B;

public class Building extends Structure{
    /**
     @author: Sam Mason-Cox
     @version: 1
     **/
     /**
     * The Building class inherits from structure, but contains all additional behaviour for Settlement and City.
     *
     * This includes keeping track of the previously built settlement/city, and the road the object is connected to.
     *
     * @field previousRoad - (Road):
     *   The previous road connected to this structure. This class definition creates a chain by linking
     *   Buildings (settlements & cities) to roads, and those roads to other previous roads, eventually leading to the root node. This is
     *   beneficial because to check if a building or road can be built, we only need to check if the single previous
     *   road has been built (because by definition, that means all other previous roads from the root node must also
     *   be built). This additionally lets us loop through the connected roads backwards, which is needed for questions.
     *
     * @field previousBuilding - (Building):
     *   Settlements and Cities have an additional field required to see if they can be built, which is
     *   checking if the previous largest point city/settlement was built. This field simply links to that
     *   previous building.
     */

    // The previous road connecting to this building
    public Road previousRoad;

    // The building preceding this one in points
    public Building previousBuilding;

    /**
     * The Default Constructor for the Building class that applies default values.
     * All values are set to 0, as this simply calls the default structure constructor.
     * It is recommended that you do NOT use this constructor.
     */
    public Building(){ super();}

    /**
     * The Constructor for the Building class that specifies values.
     *
     * @param value (int) The point value of this structure
     * @param type (structureType) The type of structure (city or settlement)
     * @param previousRoad (Road) The first road connected to this building
     * @param previousBuilding (Building) The previous building whose point value precedes this one
     */
    public Building(int value, structureType type, Road previousRoad, Building previousBuilding){
        super(value,type);
        this.previousRoad = previousRoad;
        this.previousBuilding = previousBuilding;
        // Ensure that building class is only used for settlements and cities (roads and knights have other constructors)
        assert type == structureType.SETTLEMENT || type == structureType.CITY: "Building type is not instantiated as City or Settlement";
    }

    /**
     * Given a building's value, and the type of building (settlement/city), return the building object that contains that value
     * This also requires a game to be in play, so a Game() is required as an input.
     * This is used to reference stuff for other tasks, so inputs "C20" -> getBuildingFromValue(20, CITY, currentGame)
     *
     * @param value (int) The point value of this structure
     * @param type (structureType) The type of structure (city or settlement)
     * @param game (Game) The game of Catan currently at play
     */
    public static Building getBuildingFromValue(int value, structureType type, Game game){
        Building[] buildingList = (type == structureType.CITY) ? game.cityNetwork : game.settlementNetwork;

        for (Building building : buildingList) {
            if (building.value == value) return building; // return first matching value
        }
        return null; // no building with desired value could be found
    }
}
