# n-puzzle-solver


<br><br>
Take n-puzzle arranged in random order from text file, Solve it by using the A* algorithm to find the optimal way to the solution,<br>
then Present the shortest path to the goal.<br>
We used two heuristic algorithms Manhattan and hamming.<br><br>


We designed the project as four classes: Test, Solver, State, and Node. <br>
Here is a brief about each class.<br>

● Node Class<br>
this class is considered as an n-tree data structure. <br>
This n-tree structure can track the child to its root and allows the parent to have many children.<br>
So, these features help us to find the path from the goal state to the initial state as we save the states into this data structure.<br><br>

● State Class<br>
The State class is a core class of this program. <br>
Inside this class, we implement functions and attributes related to the heuristic algorithms and state of n-puzzle. <br><br>

● Solver Class<br>
The core function of this class is findSloution() to solve the puzzle,<br>
which means finding the optimal path from the initial state to the goal state. <br><br>

● Test Class<br>
The first purpose is reading the puzzle from text input and saving it into a state object.<br>
It is in method readBoard(String).<br>
The second one is creating an object of type Solver and calling several methods from class Solver to solve the puzzle<br> and print information about it.<br>
It is inside the main(string args[]).<br><br>



UML Class Diagram:<br>

<img width="609" alt="Screen Shot 2022-03-20 at 10 42 56 PM" src="https://user-images.githubusercontent.com/74451443/159179886-7f33c404-3263-4fe4-b27e-9e1f98e5ef09.png">


<br><br>The result:<br>
We compare the given two heuristics depending on time, and we conclude the manhattan distance is faster than hamming.<br>
The table below shows puzzles of different sizes and execution times.<br>

<img width="586" alt="Screen Shot 2022-03-20 at 10 44 02 PM" src="https://user-images.githubusercontent.com/74451443/159179991-cc889469-97cf-4afa-857a-6b71c7a3f18f.png">


<br><br>
There are many ways to solve n-puzzles, but we are concerned about optimal ways depending on time. <br>
We tried to reduce the number of loops and the complexity as much as possible to make a good performance so that it has <br>
a short execution time. We choose the Manhattan heuristic because that heuristic gives the <br>
right solution with less time.<br>

Here is a <a href="">pdf</a> file describing the project in detail.

Reem Algabani, Hannan alasker
