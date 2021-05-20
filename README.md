# Maze Generator

Maze Generator is a JavaFX application that can generate rectangular mazes of custom sizes and solve them.

### [Maze Algorithms](https://github.com/Imericxu/Maze-JavaFX/tree/master/src/main/java/imericxu/mazefx/core/maze_algorithm)

* Recursive Backtracker
* Prim's Algorithm
* Wilson's Algorithm
* Kruskal's Algorithm

### [Solve Algorithms](https://github.com/Imericxu/Maze-JavaFX/tree/master/src/main/java/imericxu/mazefx/core/solve_algorithm)

* Trémaux
* A*
* Breadth First Search

## Preview

<img alt="Main Window" src="https://raw.githubusercontent.com/Imericxu/Maze/assets/Main%20Window.png" width="350" />
<p>
  <img alt="Generated Maze" src="https://raw.githubusercontent.com/Imericxu/Maze/assets/Generated%20Maze.png" width="400" />
  <img alt="Generated Maze with Solution" src="https://raw.githubusercontent.com/Imericxu/Maze/assets/Generated%20Maze%20with%20Solution.png" width="400" />
</p>

## Execution

Use Maven to build and run the project.

1. Open terminal and cd to folder containing 'pom.xml'
2. `mvn compile`
3. `mvn exec:java -Dexec.mainClass=imericxu.mazefx.Main`

## Roadmap

* Add new algorithms
* Implement different-shaped mazes (i.g., delta, sigma)
* Implement zooming in and out
