package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.ArrayList;
/** in this class we build the searchable maze instance. the constructor needs to get Maze object the then
 * we build 2-d array of the maze states*/
public class SearchableMaze implements ISearchable , Serializable {
    private Maze maze;
    private MazeState[][] mazestate_matrix;
    public SearchableMaze(Maze maze){
        if(maze==null){return;}

        this.maze= maze;
        Position[][] temp_postion_mat = this.maze.getPostion_array();
        //initiate 2-d array for the maze state like the size of the maze
        this.mazestate_matrix =new MazeState[temp_postion_mat.length][temp_postion_mat[0].length];
        for(int i=0;i<temp_postion_mat.length;i++){
            for(int j=0;j<temp_postion_mat[0].length;j++){
                mazestate_matrix[i][j]=new MazeState(temp_postion_mat[i][j].toString(),temp_postion_mat[i][j]);
            }
        }
    }
    @Override
    public AState getStartState() {
        return mazestate_matrix[0][0];
    }


    @Override
    public AState getGoalState() {
        return mazestate_matrix[maze.getGoalPosition().getRowIndex()][maze.getGoalPosition().getColumnIndex()];

    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {

        if(state==null)
            return null;
        ArrayList<AState> array_list_state= new ArrayList<>();
        Position position_temp = ((MazeState)state).get_position();
        int row_state= position_temp.getRowIndex();
        int col_state= position_temp.getColumnIndex();
        AState temp_mazestate;

        //check all the cells except the diagonal cells
        if(this.maze.getvalue(row_state+1,col_state)==0&&!mazestate_matrix[row_state+1][col_state].get_visit()){
            temp_mazestate= mazestate_matrix[row_state+1][col_state];
            temp_mazestate.set_visited();
            array_list_state.add(temp_mazestate);

        }
        if(this.maze.getvalue(row_state,col_state-1)==0&&!mazestate_matrix[row_state][col_state-1].get_visit()){
            temp_mazestate =  mazestate_matrix[row_state][col_state-1];
            temp_mazestate.set_visited();
            array_list_state.add(temp_mazestate);
        }
        if(this.maze.getvalue(row_state,col_state+1)==0&&!mazestate_matrix[row_state][col_state+1].get_visit()){
            temp_mazestate= mazestate_matrix[row_state][col_state+1];
            temp_mazestate.set_visited();
            array_list_state.add(temp_mazestate);
        }
        if(this.maze.getvalue(row_state-1,col_state)==0&&!mazestate_matrix[row_state-1][col_state].get_visit()){
            temp_mazestate= mazestate_matrix[row_state-1][col_state];
            temp_mazestate.set_visited();
            array_list_state.add(temp_mazestate);
        }

        //check diagonal step if its legal
        if(check_diagonal_step(row_state+1,col_state+1)&&(check_diagonal_step(row_state+1,col_state)||check_diagonal_step(row_state,col_state+1))){
            if(!mazestate_matrix[row_state+1][col_state+1].get_visit()) {
                array_list_state.add(mazestate_matrix[row_state + 1][col_state + 1]);
                mazestate_matrix[row_state + 1][col_state + 1].set_visited();
                mazestate_matrix[row_state + 1][col_state + 1].setdiagonally();
            }
        }
        if(check_diagonal_step(row_state+1,col_state-1)&&(check_diagonal_step(row_state+1,col_state)||check_diagonal_step(row_state,col_state-1))){
            if(!mazestate_matrix[row_state+1][col_state-1].get_visit()) {
                mazestate_matrix[row_state+1][col_state-1].set_visited();
                array_list_state.add(mazestate_matrix[row_state + 1][col_state - 1]);
                mazestate_matrix[row_state + 1][col_state - 1].setdiagonally();
            }
        }

        if(check_diagonal_step(row_state-1,col_state+1)&&(check_diagonal_step(row_state-1,col_state)||check_diagonal_step(row_state,col_state+1))){
            if(!mazestate_matrix[row_state-1][col_state+1].get_visit()) {
                mazestate_matrix[row_state-1][col_state+1].set_visited();
                array_list_state.add(mazestate_matrix[row_state - 1][col_state + 1]);
                mazestate_matrix[row_state - 1][col_state + 1].setdiagonally();
            }
        }

        if(check_diagonal_step(row_state-1,col_state-1)&&(check_diagonal_step(row_state,col_state-1)||check_diagonal_step(row_state-1,col_state))){
            if(!mazestate_matrix[row_state-1][col_state-1].get_visit()) {
                mazestate_matrix[row_state - 1][col_state - 1].set_visited();
                array_list_state.add(mazestate_matrix[row_state - 1][col_state - 1]);
                mazestate_matrix[row_state - 1][col_state - 1].setdiagonally();
            }
        }
        return array_list_state;
    }
/** check diagonal function return true if the step is states value is 0 we check also for not diagonal state
 * also the check in get value function of maze that the index is not out of bound*/
    public boolean check_diagonal_step(int row,int col){
        if(this.maze.getvalue(row,col)==0)
            return true;
        return false;
    }
    /** "initiate" function its for initiate every time the cells in the stracture to be un visited, so now we can do all the 3
     * algoritm */
    @Override
    public  void initiate(){
        for(int i =0;i<this.mazestate_matrix.length;i++){
            for(int j=0;j<this.mazestate_matrix[0].length;j++){
                this.mazestate_matrix[i][j].initiate_camefrom_and_visited();
            }
        }
    }
}
