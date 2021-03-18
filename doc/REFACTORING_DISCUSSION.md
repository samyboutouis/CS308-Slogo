## Refactoring Lab Discussion
### Team Number: 7
### Names
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590),
Felix Jiang (fj32)

We weren't able to access the static analysis tips, so these issues are discovered by looking through our code, rather than using a tool.

#### Method or Class
* CommandReader buildTree Method
    * First of all, this method is more than 20 lines long
    * We were able to utilize reflection to create a majority of our command nodes, but some command nodes take in different items, and the node for implementing user defined commands requires a bit more logic besides just creating the node.

#### Workspace
* setupDisplays method
    * Method is over 20 lines long, very verbose
    * Lack of abstraction for creating all the different types of views, causing code to be very long and inflexible
    * Adding a style class to every subclass in this one method, should maybe redistribute the code

### Refactoring Plan

* What are the code's biggest issues?
    * Long methods with repeated code
    * Issues in communication between the front-end and back-ends
    * Code is relatively inflexible to adding more multiple turtles
* Which issues are easy to fix and which are hard?
    * The long method issue is easy to fix by implementing reflection, making the class creation process less verbose
    * Changing communication between front end and back end requires changing quite a few places of code since both front end and backend refer to these objects. Requires the entire team to be on board with how communication should work.
* What are good ways to implement the changes "in place"?
    * Replace explicit constructor code with reflection to change in place
    * Work on one class at a time if possible to isolate refactoring changes

### Refactoring Work

* Workspace class: instead of all panes behind initialized in the Workspace class, each display view creates its own panes within the class and holds a public getter method for other parent classes, such as the ViewLayout or Workspace.

* CommandReader buildTree: We could create another class with a public method that returns a node given the regex symbol and the string. That way the switch statement could be in another class, and that class could have different methods for each switch case in order to reduce the lines in a method and organize the code in case some switch cases get out of hand