package comp1110.ass2;

   /**
     * Authors: Sam Mason-Cox, Hamish Hunter, Jinhao Lu
     */


public class Knight extends Structure {
    /**
     * The Knight class inherits from structure, but contains all additional behaviour for Knight.
     *
     * This includes keeping track of the previously built knight, and the resource value this knight holds.
     *
     * @field tradeResource - (Resource):
     *   The resource value given to the knight for trading.
     *
     * @field previousKnight - (Knight):
     *   Knights require an additional field to see if they can be built, which is checking if the previous
     *   largest point knight was built. This field simply links to that previous knight.
     *
     * @field used - (boolean):
     *   Declare if this knight has been "used" yet (true if the player has traded with the knight during this game)
     *
     * @field isWildcard - (boolean):
     *   Declare if this is the 6th Knight, which is a wildcard and trades for any resource (including gold)
     *
     */

    // The resource this knight holds for trading
    public Resource tradeResource;

    // The knight preceding this one in point value
    public Knight previousKnight;

    // Defines if this knight is the wildcard (6th knight)
    public boolean isWildcard;

    // Defines if the player has traded with the knight during this game
    public boolean swap;

    /**
     * The default constructor for the Knight class.
     *
     * This constructor sets most values to 0 (including value, tradeResource and previousKnight (null)) except for cost.
     * It is advised that you do NOT use this constructor.
     */

    public Knight() {
        super(0, structureType.KNIGHT);
        this.tradeResource = new Resource();
        this.previousKnight = null;
        this.isWildcard = false;
        this.swap = false;
    }

    /**
     * The Constructor for the Knight class that specifies values
     *
     * This is used to define the trade resource, and previous knight that precedes in point value.
     *
     * @param value (int) The point value given to the player when building this knight
     * @param tradeResource (Resource) The amount and type of resource to give to the player when traded
     * @param previousKnight (Knight) The previous knight preceding this one in point value.
     */
    public Knight(int value, Resource tradeResource, Knight previousKnight) {
        this(); // set swap and wildcard to false via constructor chain;
        this.value = value;
        this.tradeResource = tradeResource;
        this.previousKnight = previousKnight;
    }

    /**
     * The Constructor for the Knight class that specifies ALL specific values
     *
     * This is used to define the every value of the knight object. This is for highly specific things.
     *
     * @param value (int) The point value given to the player when building this knight
     * @param tradeResource (resourceType) The single type of resource to give to the player when traded
     * @param previousKnight (Knight) The previous knight preceding this one in point value.
     * @param swap (boolean) Declares if the player has traded/swapped with the knight during this game
     * @param isWildcard (boolean) Declares if this is the wildcard knight with unique trade behaviour.
     */
    public Knight(int value, Resource tradeResource, Knight previousKnight, boolean swap, boolean isWildcard) {
        this(value, tradeResource, previousKnight); // constructor chaining
        this.swap = swap;
        this.isWildcard = isWildcard;
    }

    // Getters and Setters

    /**
     * Set the wildcard status on a knight
     * @param isWildcard (boolean) set if this is a wildcard knight or not
     */
    public void setWildcard(boolean isWildcard) {
        this.isWildcard = isWildcard;
    }

    /**
     * Set the swap status on a knight
     * @param isSwapped (boolean) set if this is knight has been swapped or not (true for Jokers)
     */
    public void setSwap(boolean isSwapped) {
        this.swap = isSwapped;
    }

    /**
     * Get the current swapped status on a knight
     * @return (boolean) if the knight has been traded with yet.
     */
    public boolean getSwap() { return this.swap; }
}
