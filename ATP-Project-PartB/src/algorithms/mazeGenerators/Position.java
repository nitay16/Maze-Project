package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
     private int row_num;
     private int col_num;

    public Position(int row_num, int col_num) {
        this.row_num = row_num;
        this.col_num = col_num;

    }
    // copy constructor
    public  Position(Position tocopy){
        this.row_num= tocopy.row_num;
        this.col_num= tocopy.col_num;
    }

    @Override
    public String toString() {
        return "Position{"+" row num:" +row_num +" col num "+ col_num + '}';
    }
    public int getRowIndex(){
        return this.row_num;
    }
    public int getColumnIndex(){
        return this.col_num;
    }
}
