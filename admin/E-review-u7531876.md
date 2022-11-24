## Code Review


- Reviewed by: Hamish Hunter 
- Reviewing code written by: Jinhao Lu
- Component: Viewer.Java

# Comments
- The Viewer class is a very effective solution to the need to render the game board. The method Jinhao designed for rendering active game objects to their GUI position works perfectly. The methods within the class are currently only implemented in the GUI test scenario, however their design is easily adaptable to the game logic. The method of decoding the string boardstate is simple and easy to interprete, and once again, will be easy to implement with the game logic. The best feature of the class is the fact it holds the positions of every possible board object, and allows for them to be easily rendered on and off with only seven lines of code, making it very easy to use.
- The code isn't well documented, as the method descriptions are quite barren, and internal method code is lacking in comments describing the purpose of code sections.
- The structure of the code is logical and ordering of variables and methods makes sense.
- Naming conventions are easily interpretable and make sense. Code style is consistent and easy to read
- No noticeable errors or bugs are present in this class. 
