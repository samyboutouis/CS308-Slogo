## SLogo API Changes
### Team
Team 7
### Names
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590),
Felix Jiang (fj32)

### Changes to the Initial APIs

#### Backend External

* Method changed: parseInput()

   * Why was the change made?
      * Initially we planned for it to take in a string, and output a List<Command> objects. This works for single turtles, but once multiple turtles got involved, we needed a better class to encapsulate all the turtles, so we passed a turtleTracker into the method and return a turtle Tracker.
      * Even with one turtle, we needed some way to know the front end turtle's initial position and direction, so the method header had to be changed early on.

   * Major or Minor (how much they affected your team mate's code)
      * This was a minor change, because the front end only called this method in one spot, and this method delegated work to other backend calls, so there was really nothing to change besides the method header and the class that calls the method.
      * This change did require a full team meeting since it required changing code in both front and back end, so that may be the most burdensome part of this change.

   * Better or Worse (and why)
      * Definitely better. By receiving a turtle tracker interface and returning a turtle tracker interface, we introduce a lot more flexibility into the parse input method. The class can represent a lot of things, and we just have to define the correct getter methods for the front end to be able to use that information.

* Method changed: GetUserDefinedCommandsInString()

   * Why was the change made?
      * Initially there was no method to receive the user defined commands. Adding this method for the front end to call wasn't too bad, but accuratley creating this map required some thought. This was added because the front end needed a way to display all the user defined commands.

   * Major or Minor (how much they affected your team mate's code)
      * Pretty minor change to the front end, since they just had a new method to call. For the backend, we had to figure out how to create the string of what the user typed that corresponds to the defined command. In the end, since we had a great abstract SlogoNode, we could just add a method there that kept track of each name and build it up by calling all the child nodes.

   * Better or Worse (and why)
      * Much better because our program is able to leverage the SlogoNode abstraction, so for new features we know where to first look. There are so many commands such that if we had added a getter to every single one, it would've taken a while, but all we had to do was add a method in the super class and every command inherited it.


#### Backend Internal

* Method changed: ConvertCommand

   * Why was the change made?
      * Rather than converting a single string to a Command, we decided to build a tree of command nodes and then build the commands from there. Not every command corresponds to creating a command object, so by having this task delegated into each node, we can selectivity determine which commands create command nodes.

   * Major or Minor (how much they affected your team mate's code)
      * We created this change early on luckily, because we were considering the best way for a command to know its parameters. After lecture, it was determined that building some sort of tree could help. This was a very minor change because we made it early, so the plan is important. Otherwise, it could've been a much longer change.

   * Better or Worse (and why)
      * Definitely a better change because it made our program able to parse Slogo code and provided the flexibiilty for grouping and recursive commands.


* Method changed: getMovement()

   * Why was the change made?
      * We initially believed that the only kinds of commands on the turtle would be moving it around and changing direction, so this method would be responsible for creating objects that will do those tasks on the turtle. However, it turns out there are a lot more types of changes to the turtle, so we had to generalize.

   * Major or Minor (how much they affected your team mate's code)
      * Instead of one command doing this change, we delegated each node to be responsible for creating the command objects, so nodes that don't create objects have less of a task during the tree processing process. By doing it this way, it was more organized to see where these objects are created and sticks more to the single responsibily principle. It was a minor change mostly due to us discovering the many more commands we had to implement early on.

   * Better or Worse (and why)
      * Allowed a more clear understanding of what a command does, sticking to SRP and allowing for new commands to be quickly integrated into the program, rather than needing to make changes in many places. Overall a better change.


#### Frontend External

* As originally planned, no front-end external APIs were added. No public methods in the front-end were ever called by the controller or the back-end.

#### Frontend Internal

* Method changed: display(Movement movement) --> doCommand(Turtle turtle)

   * Why was the change made?
     Initially, we planned on letting each turtle object have a method called `display()` that updated the turtle's states (position, orientation, pen status, etc.) depending on the command called. However, we instead created an interface called `Command` that represented all of the supported SLogo commands. The interface also had a `doCommand()` method that took in the turtle object to apply the command on as the parameter. Doing this allowed the turtle to always call the same method for any command called, improving versatility by implementing reflection.

   * Major or Minor (how much they affected your team mate's code)
     This was a major change from the initial plan; originally, the `display()` method could only be used to update the turtle's space-related parameters, such as position, orientation, etc. Making the design of the commands universal allowed the program to support all types of different commands that had to do with other things, such as the palette, setting variables, changing colors, etc.

   * Better or Worse (and why)
     Much better since this allowed the program to support much more types of commands, making the code more flexible regarding the ability to add new commands.

* Method changed: public void updateTurtle(Movement movement) -> public void forward(double pixels)

   * Why was the change made?
     During the initial plan, we planned on the back-end calculating the new positions and orientation of the turtle, but instead, we decided to implement these changes in the front-end and have the back-end simply call these methods while parsing the user-created code. This simplified the code in the back-end and the front-end internal API for moving the turtle became much more comprehensive and complete.

   * Major or Minor (how much they affected your team mate's code)
     This was a major change from the intial plan. Since this API change was made relatively at the beginning of the development process, much code did not need to change. However, we had to change our plan to accommodate the new way of calling methods on the turtle and developing a robust turtle API. With this API change made at the beginning, however, the back-end was able to easily adjust their code to fit the requirements of the new API.

   * Better or Worse (and why)
     This API change is better because it reduces the amount of work the back-end had to take on and instead, made the internal turtle API much more robust. This was helpful later on in the project when creating GUI components that modify the turtle's positions purely on the front-end. Instead of relying on the back-end to move the turtle, having an extended turtle API allowed for more flexibility when managing the turtles on the front-end.
