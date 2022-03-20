
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {
	
	private State startState;
	private PriorityQueue<Node> frontier;
	private List<Node> explored;
	private Node goalStateNode;
	private int solutionLength;

	public Solver(State initial) { /// Constructor of class Solver
		initial.setBlankRow(-1);
		initial.setblankColumn(-1);
		startState=initial;
	}
	
	public void solve() { // to solve the puzzle and find the solution to the start state if it solvable

		if(startState.isSolvable()) { // check if start state solvable
			initializeFrontier();
			initializeExploredList();
			findSloution();
		}		
	}

	private void initializeExploredList() {  // initialize the explored list
	
		explored = new LinkedList<Node>();
		
	}

	private void initializeFrontier() { // initialize the Frontier Queue
		
		frontier = new PriorityQueue<Node>();
		startState.setCostFunction(0);				// set the cost function to the start state
		Node StartStateNode = new Node(startState); // create node to the start state
		
		frontier.add(StartStateNode);	// add the initial state node to the Frontier Queue
		
	}

	private boolean findSloution() { // find the solution to the start node and return true if solution found

		while(!frontier.isEmpty()) {
			 Node currentStateNode = frontier.remove(); //choose a leaf node that has minimum evaluation function value and remove it from the frontier
				
			if(currentStateNode.getState().isGoal()) {  //if the node contains a goal state 
				setGoalStateNode(currentStateNode);	    //then set current state as the goal state				
				return true;							// and return true
			}
			else {
					
				explored.add(currentStateNode); 		// add the node to the explored list
				expand(currentStateNode); //expand the chosen node	
			}
		}
		return false;
		
	}
	
	private void expand(Node currentStateNode) {
		
		 List<State> neighbors = currentStateNode.getState().neighbors(); 

		while(!neighbors.isEmpty()) {

			State neighbor = neighbors.remove(0);
			neighbor.setCostFunction(currentStateNode.getState().getCostFunction()+1); // set the cost function to the neighbor state 
			Node neighborNode = new Node(neighbor);
					
			if((!FoundInExpolredListAndFrontierList(neighbor))) { //  if the neighbor state exist in explored or frontier 
				
				frontier.add(neighborNode);						// adding the resulting nodes to the frontier only if not in the frontier or explored set 

				neighborNode = currentStateNode.addChild(neighborNode); // add the neighbor Node to the current to children
			}	
		}		
	}

	private boolean FoundInExpolredListAndFrontierList(State state) { // return true if the given state exist in explored or frontier 
		
		Object[] frontierArray = frontier.toArray();
		int frontierArraySize = frontierArray.length;		
		int expolredArraySize = explored.size();
		int size;
		
		// take the highest size between the two and make it loop size 
		if(expolredArraySize >= frontierArraySize)
			size = expolredArraySize;
		else
			size = frontierArraySize; 
		
		for(int i=0 ; i<size ;i++) {
			
			if (expolredArraySize > i ) { // to avoid out of index 
				if(state.equals(explored.get(i).getState())) { // check if the given state exist in explored
					return true;
				}
			}
			
			if(frontierArraySize > i) { // to avoid out of index 
				if(state.equals(((Node)frontierArray[i]).getState()) ) { // check if the given state exist in frontier
					return true;
				}
			}
		}
		return false;
	}
	
	Stack<State> getSolution(){ //returns stack that has the solution path from the start state to the goal state
		
		if(getGoalStateNode()==null) {
			return new Stack<State>(); //return empty stack
		}
		
		Stack<State> Solution = new Stack<State>();
		Node current = getGoalStateNode(); // get the goal state node 
		Solution.add(current.getState());

		while(current.getParent() != null) {	//  trace back the parent and the grandparent of the goal state till reach the root which the start state 
			Solution.add(current.getParent().getState());
			current = current.getParent();	 
		}
		setSolutionLength(Solution.size());
		
		return Solution;
	}
	
	public void printStateInformation() {  //to print the information of the start state
		
		State startState = getStartState();
		
		System.out.println("Input:\n"+startState);
		System.out.println("Hamming distance of the puzzle: "+startState.hamming());
		System.out.println("Manhattan distance of the puzzle: "+startState.manhattan());
		System.out.println("This puzzle is a goal:?"+startState.isGoal()); 
		System.out.println("Number of inversions in this puzzle: "+startState.nbInversions());
	}
	
	public void printSoluation() { //to print the solution of the start state

		Stack<State> Solution = getSolution();
		
		if(Solution != null && !Solution.empty()) {
			System.out.println("N-puzzle is solvable\nSolution:");
			while(Solution.size() != 0) {
				State currentState = Solution.pop(); 
				System.out.println(currentState.toString()+"\n----------------------");
			}
			System.out.println("Solution length: "+getSolutionLength());
		}else {
			System.out.println("N-puzzle is not solvable");
		}		
	}

	int getSolutionLength() {//return the length of the solution 
		return solutionLength;
	}

	private Node getGoalStateNode() { //  return the value of the goal state node 
		return goalStateNode;
	}

	private void setGoalStateNode(Node goalStateNode) { // set the value of the goal state node 
		this.goalStateNode = goalStateNode;
	}

	private void setSolutionLength(int solutionLength) { // set the length of the found solution 
		this.solutionLength = solutionLength;
	}
	
	private State getStartState() { //  the value of the start state  
		return startState;
	}

	
}

