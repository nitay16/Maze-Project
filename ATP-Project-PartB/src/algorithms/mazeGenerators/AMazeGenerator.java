package algorithms.mazeGenerators;

import java.io.Serializable;

public abstract class AMazeGenerator implements IMazeGenerator, Serializable {
    @Override
    public abstract Maze generate(int rows, int col);
    @Override
    public long measureAlgorithmTimeMillis(int rows, int col){
        generate(rows,col);
        long time_2= System.currentTimeMillis();
        return time_2;
    }

}
