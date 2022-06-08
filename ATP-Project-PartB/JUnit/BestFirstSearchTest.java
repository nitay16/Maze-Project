
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    @Test
    void should_return_zero(){
        ASearchingAlgorithm best= new BestFirstSearch();
        assertEquals(0,best.getNumberOfNodesEvaluated());
    }
    @Test
    void should_return_name_search(){
        ASearchingAlgorithm best= new BestFirstSearch();
        assertEquals(best.solve(null),null);
    }
    @Test
    void should_return_name() {
        ASearchingAlgorithm best = new BestFirstSearch();
        assertEquals("Best first Search",best.getName());
    }




}