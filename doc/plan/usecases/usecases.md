# Use Cases


1. The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the command is added to the environment's history.
```java
// private method used to handle user input takes in the string of the command box and sets it as userInput
turtleInfo = createMovement(x, y, angle, isPenDown);
try {
  parseCommand(userInput, turtleInfo);
} catch (Exception e){
  showError(e.getMessage());
}
```
    
2. The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
```java
// private method used to handle user input takes in the string of the command box and sets it as userInput
turtleInfo = createMovement(x, y, angle, isPenDown);
try {
  parseCommand(userInput, turtleInfo);
} catch (Exception e){
  showError(e.getMessage());
}
```

3. The user sets a variable's value and sees it updated in the UI's Variable view.
```java
map.put(String variableName, int value);
updateVariable(map);
```

4. The user sets the pen's color using the UI so subsequent lines drawn when the turtle moves use that color.
```java
try {
  Color penColor = Color.web(receivePenColor());
  changePenColor(penColor);
} catch (Exception e) {
  showError(e.getMessage());
}
```

5. The user types in multiple commands on different lines.
```java
// private method used to handle user input takes in the string of the command box and sets it as userInput
turtleInfo = createMovement(x, y, angle, isPenDown);
try {
  parseCommand(userInput, turtleInfo);
} catch (Exception e){
  showError(e.getMessage());
}
```

6. The user sets the turtle display area's background color.
```java
try {
  Color backgroundColor = Color.web(receiveBackgroundColor());
  changeBackgroundColor(backgroundColor);
} catch (Exception e) {
  showError(e.getMessage());
}
```

7. The user uploads an image to represent the turtle.
```java
try {
  Image image = getImage(FileChooser fileChooser);
  setImage(image);
} catch (Exception e) {
  showError(e.getMessage());
}
```

8. The user creates their own command.
```java
// private method used to handle user input takes in the string of the command box and sets it as userInput
turtleInfo = createMovement(x, y, angle, isPenDown);
try {
  parseCommand(userInput, turtleInfo);
} catch (Exception e){
  showError(e.getMessage());
}
// internally in the backend, this will require more calls to create a new command object that has a series of existing commands
```

9. The user uses a variable that they have not defined.
```java
// private method used to handle user input takes in the string of the command box and sets it as userInput
// in front end:
turtleInfo = createMovement(x, y, angle, isPenDown);
try {
  parseCommand(userInput, turtleInfo);
} catch (Exception e){
  showError(e.getMessage());
}

// in back end:
convertCommand(String userInput);
// throws exception since variable was undefined, front end catches it and displays to the user
```

10. The user chooses the language in which the SLogo commands are understood.
```java
String properties = // private event handler method will set this value
setLanguage(properties);
```

11. The user opens a reference file for help
```java
openReference();
```

12. The user types in an incorrectly formatted hex code for the pen color.
```java
try {
  Color penColor = Color.web(receivePenColor());
  changePenColor(penColor);
} catch (Exception e) {
  showError(e.getMessage());
}
```

13. The user chooses to clear the turtle's display area for a new drawing.
```java
clearScreen();
```

Note: only three members were present when working on the plan
