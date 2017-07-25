import java.util.LinkedList;

public class Q2 {
	
	LinkedList<Node> NodeList = new LinkedList<Node>();
	int sum1,sum2;
	
	public static void main(String[] args) {
		//sample test tree values
		Node v1 = new Node("v1",3,5);
		Node v2 = new Node("v2",2,4);
		Node v3 = new Node("v3",0,7);
		Node v4 = new Node("v4",1,3);
		Node v5 = new Node("v5",0,1);
		Node v6 = new Node("v6",0,2);
		Node v7 = new Node("v7",2,20);
		Node v8 = new Node("v8",0,3);
		Node v9 = new Node("v9",0,2);
		
		
		v1.addChildNode(v2);
		v1.addChildNode(v3);
		v1.addChildNode(v4);
		v2.addChildNode(v5);
		v2.addChildNode(v6);
		v4.addChildNode(v7);
		v7.addChildNode(v8);
		v7.addChildNode(v9);
		
		
		//                					(V1) 
		//								  rating=5
		//              				 /	  |	   \
		//								/	  |		\
		//			 				   /      |      \
		//							(V2)      (V3)     (v4)
		//						rating = 4	rating = 7   rating=3
		//                     /   \                       |
		//                    /     \                      |
		//                   /       \                     |
		//                 (v5)      (v6)                 (v7)
		//                 rating=1   rating=2            rating=20
		//												 /   \
		//                                              /     \
		//                                             /       \
		//                                            (v8)     (v9)
		//											rating=3    rating=2
		//
		//
		
		
		
		Q2 q2 = new Q2();
		q2.MainFunction(v1);//v1 is the root node
	}
	
	public void MainFunction(Node root) {
		int max = findMax(root);
		System.out.println("Max rating is "+max);
		findGuests(root);
				
	     System.out.println("The nodes which makes the max rating");
		for(int i=0;i<NodeList.size();i++) {
			System.out.println(NodeList.get(i).getVertex() +">>>>"+NodeList.get(i).rating);
		}
		
		
	}
	public int findMax(Node root) {
		
		if (root.state==State.Visited) {
			return root.maxRate;
		}
		root.state=State.Visited;
		
		if(root.getNoChildren()==0) {
			root.maxRate = root.rating;
			return root.rating;
		}
		sum1 = root.rating;
		
		for(int i=0;i<root.child.length;i++) {
			for(int j=0;j<root.child[i].child.length;j++) {
				if(root.child[i].child[j].state==State.Visited) {
					sum1+=root.child[i].child[j].maxRate;
				}else {
					sum1+=findMax(root.child[i].child[j]);
				}
			}			
		}
		
		sum2=0;
		
		for(int i=0;i<root.child.length;i++) {
			if(root.child[i].state==State.Visited) {
				sum2+=root.child[i].maxRate;
			}else {
				sum2+=findMax(root.child[i]);
			}
		}
		
		if(sum1>sum2) {
			root.maxRate = sum1;
		}else {
			root.maxRate = sum2;
		}
				
		return Math.max(sum1,sum2);
		
	}
	
	
	public void findGuests(Node root) {
		sum1 = root.rating;
		
		for(int i=0;i<root.child.length;i++) {
			for(int j=0;j<root.child[i].child.length;j++) {
				sum1 = sum1+ root.child[i].child[j].maxRate;
			}			
		}
		
		sum2 = 0;
		
		for(int i=0;i<root.child.length;i++) {
			sum2 = sum2+ root.child[i].maxRate;
		}
		
		if(root.maxRate == sum1) {
			NodeList.add(root);
			for(int i=0;i<root.child.length;i++) {
				for(int j=0;j<root.child[i].child.length;j++) {
					findGuests(root.child[i].child[j]);
				}			
			}
		}else if(root.maxRate==sum2) {

			for(int i=0;i<root.child.length;i++) {
				findGuests(root.child[i]);
			}
		}
	}

}

