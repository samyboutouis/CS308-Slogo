SLogo
====

This project implements a development environment that helps users write programs to draw using a frontEndTurtle.

Names: Andre Wang (jw542), Donghan Park (dp239), Samy Boutouis (sb590), Felix Jiang (fj32)

### Timeline

Start Date: 03/04/2021

Finish Date: 03/22/2021

Hours Spent: ~160 total

### Primary Roles

* Andre Wang (jw542)
    * Back-end
    * Note: Andre was unable to share his screen and zoom due to VPN/Bandwidth issues, so practically all pair programming code was written by Felix (me) but both members contributed equally.
* Donghan Park (dp239)
    * Front-end
* Samy Boutouis (sb590)
    * Front-end
* Felix Jiang (fj32)
    * Back-end

### Resources Used

- JavaFX FileChooser (loading/saving files)

### Running the Program

Main class: `main`

Data files needed:

- Property files with SLogo commands of each language in `src/resources/languages`
- Property file (called `DefaultPalette`) with default palette values in `src/resources/parameters`
- Property file (called `ButtonMethods`) with the names of event listener methods for UI buttons in `src/resources/reflection`
- Property file (called `CSS_IDs`) with the names of each UI's CSS IDs in `src/resources/stylesheets`
- CSS files for each supported color theme in `src/resources/stylesheets`
- Property file (called `Commands`) holding the number of parameters or brackets each command needs in `src/resources/parameters`
- Property file (called `Packages`) with the package name the command node is in (used for reflection) in `src/resources/packages`

Features implemented:

* enter commands to the turtle interactively by entering text commands
* see the results of the turtle executing commands displayed visually
* see errors that may result from entered commands in a user friendly way (i.e., not just printed to the console)
* set a background color for the turtle's display area
* set an image to use for the turtle
* set a color to use for the pen
* see commands previously run in the environment
* see variables currently available in the environment
* see user-defined commands currently available in the environment
* choose the language in which SLogo commands are understood
* access help about available commands
* users are able to open or close any view or put them in any order they want
* see current state of all turtle(s) (its ID, position, heading) and each turtle's pen (up/down, color, thickness) in real time
* see palettes of images and colors with their associated numeric values that can be referred to within new SLogo commands
* click to execute commands from the history or user-defined commands
* click to edit the value of user-defined variables
* click to toggle which turtles are currently active
* provide a way to move the current turtle(s) graphically
* provide a way to change the pen's current properties graphically
* allows users to create multiple workspaces that have separate:
    * turtle display areas with different numbers of turtles and states for each turtle and their pen
    * command histories, active variables, and user-defined commands
    * palettes for image shapes and colors
    * default command language and color theme
* each workspace preference (including starting background, command language, color theme, color and image palettes, starting number of turtles, file of code to load) can be loaded/saved via XML files
* allow users to save and read a file of valid SLogo functions/variables
* grouping: allow an unlimited number of parameters to commands by using parentheses
* recursion: allow user defined commands to include recursive calls
* variable scope: recursion variable scope is also considered and implemented
* exception handling: parser will give as detailed messages as it can to be thrown as an error, which is caught by front end and displayed to the user in a safe-format
  * handle a variety of bad input strings and provide information about failure, such as unrecognized command, unrecognized syntax, bad variables, etc.

### Notes/Assumptions

Assumptions or Simplifications:
* Users can only add to the color palette through the program
* Grouping command's output is sum of each individual commpand output
* Different instances of the workspace are created in separate windows
* By creating separate windows, users are able to freely
  open and close different workspaces and adjust how they want it on the screen
* Animations are implemented but user cannot control the speed or pause them

Interesting data files:
* spiral.SLogo
* race.SLogo
* circles_french.SLogo

Known Bugs:
* When loading in a new workspace from an XML preference file, even though the background color may update, the colorpicker itself may not update
* If an index is not in the palette and is called in a command, the program malfunctions
* Stack overflow occurs in some cases of recursion
* If turtle leaves the pane and comes back, the pane extends blocking parts of the toolbar of terminal

