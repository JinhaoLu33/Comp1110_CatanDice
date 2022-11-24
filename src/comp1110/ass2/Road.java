package comp1110.ass2;
   /**
     * Authors: Sam Mason-Cox, Hamish Hunter, Jinhao Lu
     */


public class Road extends Structure {
    /**
     * The Road class inherits from structure, but contains all additional behaviour for Roads.
     *
     * This includes keeping track of the road preceding it.
     *
     * @field previousRoad - (Road):
     *   The previous road connected to this structure. This class definition creates a chain by linking
     *   Buildings (settlements & cities) to roads, and those roads to other previous roads,
     *   eventually leading to the root node. This is beneficial because to check if a building or road can be built,
     *   we only need to check if the single previous road has been built
     *   (because by definition, that means all other previous roads from the root node must also
     *   be built). This additionally lets us loop through the connected roads backwards, which is needed for questions.
     */

    // The road preceding this one
    public Road previousRoad;

    /**
     * The default constructor for the Road class.
     *
     * This constructor uses default road costs and point value, but defines no connecting roads.
     */
    public Road() {
        super(0, structureType.ROAD);
        this.previousRoad = null;
        this.built = false;
    }

    /**
     * The constructor for the Road class that specifies values.
     *
     * @param previousRoad (Road) the road preceding this one. It creates a recursive definition that links all roads like a tree
     */
    public Road(Road previousRoad) {
        super(1, structureType.ROAD);
        this.previousRoad = previousRoad;
        this.built = false;
    }
}
