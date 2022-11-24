# COMP1110 Assignment 2

## Academic Honesty and Integrity

Honesty and integrity are of utmost importance. These goals are *not* at odds
with being resourceful and working collaboratively. You *should* be
resourceful, you should collaborate within your team, and you should discuss
the assignment and other aspects of the course with others taking the class.
However, *you must never misrepresent the work of others as your own*. If you
have taken ideas from elsewhere or used code sourced from elsewhere, you must
say so with *utmost clarity*. At each stage of the assignment you will be asked
to submit a statement of originality, either as a group or as individuals. This
statement is the place for you to declare which ideas or code contained in your
submission were sourced from elsewhere.

Please read the ANU's [official position](http://academichonesty.anu.edu.au/)
on academic honesty. If you have any questions, please ask me.

Carefully review the statements of originality in the [admin](admin) folder
which you must complete at each stage.  Edit the relevant statement and update
it as you complete each stage of the assignment, ensuring that when you
complete each stage, a truthful statement is committed and pushed to your repo.

## Purpose

In this assignment you will *work as a group* to master a number of major
themes of this course, including software design and implementation, group
work, using development tools such as Git and IntelliJ, and using JavaFX to
build a user interface.  **Above all, this assignment will emphasize group
work**; while you will receive an individual mark for your work based on your
contributions to the assignment, **you can only succeed if all members
contribute to your group's success**.

## Assignment Deliverables

The assignment is worth 30% of your total assessment, and it will be
marked out of 30.  So each mark in the assignment corresponds to a
mark in your final assessment for the course.  Note that for some
stages of the assignment, you will get a _group_ mark, and for others
you will be _individually_ marked.  The mark breakdown and the due
dates are described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
page.

Your tutor will mark your work via GitLab, so it is essential that you carefully follow instructions for setting up and maintaining your group repository.
You will be marked according to whatever is committed to your repository at the time of the deadline.
You will be assessed on how effectively you use Git as a development tool.

## Problem Description

Your task is to implement in Java, using JavaFX, a board game called the
[Catan Dice Game](https://www.catan.com/dice-game).
Important: The Catan Dice Game comes with two variants, called "Island One"
and "Island Two": **you will implement the Island One game**.
You can find the game rules, and a PDF of the score sheet (game map) on the
web page linked above.
You can also find several video tutorials explaining how to play the game
on-line, for example <https://www.youtube.com/watch?v=DNZ4tXhnBFA>.

Catan Dice Game is a spin-off (one of many) of the "Settlers of Catan" game
series. There are even several Catan dice games (for example,
"Catan Dice Game XXL"). If you search for information or answers about the
game on-line, make sure what you read actually refers to the
**Catan Dice Game**, not one of the many other games in the series.

## Game Overview

Catan Dice can be played by one or more players. Each player has their own
score sheet (map), and players take turns rolling dice to obtain resources
and using those resources to build structures on their map. All things built
contribute to a player's score, and the player with the highest score at the
end of the game is the winner. (If playing solo, you simply attempt to
maximise your score.)

An overview of the game rules is given below. Use this, in addition to the
resources linked above. If anything is unclear, please consult Piazza for
clarification.

### The Game Board

![Island One score sheet](assets/island-one-score-sheet-annotated.png)

The game board (or map) has four types of buildable structures: Roads
(rectangles, on the edges between the hexagonal tiles), Settlements
(the small house-like shapes at tile corners), Cities (the larger house
shapes, also at tile corners) and Knights (the figures near the centre
of each tile). Initially, only one road (the filled one, with an arrow on)
is built; all other structures are unbuilt. As a player builds structures,
they mark them off on their map by filling them in.
All buildable structures on the map have a number on them, which is their
points value. All roads are worth 1 point, while settlements, cities and
knights have varied points values.

### Resources

There is six different types of resources: Ore, Grain, Wool (represented
by a sheep), Timber, Bricks and Gold. Each structure has a build cost, which
is the resources a player must have available to build it. For example,
building a Road consumes 1 Brick and 1 Timber. The build costs are summarised
at the top of the score card. Gold is not used to build anything, but can
be traded for other resources (see "Trades and Swaps" below).

### Player Turn

On their turn, a player first rolls the dice (there are six of them). Each
die is marked with the six different resources in the game.

After rolling, the player may select a number of dice to re-roll, and after
the first re-roll may again select a number of dice and roll a third time.

Next, the player can trade and/or swap resources (using Knights), and use
resources to build. A player can build more than one structure on the same
turn, as long as they have enough resources to build all of them. Building
actions are done in sequence, so that conditions required to build a
structure (see "Building Constraints" below) do not necessarily have to
be satisfied at the start of the turn. For example, a player may build a
Road to reach a Settlement, and then build that Settlement, on the same
turn. Also note that building and swapping can be interleaved: a Knight
can be used to swap a resource on the same turn that the knight was built
(but only after it has been built, of course).

When a player cannot take any more actions, they sum up the points values
of all structures built during the turn and add that to their running score.
Resources remaining at the end of the turn are lost (i.e., there is no
accumulation of resources between turns).
Important rule: If a player does not build any structure during a turn,
they get a penalty of -2 points for that turn, added to their running
score.

### Building Constraints

In addition to having the resources available, there are certain constraints
on the order in which structures can be built:

*   Roads, Settlements and Cities must form a connected network, starting
    from the initial Road.

*   Settlements, Cities and Knights must be built in order of increasing
    points value. For example, a player must have built both the 7-point and
    12-point Cities before they can build the 20-point City.

### Trades and Swaps

A player can change their resources in two ways: trading Gold for other
resources, at a rate of 2:1, or swapping resources using Knights.

To trade, the player simply exchanges two Gold for one resource of their
choice.

A Knight, once built, can be used once per game to swap a resource. The
Knights numbered 1 through 5 each allow the player to swap one available
resource of their choice for Ore, Grain, Wool, Timber and Bricks,
respectively. (This is shown by a resource icon below each Knight on the
game map.) Knight number 6 is wildcard: it can be used to swap one
available resource of the player's choice for one of any other resources
(including Gold).

### Game End

The game ends after all players have had 15 turns. The winner is the player
with the highest score.

## Encoding Game State

*More details of the `CatanDice` class and the string encoding used
 for interfacing with tests will be included here after D2B is complete.*

## Evaluation Criteria

It is essential that you refer to the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
to check that you understand each of the deadlines and what is
required.  Your assignment will be marked via tests run through git's
continuous integration (CI) framework, so all submittable materials
will need to be in git and in the *correct* locations, as prescribed
by the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/).

