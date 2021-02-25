# API Lab Discussion
# Cell Society API Discussion

## Names and NetIDs
Andre Wang (jw542),
Donghan Park (dp239),
Samy Boutouis (sb590), 
Felix Jiang (fj32)

### Simulation API Motivation/Analogies
Only instance of backend in the frontend. 

#### External
Keeps it very clean with only having one object that represents all of the back end. Hides all of the implementation details. Sends required information to the front-end for display purposes.

#### Internal
All the other classes in the backend are the hidden implementation details of the Simulation. They represent a high level grid and simulation style, such as Wa-Tor or Percolation. By having Simulation hold a high level representation of a simulation, it can be easily replaced without changing any code as a result of the abstraction. 

### Simulation API Classes/Methods

#### External
makeStep()
getGridDisplay()
getCellDistribution()
getParameters()
setParameters()

#### Internal
Classes:
  * Grid
  * SimulationInitializer
  * SimulationStepper

Methods:
  * updateDisplayGrid()
  * updateDisplayGraph()

