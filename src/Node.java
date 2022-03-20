

import java.util.ArrayList;
import java.util.List;
 
public class Node implements Comparable<Node> {
 
 private State state ;
 
 private List<Node> children = new ArrayList<>();
 
 private Node parent;
 
 public Node(State data) { //constructor of Node class
	 this.state = data;
 }
 
 public Node addChild(Node child) { // add one child to the current node
	 child.setParent(this);
	 this.children.add(child);
	 return child;
 }
 
 public String toString() { // recall toStraing method in class State  
	 return this.state.toString();
 }
 
 public State getState() { // return the state value of current node
	 return state;
 }
 
 public void setState(State data) { // set the state value of current node
	 this.state = data;
 }
 
 private void setParent(Node parent) { // set the parent of current node
	 this.parent = parent;
 }
 
 public Node getParent() { // get the parent of current node
	 return parent;
 }
 
@Override
public int compareTo(Node o) { 	// compare the value of evaluation function for the current node 
								//and the node the pass by the parameter 

	if(this.getState().getEvaluationFunction() == ((State)o.getState()).getEvaluationFunction())
		return 0;
	
	if(this.getState().getEvaluationFunction() > ((State)o.getState()).getEvaluationFunction())
		return 1;

	return -1; 
}
 
}