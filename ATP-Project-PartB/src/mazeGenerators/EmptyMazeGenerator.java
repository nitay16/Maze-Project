package mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int col) {
        int[][] array_maze= new int [rows][col];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                array_maze[i][j] = 0;
            }
        }
        Maze my_maze = new Maze(array_maze);
        return my_maze;
    }
}

