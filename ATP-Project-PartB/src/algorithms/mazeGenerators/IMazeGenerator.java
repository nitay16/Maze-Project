package algorithms.mazeGenerators;

public interface IMazeGenerator {
    /** the function  get num of rows and num of columns and create a maze and return it */
     Maze generate(int rows,int col);

     long measureAlgorithmTimeMillis(int rows ,int col);

}
