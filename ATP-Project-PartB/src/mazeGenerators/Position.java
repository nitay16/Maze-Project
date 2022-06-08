package mazeGenerators;

public class Position {
     private int row_num;
     private int col_num;

    public Position(int row_num, int col_num) {
        this.row_num = row_num;
        this.col_num = col_num;
    }

    @Override
    public String toString() {
        return "Position{" +row_num + col_num + '}';
    }
    public int getRowIndex(){
        return this.row_num;
    }
    public int getColumnIndex(){
        return this.col_num;
    }
}
