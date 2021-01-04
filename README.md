# Maze Generator
Maze Generator is a JavaFX application that can generate rectangular mazes of any size and solve them.

#### Maze Algorithms
* Recursive Backtracker
* Prim's Algorithm
* Wilson's Algorithm

#### Solve Algorithms
* Trémaux
* A*
* Breadth First Search

## Examples
<img alt="Main Window" src="https://raw.githubusercontent.com/Imericxu/Maze/assets/Main%20Window.png" width="400" />
<p float="left">
  <img alt="Generated Maze" src="https://raw.githubusercontent.com/Imericxu/Maze/assets/Generated%20Maze.png" width="400" />
  <img alt="Generated Maze with Solution" src="https://raw.githubusercontent.com/Imericxu/Maze/assets/Generated%20Maze%20with%20Solution.png" width="400" />
</p>

## Execution
Use Maven to build and run the project.

1. Open terminal and cd to folder containing 'pom.xml'
2. `mvn compile`
3. `mvn exec:java -Dexec.mainClass=imericxu.zhiheng.mazegen.Main`

## Roadmap
* Add new algorithms
* Implement different-shaped mazes (i.g., delta, sigma)
* Implement zooming in and out
* Fix messy code
* Document code