Extra credit:
* In the process of saving preferences, all the turtles on the screen along with their current states and line states are saved for reference
* Users are able to move around and open/close different display views using interactive graphical buttons at any time (doesn't require use of premade files)
* Besides recursion and grouping, we were also able to create a list of commands that seperated each turtle action, so the turtle could've been stepped through if a button was made for it
    * Additionally, the queries all print the querie value to the front end, except the front end method was not implemented for us to actually see it. The DisplayCommand class has this framework set up so the user could see the turtle queries through code as well

### Code Design Decisions

#### Ask
```
tell [ 1 2 3 ]
ask [ 1 3 ] [ fd 50 ]
```
1 2 3 should be active after code is run

#### Tell
```
tell [ 100 ]
```
Creates one turtle with id 100, rather than all turtles up to 100. Design decision made by the team here
We thought up cases where we only wanted to tell a single turtle something (to see their xcor for example)
So tell [ 2 ] xcor would provide us the xcor of second turtle, but if we didn't allow single tells to only create one turtle, issues may occur

#### ID
```
tell [ 1 2 3 4 ] 
id
ask [ 1 2 3 4 ] [ id ]
```
ID will return 1 in both cases because that is the first ID in the tell/ask list. ID only returns the id for the current turtle, so it will not loop around and return the last value in the list.

#### Multi-Turtle Variables
```
set :x 10
tell [ 1 2 3 ]
fd set :x sum :x 10
```
Each turtle will move a different amount forward, because for each turtle, the fd command will run each of its parameters again

#### Grouping
```
( sum 10 20 30 )
```
This is invalid grouping, as the number of arguments are not a multiple of 2, the number of parameters that sum command takes in. We did not decided to let the output of first be input of next, so any number of commands could work (in the example above, it would be 10 + 20, and then that output would be added to next value, so 30 + 30 - we did NOT do this). If we had implemented the grouping in this way, then the below set palette command from the example on the assignment website would be invalid, as each group of 3 outputs a 1 and takes the next 2, so there would be an invalid amount of commands
```
( setpalette 1 255 0 0 2 0 255 0 3 0 0 255 )
```
We decided to also have each command output be summed up in the final return value (so each command runs its parameters separately, but final return value is sum of all commands ran). This makes sense for SUM node of course, and perhaps even the FD and BK nodes, and even AND and OR nodes. However, behavior is not intuitive for nodes like DIFFERENCE or setpalette since we add all the input. We decided to add all the outputs because any other choice would not really satisfy the SUM example, and based on our code design, we did not want to make more case statements in the group node to determine how outputs should add. The purpose of our grouping node is to run the same command on multiple arguments all at once. This is satisfied. However, the output is not necessarily understandable (e.g. setpalette output is sum of indices), but we could've had the output be last executed command, except that would defeat the purpose of the grouping for sum. It is a hard balance but in the end these are the ideas we came up with for how grouping should be designed.

### Impressions

We think that there was more work to be done in the second half than in the first half. Had it been a more even split, we could have prioritized better. We also found that not knowing all the requirements up front helped us to think about the long term extendability and maintainability of our project. Implementing tests in our code also helped us uncover bugs in our application making the program more accurate. Working on the front-end was very challenging and required us to think about how flexible our code is. It required us to implement multiple design patterns such as the factory pattern or observer pattern to reduce dependencies between classes and clean our code.

Working on parsing this language really challenged code flexibility. It was too difficult to hardcode anything since there are just so many commands to implement and so many variations of each command that could have other commands as their input. The backend began with pair programming and I'm surprised we came up with our SlogoNode abstract class, as it has proven us well for the complete requirements and even the challenge ones. We had some difficulties with time zone and vpn issues, but overall I've learned a lot about managing a program that has so many features (really need to keep code DRY), and how important testing was for each feature since when we made big changes, we could just run our tests to make sure nothing broke. It is unfortunate that the pair programming did not switch computers, but I still saw the importance of pair programming and being on the same page in the back end.
