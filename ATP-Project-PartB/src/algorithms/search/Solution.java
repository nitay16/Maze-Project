package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Solution implements Serializable {
     private  ArrayList<AState> array_list_path;
     public Solution(ArrayList<AState> path_solution){
         Collections.reverse(path_solution);
         this.array_list_path=path_solution;
     }
    public ArrayList<AState> getSolutionPath(){
        return this.array_list_path;
    }
}
