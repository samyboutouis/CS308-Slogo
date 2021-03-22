SLogo
====

This project implements a development environment that helps users write programs to draw using a frontEndTurtle.

Names: Andre Wang (jw542), Donghan Park (dp239), Samy Boutouis (sb590), Felix Jiang (fj32)

### Timeline

Start Date: 03/04/2021

Finish Date: 

Hours Spent:

### Primary Roles


### Resources Used


### Running the Program

Main class:

Data files needed: 

Features implemented:



### Notes/Assumptions

Assumptions or Simplifications:

Interesting data files:

Known Bugs:

Extra credit:

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
ID will return 1 in both cases because that is the first ID in the tell/ask list. 

#### Multi-Turtle Variables
```
set :x 10
tell [ 1 2 3 ]
fd set :x sum :x 10
```
Each turtle will move a different amount forward, because for each turtle, the fd command will run each of its parameters again


### Impressions

