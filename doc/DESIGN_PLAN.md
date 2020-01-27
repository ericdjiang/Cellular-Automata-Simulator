# Simulation Design Plan
### Team 16
### Eric Jiang, Karthik Ramachandran, Kyle Brisky

## Introduction
The goal of this project is to create a program that can visually display and run various CA simulations. These simulations attempt to visualize various phenomena, like a burning forest fire, by changing the state of cells over time as a function of their neighboring cells.

The main design goal of this project is to implement the simulation in such a way that new simulations can easily be added without needing to make substantial changes to existing code. We also want to ensure that code doesn't need to constantly be duplicated to allow for various simulations.

To achieve this, there will be 3 components to our project. We will have a model that does manages the grid and is in charge of making changes to the grid and reading from the grid. A controller will access the grid through the model and will run the actual simulation, checking surrounding cells to update each cell's state. Lastly, we will have a visualization component that will access the grid and display the info on the screen in the GUI.

## Overview
This project will adhere to the conventions of Model, Controller, and View. The Application class is the heart of the entire simulation; this is where the step function is called multiple times to update the view (Visualizer) and call controller (Simulation) methods which handle game logic. The Visualizer class serves as the GUI which will hold the stage, scene, buttons/sliders. It is solely responsible for rendering the current state of each cell in the Model by calling getGrid() from the Grid class and rendering the color/location of each corresponding cell in the grid. Any user input caught by the Visualizer class is passed to the Application, in which the parameter change will be handled. The class Simulation serves as the base class where the simulation-specific game logic is handled. Each simulation will extend this base Simulation class, overriding abstract methods and containing simulation-specific methods if its own. The Simulation class will be responsible for handling the states of each cell in the Grid, checking for neighbors and updating states accordingly. The Model will reside in the Grid class, which contains either a 2-D array of Cells or a Graph of Cells as a data model. The Grid class will have a method called loadLayoutFile() to read in the parameters from the XML file and initialize the grid. Each simulation cell object in the grid will extend the Cell class and contain getter/setter methods to alter its state variables (which differ from simulation to simulation). In summary, the Application is the center of the program; it contains the visualizer, simulation, and grid. For every step of the simulation, the view is rerendered by calls to the visualizer and the model is altered by calls to the Simulation class, which alters the Cell objects contained in Grid.
![flowchart](./flowchart.JPG)

## User Interface
![GUI](./gui.jpg)

For the individual simulations there will always be the option to either run the simulation at full speed or to step manually between each cycle. This will be available with simple buttons below the display grid. Additionally there will be a button to reset the simulation.

There will also be simulation specific UI. This would be sliders that allow the user to change the starting conditions of the simulations before running or stepping through them. This section can have a lot of choices and places to expand on depending on the simulation and is where the most room for improvements can be found.

Long term there will be a menu with buttons that allow you to select which simulation you want to run and have the option to go back to this menu to select a different simulation mid program. 

Further improvements might include adding sliders that effect the program run speed or changing variable during a simulation session.

## Design Details
The Application class will be in charge of presenting the user with the visual interface. It will call the Visualizer class to display the results of the simulation and the Simulator class to actually update the cells. This will be handled in a step function such that the simulation runs and is visually updated at a set speed.

The Simulator class is an abstract class that will be extended by each simulation that is run. All simulations will have some features in common; for instance, they will find neighbors and set the starting configuration of a grid in identical ways. The difference between each Simulation child class (PercSim, FireSim, etc.) will be the formula with which cells' new states are found, allowing new Simulations to be easily added with only a single method needing to be modified. The Simulator class will also have a method to update the cells such that all cell states can be checked simultaneously before making changes to the cells.

The Grid class will maintain the grid that is being used. Potential data structures to create the grid include a 2D array, a 2D ArrayList, or a graph. Regardless, this choice will be hidden from the rest of the code by only allowing changes to the grid to occur through methods in the Grid class that update the grid. The exact method signatures can be found in the image in the Overview section.

The Visualizer class will take in a JSON object passed from the Simulator to ensure a unique color scheme and text for each type of simulation. It will call the accessor methods in the Grid class in order to display all the components of the Grid on the screen. As it is called by the step function, it will update the display to reflect whatever changes to the cells occurred over the last iteration.

The final component of the project is the Cell class. The Cell class will contain the elements that cells of all simulation types have in common: a variable to store state, a method to set state, and a method to get state. After that, though, these similarities end. Each cell will store different information depending on the simulation. For instance, a forest fire simulation may contain information about humidity or whether there is a tree there, while a social segregation simulation would not consider these variables. Thus, each simulation will have its own type of cell that extends the Cell class (for instance, FireCell). All cells will have a method called updateState() and setNextState(int state), allowing the Cell object to store a next state that can be updated after the Simulator has passed over the cells once. This will ensure that all updates occur simultaneously, adding a sort of synchronicity to the process.

