public class pqSetCompare implements Comparable<pqSetCompare>{
    int cost;
    int x[];
    
    pqSetCompare(int cost, int x[]){
        this.cost = cost;
        this.x = x;
    }

    @Override
    public int compareTo(pqSetCompare t) {
        if(this.cost > t.cost)
            return -1;
        else if(this.cost < t.cost)
            return 1;
        else
            return 0;
    }
}
