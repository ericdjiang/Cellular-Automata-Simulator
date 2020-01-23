# kr211, kb325, edj9
# Inheritance Review

The most overarching way to implement inheritance would be to define an abstract Sprite class that is extended by Bricks, Balls, Powerups, Paddles, and any other image or object in the game. This way, any new objects or features can easily be added. We also considered having the Ball and PowerUp class inherit some RoundObject class, such that new bouncers or powerups can be easily added without needing to constantly copy the code that defines how they bounce off walls, bricks, and paddles, or how they are affected at the bottom of the screen.


# Simulation High Level Design

We will use an MVC design to handle which object can see what. Each cell will only be able to view itself and won't be aware of its surrounding neighbors. Calls to other cells will be handled by the controller class.

The Controller will use the simulation rules and the surrounding cells to modify each cell's state. Each simulation variant will be a class that inherents from the base Simulation class. Given the specific rules of the specific simulation, methods that check for changes between neighboring cells are run. 

Model: There will be a 2D array which represents the Grid. In each space of the grid, a Cell object will be stored. The Model class will contain private methods to read an XML configuration file and generate the Cell objects in their corresponding indices.

```
class Grid

//data structure storing cells

//read in layout from XML, generate cells in correct locations
public void loadLayoutFile()

// public void generateCell(int x, int y, Cell cell)

//modify grid
public void setCell(int x, int y, Cell cell)

//access grid
public Cell getGridCell(int x, int y)
```

```
class Cell

int state

public void setState(int state)

public int getState()
```

```
abstract class Simulation
Grid grid

public void simulate()
private Grid makeNewGrid()
private abstract void findNewState()
```

```
class View

//visualize grid
private void makeDisplay(Grid grid)
```