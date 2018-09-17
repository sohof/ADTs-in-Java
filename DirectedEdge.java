public class DirectedEdge {

    private final double weight;
    private final int v;     //edge tail. In this ex. v -> w,  v is the tail and w the head
    private final int w;     //edge head   
    
    public DirectedEdge(int v, int w, double weight){

	this.v = v;
	this.w = w;
	this.weight = weight;
    }

    public int from() { return v; }
    public int to() { return w; }
    public double weight() { return  weight; }

    public String toString() {
	return String.format("%d->%d %.2f", v,w,weight);
    }
}
