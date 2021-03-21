# Communication Plan

## What Does FrontEnd pass to Backend?
FrontEnd will pass us their tracker, but the backendTurtleTracker version

## What Does BackEnd pass to FrontEnd?
BackEnd will pass a Map of turtle id (integer) and List<Command> corresponding to each turtle
  * For future: hide implementation of map by passing a class to the frontend that represents what we parsed
    * For now, we don't see the front end needing anything else besides the map of id and list of commands
    
Frontend will read the map and if any ID doesn't exist already, it first creates that turtle before calling each list of commands

## Edge Stuff Handled
Circle around turtle
  * Handled in TellCommand that will be created with each Tell command for every turtle, turning it on or off
  * Each TellCommand also calls the safeTurtleTracker command that adds or removes a turtle from active list

Commands that aren't associated to a specific turtle but affect the front end (so we need to get it to them somehow)
  * If we see a setbg in a tell, we add it to all turtles. That is what the code tells us to do
  * SetBGCommand will have access to safe turtle and that can call the change background command

SetPalette rgb values are integer between 0 and 255
  * Backend parser will handle this, and if it's not, will throw an error that front end already is capable of displaying

```
tell [ 1 2 3 ]
fd 50
setbg green
tell [ 1 3 5 ]
bk 50
setbg blue

Map
1 : fd 50 setbg green bk 50
2 : fd 50 setbg green
3 : fd 50 setbg green bk 50 setbg blue
5 : bk 50 setbg blue
```