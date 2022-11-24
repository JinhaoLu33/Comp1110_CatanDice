## Code Review

- Reviewed by: Jinhao Lu
- Reviewing code written by: Samuel Mason-Cox
- Component: Resource.Java

# Comments

- The Resource class is useful to manipulate the resource states during the game. An array is used to record the
  resources. It has constructors to create the player's resource state and set the cost of building each structures. It
  also contains methods like subtract and add to update the resource state. The greaterThanEqualTo method is really
  handy to check the whether the resource is enough to build a structure. ------------------------
- The code is well documented. All the methods and variables are named properly and have nice readability.
- The overall method structure is clear and comprehensive. I only supplement a method to add a particular resource type
  for completing task 11.
- Naming conventions are easily interpretable and make sense. Code style is consistent and easy to read
- No noticeable errors or bugs are present in this class. Knight enum from costFromType might be changed to Joker.
