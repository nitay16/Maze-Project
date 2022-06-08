package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected  Queue<AState> openlist;

    public BreadthFirstSearch() {
        super();
        // create regular queue
        this.openlist= new LinkedList<AState>();
    }


    @Override
    public Solution solve(ISearchable mazesearch){
        if(mazesearch==null) {return null;}
        AState start_vert= mazesearch.getStartState();
        start_vert.set_visited();
        AState goal_vert= mazesearch.getGoalState();
        // start the algorithm with add the start state
        openlist.add(start_vert);
        boolean solve = false;
        AState current = null;
        while(openlist.size()>0&&!solve){
            current= this.openlist.remove();
            if(!visited_set.contains(current.toString())) {
                visited_set.add(current.toString());
                this.visit_nodes++;
                if(goal_vert.equals(current)){//if we got the goal vert quit
                    goal_vert.setCamefrom(current.getCamefrom());
                    solve=true;
                }
                else {
                    ArrayList<AState> adjacents = mazesearch.getAllPossibleStates(current);
                    for (AState a : adjacents) {
                        this.update_cost(current,a);
                        openlist.add(a);
                    }
                }
            }
        }
        // createPath from the begin state to goal state
        ArrayList<AState> path = new ArrayList<>();
        int i=0;
        while (i<=this.visit_nodes) {
            path.add(current);
            current = current.getCamefrom();
            if(current.equals(start_vert)){break;}
            i++;
        }
        path.add(start_vert);
        //if we run another algorithm with the same object so we need to initiate the visited states to be unvisited
        mazesearch.initiate();
        // create solution object and return it
        return new Solution(path);
    }



    @Override
    public String getName() {
        return "Breath first algorithm";
    }
}
