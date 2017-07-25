package q2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import sun.misc.Queue;

class Node {//for Tree data structure

    Node leftChild;
    Node rightSibling;
    String name;
    double rating;
    boolean invite;

    public Node(Node leftChild, Node rightSibling, String name, double rating) {
        this.leftChild = leftChild;
        this.rightSibling = rightSibling;
        this.name = name;
        this.rating = rating;
    }
}

class Tree {//to represent company organizational hierachy of employees

    Node root;
//insertNode
    void insertNode(String childName, double childRating, String parentName) throws InterruptedException {
        Node node = new Node(null, null, childName, childRating);
        if (parentName == null) {
            root = node;
        } else {
            Node parentNode = searchNode(parentName);
            if (parentNode == null) {
                System.out.println("Failed to insert " + childName + " : No parent node named " + parentName);
            } else {
                Node childNode = searchNode(childName);
                if (childNode == null) {
                    if (parentNode.leftChild == null) {
                        parentNode.leftChild = node;
                    } else {
                        Node sibling = parentNode.leftChild;
                        while (sibling.rightSibling != null) {
                            sibling = sibling.rightSibling;
                        }
                        sibling.rightSibling = node;
                    }
                } else {
                    System.out.println("Failed to insert " + childName + " : " + childName + " node already exists");
                }
            }
        }
    }
//check whether given node name exist and if exist return parent node
    Node searchNode(String nodeName) throws InterruptedException {
        Queue<Node> queue = new Queue<>();//create object of queue
        queue.enqueue(root);
        Node currentNode = root;
        if (root.name.equals(nodeName)) {
            return root;
        }
        Node nodeFound = null;
        while (nodeFound == null && (!queue.isEmpty())) {
            currentNode = queue.dequeue();
            if (currentNode != null) {
                currentNode = currentNode.leftChild;
                if (currentNode != null) {
                    queue.enqueue(currentNode);
                    if (currentNode.name.equals(nodeName)) {
                        nodeFound = currentNode;
                    }
                    while (currentNode.rightSibling != null && nodeFound == null) {
                        currentNode = currentNode.rightSibling;
                        queue.enqueue(currentNode);
                        if (currentNode.name.equals(nodeName)) {
                            nodeFound = currentNode;
                        }
                    }
                }
            }

        }

        return nodeFound;
    }
//used to print the tree
    void printTree() throws InterruptedException {
        Queue<Node> queue = new Queue<>();//create object of queue
        queue.enqueue(root);
        Node currentNode = root;
        System.out.println(currentNode.name + ':' + currentNode.rating);
        String line = "";
        while (!queue.isEmpty()) {
            currentNode = queue.dequeue();
            if (currentNode != null) {
                currentNode = currentNode.leftChild;
                if (currentNode != null) {
                    queue.enqueue(currentNode);
                    line = line + currentNode.name + ":" + Double.toString(currentNode.rating);
                    while (currentNode.rightSibling != null) {
                        currentNode = currentNode.rightSibling;
                        queue.enqueue(currentNode);
                        line = line + " " + currentNode.name + ':' + Double.toString(currentNode.rating);
                    }
                    System.out.println(line);
                    line = "";
                }
            }

        }

    }

}

