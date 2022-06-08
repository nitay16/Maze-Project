package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    public AState getStartState();
    public AState getGoalState();
    ArrayList<AState> getAllPossibleStates(AState state);
    /** "initiate" function its for initiate every time the cells in the stracture to be un visited, so now we can do all the
     * algorithm together */
    public void initiate();
}
