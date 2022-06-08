package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    /** Mazestate is an object the represent state in maze, so its position, and for that to bulid maze state
     * we need instance of position */
    private Position position_state;
    //cordinate x
    private int x;
    //cordinate y
    private int y;
    // diagonality it means if the state is diagonal
    private boolean diagonality;
    public MazeState(String state,Position postinstate) {
        super(state);
        this.position_state =postinstate;
        this.x= this.position_state.getRowIndex();
        this.y = this.position_state.getColumnIndex();
        this.diagonality =false;

    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
    /** hash code of maze state is the string of the coordinate */
    @Override
    public  int hashCode(){
        return this.toString().hashCode();
    }

    public Position get_position(){
        return this.position_state;
    }
    /** setter and getter of diagonality */
    public void setdiagonally(){
        this.diagonality=true;
    }
    public boolean getdiagonlity(){
        return this.diagonality;
    }



}