**The mark breakdown is described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
page.**

### Part One

In the first part of the assignment you will:
* Set up your assignment (Task #1).
* Create a design skeleton (Task #2).
* Implement parts of the testing interface to the game (Tasks #3 and #4).
* Implement a simple viewer that allows you to visualize game states (Task #5).
* Implement basic parts of the game logic (Tasks #6, #7 and #8).

An indicative grade level for each task for the [completion of part one](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2C) is as follows:

**Pass**
* Tasks #1, #2, #3 and #4.

**Credit**
* Task #5 *(in addition to all tasks required for Pass)*

**Distinction**
* Task #6, #7 and #8. *(in addition to all tasks required for Credit)*

### Part Two

Create a fully working game, using JavaFX to implement a playable
graphical version of the game in a 1200x700 window.

Notice that aside from the window size, the details of exactly how the
game looks etc, are **intentionally** left up to you. You may choose to
closely follow the look of the original board game, or you may choose to
present the game in totally different manner.

The only **firm** requirements are that:

* Your game must run using Java 17 and JavaFX 17.
* Your implementation must respect the specification of the game rules
  given here.
* Your game must be easy to play.
* Your game must runs in a 1200x700 window.
* Your game must be executable on a standard lab machine from a jar
  file called `game.jar`,

Your game must successfully run from `game.jar` from within another
user's (i.e.  your tutor's) account on a standard lab machine. In
other words, your game must not depend on any features not self-contained
within that jar file and the Java 17 runtime.

An indicative grade level for each task for the [completion of part
two](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/#D2F)
is as follows:

**Pass**
* Correctly implements all of the <b>Part One</b> criteria.
* Appropriate use of git (as demonstrated by the history of your repo).
* Completion of Task #10.
* Executable on a standard lab computer from a runnable jar file,
  game.jar, which resides in the root level of your group repo.

**Credit**
* _All of the Pass-level criteria, plus the following..._
* Tasks #9 and #11.

**Distinction**
* _All of the Credit-level criteria, plus the following..._
* Tasks #12 and #13.

**High Distinction**
* _All of the Distinction-level criteria, plus the following..._
* Tasks #14 and #15.

