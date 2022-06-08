package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    private String state;
    // cost of cross between states
    private double cost;
    // camefrom tell us from where we came to the current state
    private AState camefrom;
    // if state is visited its true else false
    private boolean visited;


    public AState(String state) {
        this.state = state;
        this.camefrom=null;
        this.cost=0;
        this.visited= false;
    }
    public void setState(String state) {
        this.state = state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
            this.cost = cost;
    }
    public boolean get_visit(){
        return this.visited;
    }
    public void set_visited(){
        this.visited =true;
    }

    public AState getCamefrom() {
        return camefrom;
    }

    public void setCamefrom(AState camefrom) {
        if(this.camefrom==null) {
            this.camefrom = camefrom;
        }

    }
    public abstract void setdiagonally();
    public abstract boolean getdiagonlity();

    public void initiate_camefrom_and_visited(){
        this.visited=false;
        this.camefrom=null;
    }





    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass())
            return false;
        AState state1= (AState)  o;
        return state!=null?state.equals(state1.state):state1.state==null;

    }
     public abstract   int hashCode();

}