## Design Considerations
By designating the Application as the "central" class which contains the Grid object, Visualizer object, and Simulation object, concerns were voiced regarding its violation of a code-smell principle: each class should only serve one purpose. However, the team consensus was that the Application class merely serves as a wrapper class, or origin of sorts, which holds the main components crucial to the design. By implementing Visualizer, Grid, and Simulation classes, the team chose to separate the View, Model, and Controller. The visualizer and simulation function independently of each other and are both dependent upon the Grid (the source of data). 

With respect to choosing to make Simulation an abstract class, we felt it important to have each simulation variant extend from this base class. After all, at a high level, each simulation in roughly the same fashion; every simulation will first call the overriden findNewState() method, which iterates through the cells in the Grid model. For each Cell object, the getNeighbors method (not abstract, as this behaves the same for all simulations) is called. Finally, the place in which the findNewState() method differs between simulations is where the simulation specific game logic is implemented (e.g. if neighbors is 4 for Game of Life, set the next state of the cell as 0). Since each simulation variant is functionally similar, we felt it important for their respective controller to extend the base Simulation class.

Regarding the decision to house the data structure representing the grid of cells in an entirely separate class Grid, we felt it was important to completely separate the Model. The sole instance variable of this Grid class is the 2D ArrayList of the grid itself (in which each element of the nested ArrayList is a Cell object). We spent lots of time considering potential options for the datatype, specifically oscillating between ArrayList and Graph. We chose to implement th 2D ArrayList due to its scalability and ease of rendering for the View. The issue with utilizing a Graph approach to represent the matrix is that each Node (Cell) within the graph must then contain pointers to each of its neighbors. Storing all these Nodes in a single ArrayList would prove unweildy and confusing, especially given that tailored algorithms would need to be implemented in order to correctly traverse and render the nodes in the correct order to the screen. Choosing a 2D ArrayList still offers scalability in terms of not setting concrete dimensions while offering a clearer structure to the Cell locations to be drawn on the screen.

The Simulation class and Visualizer class are both dependent on the Grid class, as both both of the former classes recieve the Grid object as a parameter in each call to step (to move forward the simulation by one epoch). This approach is potentially dangerous, as the interdependence violates the DRY principle. However, we felt this approach is also elegant in that any changes made to the grid during the Simulation game logic step will be directly reflected in the View (which draws directly from the current state of the grid object). An alternative design would be to have both only one class, such as the Visualizer, house the grid object. However, this would necessarily require the grid object to be passed down from the Visualizer, breaching the division between Model and View.

#### Components
Any simulation consists of 3 main components, the Model, View, and Controller. In our design, the Model is represented by the Grid class. The View is represented by the Visualizer class. The Controller is represented by the Simulation class, where each simulation extends this base abstract Simulation class. Finally, the Main class serves as the container for these classes.

### Use cases:
1. GameOfLifeSimulator.getNeighbors(cell) will be called to return a list of neighboring cells. Then, the state of these cells will be checked using GameOfLifeCell.getState() and the number of live cells will be counted. The count is not equal to 2 or 3, so GameOfLifeCell.setNextState(0) is called. Once all the rest of the cells have been checked, GameOfLifeSimulator.setNewStates() is called, calling GameOfLifeCell.updateState() on each cell to make this cell dead. Now, Visualizer.drawObjects() is called and the new changes are shown on the screen.
2. GameOfLifeSimulator.getNeighbors(cell) will be called to return a list of neighboring cells but will return a list of fewer than 4 cells since it is an edge cell. Then, the state of these cells will be checked using GameOfLifeCell.getState() and the number of live cells will be counted. The count is equal to 2 or 3, so GameOfLifeCell.setNextState(1) is called. Once all the rest of the cells have been checked, GameOfLifeSimulator.setNewStates() is called, calling GameOfLifeCell.updateState() on each cell to make this cell live. Now, Visualizer.drawObjects() is called and the new changes are shown on the screen.
3. GameOfLifeSimulator.setNewStates() is called, calling GameOfLifeCell.updateState() on each cell to push the new states into the cells, updating their current state to whatever new value they should have. Now, Visualizer.drawObjects() is called and these new changes are shown on the screen.
4. FireSimulator.probCatch is set to the value stated in the XML file. The implementation of FireSimulator.findNewState() will use that value while also looking at the neighboring cells when determining a given cell's next state.
5. Main.loadParametersFile will be called on the corresponding XML file, creating a new Simulator object for the appropriate simulation. Then, Main.start() will be called, beginning the step function on the Simulator and calling the Visualizer to update the GUI.

## Team Responsibilities

 * Team Member #1: Karthik
     * Primary: Simulator class
     * Secondary: Visualizer class
 * Team Member #2: Eric
     * Primary: Main class and Visualizer
     * Secondary: Simulator
 * Team Member #3: Kyle
     * Primary: Visualizer
     * Secondary: Simulator 

