package algorithms.mazeGenerators;

import java.io.Serializable;

public class EmptyMazeGenerator extends AMazeGenerator implements Serializable {
    @Override
    public Maze generate(int rows, int col) {
        int [][] array_to_maze = new int[rows][col];
        for(int i=0;i<rows;i++){
            for(int j=0;j<col;j++){
                array_to_maze[i][j]=0;
            }
        }
        //return empty maze
        return new Maze(array_to_maze,new Position(0,0),new Position(rows-1,col-1));
    }
}

