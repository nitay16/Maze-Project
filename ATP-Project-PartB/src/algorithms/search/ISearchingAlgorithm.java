package algorithms.search;

public interface ISearchingAlgorithm {
    Solution solve(ISearchable maze_search);
    public int getNumberOfNodesEvaluated();
    String getName();
}

