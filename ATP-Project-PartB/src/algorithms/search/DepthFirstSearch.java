package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {
    Stack<AState> openlist;

    public DepthFirstSearch(){
        super();
        //the dfs algorithm works with stack
        this.openlist= new Stack<AState>();
    }

    @Override
    public Solution solve(ISearchable mazesearch){
        if(mazesearch==null) {return null;}
        boolean solve =false;
        AState start_state= mazesearch.getStartState();
        AState goal_state= mazesearch.getGoalState();
        start_state.set_visited();

        AState current=null;
        openlist.push(start_state);
        while (!openlist.empty()&&!solve)
        {
            // Pop a state from the stack
            current = openlist.pop();


            // if the state is already discovered yet continue
            if (visited_set.contains(current.toString())) {
                continue;
            }
            this.visit_nodes++;
            visited_set.add(current.toString());
            if(goal_state.equals(current)) {
                goal_state.setCamefrom(current.getCamefrom());
                solve = true;
            }


            // we will reach here if the popped state current is not discovered yet;
            ArrayList<AState> adjList = mazesearch.getAllPossibleStates(current);
            for (AState a : adjList) {
                update_cost(current,a);
                openlist.push(a);
            }
        }
        ArrayList<AState> path = new ArrayList<>();
        int i=0;
        while (i<=this.visit_nodes) {
            path.add(current);
            current = current.getCamefrom();
            if(current.equals(mazesearch.getStartState())){break;}
            i++;
        }
        path.add(current);

        (mazesearch).initiate();

        return new Solution(path);
    }
    public int getNumberOfNodesEvaluated(){
        return this.visit_nodes;
    }

    @Override
    public String getName() {
        return "Depth First Search";
    }
}
