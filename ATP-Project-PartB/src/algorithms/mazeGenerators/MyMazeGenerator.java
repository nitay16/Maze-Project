package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator implements Serializable {

    @Override
    public Maze generate(int rows,int col){
        // if the input is invalid
        if(rows < 2 || col <2){return new Maze();}
        // Create Random Val for The Build
        Random rand = new Random();
        //
        Position[][] point_list = new Position[rows][col];
        // Make the {0,0} as the Start Of the Maze
        point_list[0][0]= new Position(0,0);
        // The grid That we Put in the Maze Constructor
        int[][] arrayToMaze = new int[rows][col];
        // Make the {0,0} as the Start Of the Maze
        arrayToMaze[0][0]=0;
        // initiate array of position and the array of int to 1
        // except from the Start Point
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                //Start Point
                if (i == 0 && j == 0){continue;}
                //Make the maze with walls
                arrayToMaze[i][j] = 1;
                //Make Position For each cell in maze
                point_list[i][j]= new Position(i,j);
            }
        }
        /** the main algoritm in this loop:
         *  for each cell in Arraytomaze we take randomly the west or north cell
         *  and carve a passage trough it */
        for(int i=0;i<rows;i++){
            for(int j=0;j<col;j++) {
                //checking my neibor if any of them has value of zero.
              if(is_all_zero(arrayToMaze, i, j)) {
                    //check if our index is not out of bound to prevent exception
                    if ((islegal(i - 2, 0) && islegal(j - 2, 0))) {
                        Position[] temp_position = new Position[2];
                        temp_position[0] = point_list[i - 1][j];
                        temp_position[1] = point_list[i][j - 1];
                        int index_random = rand.nextInt(2);
                        // set value of an index in the array to zero
                        set_to_zero(temp_position[index_random], arrayToMaze);
                    if(index_random==0)
                        set_to_zero(point_list[i][j],arrayToMaze);
                    else{
                        set_to_zero(point_list[i][j],arrayToMaze);
                    }


                    }
                    if (!islegal(j - 2, 0) && islegal(i - 2, 0)) {
                        set_to_zero(point_list[i - 1][j], arrayToMaze);
                        set_to_zero(point_list[i][j],arrayToMaze);

                    }
                    if (!islegal(i - 2, 0) && islegal(j - 2, 0)) {
                        set_to_zero(point_list[i][j - 1], arrayToMaze);
                        set_to_zero(point_list[i][j],arrayToMaze);
                    }
                }
            }
        }
        // for the case if we have even number of rows and columns in the maze
        if(rows%2==0&&col%2==0){
            int random_index = rand.nextInt(2);
            Position[] temp_list= new Position[2];
            temp_list[0]=point_list[rows-2][col-1];
            temp_list[1]= point_list[rows-1][col-2];
            set_to_zero(temp_list[random_index],arrayToMaze);
            set_to_zero(point_list[rows-1][col-1],arrayToMaze);
        }
        return new Maze(arrayToMaze,point_list[0][0],point_list[rows-1][col-1]);

    }

    /** function that check the bound of index before we access him in the array*/
    private boolean islegal(int col,int raw){
        if(col<raw)
            return false;
        else
            return true;
    }
    /**function that check index the array. we check the neibor of the index if any of his neibor has "0" as valaue,
     * if its true we return false and then we are not change this index */
    private boolean is_all_zero(int[][] array_2d,int r,int l){
        if(islegal(r-1,0)&&islegal(l-1,0)){//raw bigger then zero
            if(array_2d[r-1][l-1]==0){
                return false;
            }
        }
         if(islegal(r-1,0)){
            if(array_2d[r-1][l]==0)
                return false;
        }
         if ((islegal(r-1,0)&&islegal(array_2d[0].length-1,l+1))){
            if(array_2d[r-1][l+1]==0) {
                return false;
            }
        }
         if (islegal(l-1,0)){
            if(array_2d[r][l-1]==0)
                return false;
        }
         if(islegal(array_2d[r].length-1,l+1)){
             if(array_2d[r][l+1]==0)
                 return false;
         }
        if(islegal(array_2d.length-1,r+1)&&islegal(l-1,0)){//raw bigger then zero
            if(array_2d[r+1][l-1]==0){
                return false;
            }
        }
         if (islegal(array_2d.length-1,r+1)){
            if(array_2d[r+1][l]==0)
                return false;
        }
         if(islegal(array_2d.length-1,r+1)&&islegal(array_2d[array_2d.length-1].length-1,l+1)){
            if(array_2d[r+1][l+1]==0)
                return false;
        }
        return true;


    }

    /** change value of an index in the array to zero*/
    public void set_to_zero(Position to_change,int[][] array_maze){
        array_maze[to_change.getRowIndex()][to_change.getColumnIndex()]=0;

    }


}
