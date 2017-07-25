public class Node{
	public Node[] child;
	int rating;
	int maxRate;
	public int childCount;
	private String vertex;
	public State state;
	
	public Node(String vertex,int rating) {
		this.vertex=vertex;
		this.rating = rating;
		this.maxRate = rating;
	}
	
	public Node(String vertex,int children,int rating) {
		this.vertex=vertex;
		this.rating = rating;
		this.maxRate = rating;
		childCount = 0;
		child = new Node[children];
		this.state=State.Unvisited;
	}
	
	public void addChildNode(Node adj) {
		adj.state = State.Unvisited;
		
		if(childCount>=0) {
			this.child[childCount]=adj;
			childCount++;
		}
	}
	
	public Node[] getChild() {
		return child;
	}
	public int getNoChildren(){
		return child.length;
	}
	public String getVertex() {
		return vertex;
	}
}
