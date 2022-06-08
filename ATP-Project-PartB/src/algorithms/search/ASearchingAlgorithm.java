package algorithms.search;
import java.io.Serializable;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm , Serializable {
    protected PriorityQueue<AState> openlist;
    protected int visit_nodes;
    // hash set helps us to do the searching algorithm more efficient with the time complexity length
    protected HashSet<String> visited_set;

    public ASearchingAlgorithm() {
        this.openlist = new PriorityQueue<AState>();
        this.visit_nodes = 0;
        this.visited_set = new HashSet<>();
    }


    public abstract Solution solve(ISearchable mazesearch);

    public int getNumberOfNodesEvaluated(){
        return this.visit_nodes;
    }

    /** the cost here is the same for all cells*/
    public void update_cost(AState father, AState son) {
        son.setCamefrom(father);
        son.setCost(son.getCamefrom().getCost() + 1);
    }
}
