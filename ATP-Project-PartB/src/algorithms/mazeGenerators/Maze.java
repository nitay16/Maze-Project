package algorithms.mazeGenerators;

import java.io.Serializable;

public class Maze implements Serializable {
    private  int[][] maze;
    private Position begin_position;
    private Position end_Position;
    private Position[][] positions_matrix;

    //default Constructor
    protected Maze() {
        this.maze =  new int[0][0];
        this.begin_position = new Position(0,0);
        this.end_Position = new Position(0,0);
    }


    //Constructor
    public Maze(int[][] maze,Position begin_position,Position end_Position) {
        this.begin_position = begin_position;
        this.end_Position = end_Position;
        this.maze = maze;

        //create position matrix
        Position [][] temp_array= new Position[this.maze.length][this.maze[0].length];
        for(int i=0;i<this.maze.length;i++){
            for(int j=0;j<this.maze[0].length;j++){
                temp_array[i][j]= new Position(i,j);
            }
        }
        this.positions_matrix=temp_array;
    }

    /**constructor of maze that gets bytes array*/
    public Maze(byte[] toDecompress){
        this.begin_position = new Position(toDecompress[2]*127+toDecompress[3],toDecompress[4]*127+toDecompress[5]);
        this.end_Position = new Position(toDecompress[6]*127+toDecompress[7],toDecompress[8]*127+toDecompress[9]);
        this.maze = new int[toDecompress[0]*127+toDecompress[1]][(toDecompress.length-10)/(toDecompress[0]*127+toDecompress[1])];
        int index =10;
        for (int i = 0; i <this.maze.length ; i++) {
            for (int j = 0; j <this.maze[0].length ; j++) {
                this.maze[i][j] = toDecompress[index];
                index++;
            }
        }
    }
    public Position[][] getPostion_array(){
        return this.positions_matrix;
    }
    //Func That return the Start and Goal Positions
    public Position getStartPosition(){
        return new Position(this.begin_position);
    }
    public Position getGoalPosition(){
        return new Position(this.end_Position);
    }



    //Maze Print
    public void print()
    {
        int row;
        int coulm;
        //Check if we Don't Create Default (Empty) Maze
        if (maze.length > 0) {
            row = this.maze.length;
            coulm = this.maze[0].length;
            System.out.print("{");
            //for each row
            for (int i = 0; i < row; i++) {
                System.out.print("{");
                //for each coulm
                for (int k = 0; k < coulm; k++) {
                    // Check if we get to StartPosition
                    if ((getStartPosition().getRowIndex()) == i && (getStartPosition().getColumnIndex()) == k) {
                        System.out.print("S");
                        //Check we dont print in the end
                        if (coulm > k + 1) {
                            System.out.print(",");
                        }
                        continue;
                    }
                    // Check if we get to GoalPosition
                    if (getGoalPosition().getRowIndex() == i && getGoalPosition().getColumnIndex() == k) {
                        System.out.print("E");
                        //Check we dont print in the end
                        if (coulm > k + 1) {
                            System.out.print(",");
                        }
                        continue;
                    }
                    System.out.printf("%s", maze[i][k]);
                    //Check we dont print in the end
                    if (coulm > k + 1) {
                        System.out.print(",");
                    }
                }
                //if we in the Not in last line of the maze
                if (row == i + 1) {
                    System.out.print("}");
                } else {
                    System.out.print("}\n,");
                }
            }
            System.out.print("}\n");
        }
    }


    public byte[] toByteArray(){
        //calculate the size of the bit array
        int byteArraySize = this.maze.length*this.maze[0].length + 10;
        byte[]compressedMaze = new byte[byteArraySize];

        int len_array = this.maze.length;
        int begin_X = this.begin_position.getRowIndex();
        int begin_Y = this.begin_position.getColumnIndex();
        int goal_postion_X = this.end_Position.getRowIndex();
        int goal_position_Y = this.end_Position.getColumnIndex();

        compressedMaze[0] =(byte)(len_array/127) ;
        compressedMaze[1] =(byte)(len_array%127) ;
        compressedMaze[2] =(byte)(begin_X/127) ;
        compressedMaze[3] =(byte)(begin_X%127) ;
        compressedMaze[4] =(byte)(begin_Y/127) ;
        compressedMaze[5] =(byte)(begin_Y%127) ;
        compressedMaze[6] =(byte)(goal_postion_X/127) ;
        compressedMaze[7] =(byte)(goal_postion_X%127) ;
        compressedMaze[8] =(byte)(goal_position_Y/127) ;
        compressedMaze[9] =(byte)(goal_position_Y%127) ;
        int index = 10;
        for (int i = 0; i <this.maze.length ; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                compressedMaze[index] = (byte)this.maze[i][j];
                index++;
            }
        }
        return compressedMaze;
    }
    // check if tha index is not out of bound
    private boolean islegal(int num1,int num2){
        if(num1<num2)
            return false;
        else
            return true;
    }
    // get the value of cell
    public int getvalue(int row,int col){
        if(!islegal(row,0)||!islegal(maze.length-1,row)||!islegal(col,0)||!islegal(maze[0].length-1,col)){
            return -1;
        }
        return this.maze[row][col];
    }


}