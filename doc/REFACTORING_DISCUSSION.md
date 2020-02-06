Our main priorities were eliminating any direct references to our grid (which existed as myModel.getGrid()), as this
required other classes to be aware of the type of data.

We also want to move getNeighbors to the model class as it requires awareness of the shape of the cells, which should
also be a Model specific piece of information. This will also help us eliminate the most repeated code throughout our
project.

We will also add constants to eliminate the frequency and occurrence of "magic numbers" and strings, especially in the
main class when dealing with simulator selection.

We will eliminate the Fish and Shark subclasses and instead make a single PredatorPreyCell cell to allow for improved 
accuracy in the simulation while ensuring our class heirarchy actually makes sense.