class solveTree {
//return sum of children's ratings for given parent node
    double getRatingSumOfChildren(Node parent) {
        Node currentNode = parent.leftChild;
        double ratingSum = 0;
        while (currentNode != null) {
            ratingSum += currentNode.rating;
            currentNode = currentNode.rightSibling;
        }
        return ratingSum;

    }
//return sum of grandchildren  ratings for given parent node
    double getRatingSumOfGrandChildren(Node parent) {
        double ratingSum = 0;
        Node child = parent.leftChild;
        while (child != null) {
            Node grandChild = child.leftChild;
            while (grandChild != null) {
                ratingSum += grandChild.rating;
                grandChild = grandChild.rightSibling;
            }
            child = child.rightSibling;
        }
        return ratingSum;

    }
//preprosessing,to acesses the tree from bottom to top
    List<String> solve(Tree tree) throws InterruptedException {
        Stack<Node> stack = new Stack<Node>();//create object of Stack
        Queue<Node> queue = new Queue<>();//create object of queue
        queue.enqueue(tree.root);
        stack.push(tree.root);
        Node currentNode = tree.root;
        while (!queue.isEmpty()) {
            currentNode = queue.dequeue();
            if (currentNode != null) {
                currentNode = currentNode.leftChild;
                if (currentNode != null) {
                    queue.enqueue(currentNode);
                    stack.push(currentNode);
                    while (currentNode.rightSibling != null) {
                        currentNode = currentNode.rightSibling;
                        queue.enqueue(currentNode);
                        stack.push(currentNode);
                    }
                }
            }
        }
        while (!stack.isEmpty()) {//calculate maximum possible rating for each sub tree intiating from Node
            currentNode = stack.pop();
            double childrenRatingSum = getRatingSumOfChildren(currentNode);
            double grandChildrenRatingSum = getRatingSumOfGrandChildren(currentNode);
            if ((currentNode.rating + grandChildrenRatingSum) >= childrenRatingSum) {
                currentNode.invite = true;
                currentNode.rating += grandChildrenRatingSum;
            } else {
                currentNode.invite = false;
                currentNode.rating = childrenRatingSum;
            }
        }
        //Create invite list
        List<String> inviteList = new ArrayList<String>();//create object of List
        Queue<Node> queue1 = new Queue<>();//create object of Queue
        queue1.enqueue(tree.root);
        currentNode = tree.root;
        if (currentNode.invite) {
            inviteList.add(currentNode.name);
            Node childNode = currentNode.leftChild;
            while (childNode != null) {
                childNode.invite = false;
                childNode = childNode.rightSibling;
            }
        }
        while (!queue1.isEmpty()) {
            currentNode = queue1.dequeue();
            if (currentNode != null) {
                currentNode = currentNode.leftChild;
                if (currentNode != null) {
                    queue1.enqueue(currentNode);
                    if (currentNode.invite) {
                        inviteList.add(currentNode.name);
                        Node childNode = currentNode.leftChild;
                        while (childNode != null) {
                            childNode.invite = false;
                            childNode = childNode.rightSibling;
                        }
                    }
                    while (currentNode.rightSibling != null) {
                        currentNode = currentNode.rightSibling;
                        queue1.enqueue(currentNode);
                        if (currentNode.invite) {
                            inviteList.add(currentNode.name);
                            Node childNode = currentNode.leftChild;
                            while (childNode != null) {
                                childNode.invite = false;
                                childNode = childNode.rightSibling;
                            }
                        }
                    }
                }
            }
        }
        return inviteList;

    }
}

public class Q2 {

    public static void main(String[] args) throws InterruptedException {
        Tree testTree1 = new Tree();
        testTree1.insertNode("president", 100, null);
        testTree1.insertNode("employee1", 100, "president");//#not invited
        testTree1.insertNode("employee2", 200, "president");//#not invited
      //  testTree1.printTree();
        testTree1.insertNode("employee3", 300,"employee1");
	testTree1.insertNode("employee4", 100,"employee1");

	testTree1.insertNode("employee5", 200,"employee2");
	testTree1.insertNode("employee6", 100,"employee2");

	testTree1.insertNode("employee7", 200,"employee3");
	testTree1.insertNode("employee8", 200,"employee3");
	testTree1.insertNode("employee9", 200,"employee3");

	testTree1.insertNode("employee10", 200,"employee5");
	testTree1.insertNode("employee11", 200,"employee5");

	testTree1.insertNode("employee12", 200,"employee7");
	testTree1.insertNode("employee13", 200,"employee7");

        //testTree1.printTree();
        Tree z = new Tree();
        z.insertNode("T",10,null);
        z.insertNode("T1",7, "T");
        z.insertNode("T2",11,"T");
        z.insertNode("T3",3,"T");
        z.insertNode("T4",1 ,"T1");
        z.insertNode("T5",2 ,"T1");
        z.insertNode("T6",12,"T2");
        z.insertNode("T7",3,"T2");
        z.insertNode("T8",2,"T2");
        z.insertNode("T9",5,"T3");
        z.insertNode("T10",6,"T5");
        z.insertNode("T11",8, "T5");



        solveTree st=new solveTree();
        System.out.println(st.solve(z));

    }

}
