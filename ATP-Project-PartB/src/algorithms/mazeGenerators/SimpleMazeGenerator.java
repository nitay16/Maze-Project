package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator implements Serializable {
    @Override
    public Maze generate(int row, int colum) {
        // if the input is invalid
        if(row < 2 || colum <2){return new Maze();}
        // Create the grid
        int[][] grid = new int[row][colum];
        // Random
        Random ran = new Random();
        // from the second row until the one before the last one
        for (int i_row = 1 ; i_row < row-1 ; i_row+=2)
        {
            for (int j_colum = 0 ;j_colum < colum ; j_colum++)
            {
                grid[i_row][j_colum]=1;
            }

        }
        //Check if the Number of row is even,if even than make all the last line.value = 1
        if (row%2==0)
        {
            for (int j_colum = 0 ;j_colum < colum ; j_colum++)
            {
                grid[row-1][j_colum]=1;
            }
        }
        // The Grid is now zebra/

        // open walls
        for (int i_row = 1 ; i_row < row-1 ; i_row+=2)
        {
            int int_random = ran.nextInt(colum);
            grid[i_row][int_random]=0;
        }
        // Create the Positions
        Position Po_Start = new Position(0,ran.nextInt(colum));
        Position Po_Goal;
        // if the row is even then the last row should be 1111....
        // then we need to create goalPosition
        if(row%2==0)
        {
            int location = ran.nextInt(colum);
            grid[row-1][location]=0;
            Po_Goal = new Position(row-1,location);
        }
        else
        {
            Po_Goal = new Position(row-1,ran.nextInt(colum));
        }
        //Create and return the maze
        return new Maze(grid,Po_Start,Po_Goal);
    }
}
