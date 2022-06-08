package algorithms.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch(){
        super();
        this.openlist = new PriorityQueue<AState>(new Comperator_state());
    }

    /** update cost works with the cost to getting diagonal cell or regular cell*/
    @Override
    public void update_cost(AState fathder,AState son) {
        son.setCamefrom(fathder);
        if(son.getdiagonlity()) {
            son.setCost(son.getCamefrom().getCost() + 15);
        }
        else{
            son.setCost(son.getCamefrom().getCost()+10);
        }
    }
    @Override
    public String getName() {
        return "Best first Search";
    }
}
/** class comperator for the priority queue we create with compare function */
class Comperator_state implements Comparator<AState>{

    @Override
    public int compare(AState o1, AState o2) {
        if(o1.getCost()<o2.getCost())
            return 1;
        else if(o1.getCost()>o2.getCost())
            return -1;
        else
            return 0;
    }
}
