
import java.util.LinkedList;
import java.util.List;

public class State {
	
	private final int BLANK_TILE_VALUE = 0;
	private int tiles[][];
	private int blankRow ;
	private int blankColumn;
	private int totalNumberOfInversions;
	private int costFunction;
	
	public  State(int[][] tiles) {	// Constructor of class State
		this.tiles = tiles;
	}
	
	public String toString() { // return string the represent the value of state

		int size = size();
		
		if(tiles == null || size == 0)
			return null;
		
		String tilesStr="";
		for(int i=0 ; i<size; i++) {
			for(int j=0; j<size; j++) {
				tilesStr+=tiles[i][j]+" ";
			}
			tilesStr+="\n";
		}
		return tilesStr;
	}
	
	public boolean equals(Object y) { //checks if the current State is equal to the State y
		
		if(y == null)
			return false;
	
		for(int i=0; i<((State)y).tiles.length; i++) { 
			for(int j=0 ;j<((State)y).tiles[i].length; j++) { // two loop to go through the array
				if( ((State)y).tiles[i][j] != tiles[i][j] ) { //to check if they have the same elements' values 
					return false; 
				}
			}
		}
		
		return true; // return true in case they both have the same elements' values 
	}
	
	public int hamming() { // Hamming Heuristic compute the sum of misplaced tiles
		
		int numberOfMisplacedTiles = 0; // initial with 0
		int size = size();

		for(int row=0; row<size; row++) { 
			for(int column=0; column<tiles[row].length; column++) { // two loop to go through the array
				int tileGoalValue = (column+1)+(row*size);  
				if((tiles[row][column] != tileGoalValue) && (tiles[row][column] != BLANK_TILE_VALUE)) { // check if the tile contain its goal value or the blank value 
					numberOfMisplacedTiles++;															//increase the number of misplaced tile
				}
			}
		}
		return numberOfMisplacedTiles; 	// return total number of misplaced tile
	}
		
	public int manhattan() { // Manhattan Heuristic computes the sum of distance from current position to the goal position
		
		int manhattan = 0;
		int size = size();

		for(int row = 0; row<size; row++) {
			for(int column = 0; column<tiles[row].length; column++) {  // two loop to go through the array

				int tileGoalValue = (column+1)+(row*size);  
				
				if((tiles[row][column] != tileGoalValue) && (tiles[row][column] != BLANK_TILE_VALUE)) // check if the tile contain its goal value or the blank value 
				{
					double goalRowOfCurrentTile = Math.ceil(tiles[row][column]/(double)size)-1;  
					double goalColumnOfCurrentTile = tiles[row][column]-(goalRowOfCurrentTile*(double)size+1); 
		
					if(row == goalRowOfCurrentTile) { //in case current row equals the goal row 
						manhattan+= Math.abs(tileGoalValue - tiles[row][column]);  // Manhattan distance equals the different between goal value of tile and the current value of tile
						
					}else if(column == goalColumnOfCurrentTile) { //in case current column equals the goal column 
						manhattan+= Math.abs(row - goalRowOfCurrentTile);	 // Manhattan distance equals the different between goal row of current tile and current row
						
					}else { //on case current row and column different form the goal row and column
						manhattan+= Math.abs(row - goalRowOfCurrentTile) + Math.abs(column - goalColumnOfCurrentTile); // Manhattan distance equals the different between goal row of current tile and current row 
																														// and the different between goal column of current tile and current column 
					} //sum the value of Manhattan for each tile
				}
			}
		}
		return manhattan; // return the total value of Manhattan distance
	}

	public boolean isGoal() { // return true of value of Heuristic Function equals 0
	
		return getHeuristicFunction() == 0;	// check of the value of Heuristic Function
	}
	
	public List<State> neighbors() {	// return list of current state neighbors
		
		List<State> neighbors = new LinkedList<State>();		
		int size = size();
		
		if(getBlankRow()==-1) { 
			searchForBlankTile(); //this if statement just work with the initial state otherwise the blank position will be determined before 
		}
		
		if(blankRow != 0) //check if current state has UpNeighbor
		{	neighbors.add(findUpNeighbor());
		
		}
	
		if(blankRow!= size-1) //check if current state has DownNeighbor
		{	neighbors.add(findDownNeighbor());
			
			}

		if(blankColumn != 0) //check if current state has LeftNeighbor
		{	neighbors.add(findLeftNeighbor());
		

		}
		
		if(blankColumn != size-1) //check if current state has RightNeighbor
		{	neighbors.add(findRightNeighbor());

		}
		
		return neighbors;
	}

	private State findUpNeighbor() {	// return the state of the up Neighbor 

		int [][]upNeighborTiles = copyTiles();							// copy the current state
		int upNeighborValue = tiles[blankRow-1][blankColumn];		 	// save the value of the up tile
		
		upNeighborTiles[blankRow-1][blankColumn]= 0;					// move the blank to the up tile
		upNeighborTiles[blankRow][blankColumn]= upNeighborValue;		// move the value of up tile to the blank tile
		
		State state = new State(upNeighborTiles);
		state.setBlankRow(blankRow-1);
		state.setblankColumn(blankColumn);
		
		return state;								 // return the new state
	}
	
	private State findDownNeighbor() {	// return the state of the down Neighbor 

		int [][]downNeighborTiles = copyTiles();						 // copy the current state
		int udownNeighborValue = tiles[blankRow+1][blankColumn];		 // save the value of the down tile
		
		downNeighborTiles[blankRow+1][blankColumn]= 0;					// move the blank to the down tile
		downNeighborTiles[blankRow][blankColumn]= udownNeighborValue;	// move the value of down tile to the blank tile
		
		State state = new State(downNeighborTiles);
		state.setBlankRow(blankRow+1);
		state.setblankColumn(blankColumn);
		
		return state;							 // return the new state
		
	}
	
	private State findLeftNeighbor() { // return the state of the Left Neighbor 

		int [][]leftNeighborTiles = copyTiles();					 // copy the current state
		int leftNeighborValue = tiles[blankRow][blankColumn-1]; 	 // save the value of the left tile
			
		leftNeighborTiles[blankRow][blankColumn-1]= 0;				 // move the blank to the left tile
		leftNeighborTiles[blankRow][blankColumn]= leftNeighborValue; // move the value of right tile to the blank tile
		
		State state = new State(leftNeighborTiles);
		state.setBlankRow(blankRow);
		state.setblankColumn(blankColumn-1);
		
		return state;						 // return the new state
		
	}
	
	private State findRightNeighbor() { // return the state of the Right Neighbor 

		int [][]rightNeighborTiles = copyTiles();						// copy the current state
		int rightNeighborValue = tiles[blankRow][blankColumn+1]; 		// save the value of the right tile
		
		rightNeighborTiles[blankRow][blankColumn+1]= 0;					// move the blank to the right tile
		rightNeighborTiles[blankRow][blankColumn]= rightNeighborValue;	// move the value of right tile to the blank tile
		
		State state = new State(rightNeighborTiles);
		state.setBlankRow(blankRow);
		state.setblankColumn(blankColumn+1);
		
		return state;							// return the new state
	}

	private int[][] copyTiles() { // return a copy of the tiles array
		
		int size =  size();
		int [][] copiedTiles = new int [size][size];
		for(int i=0;i<tiles.length;i++) {
			for(int j=0;j<tiles[i].length;j++) {
				copiedTiles[i][j] = tiles[i][j];
			}
		}
		
		return copiedTiles;		
	}
	
	private void searchForBlankTile() { //to determine blank tile position 
		
		int size = size();
		for(int row = 0; row<size; row++) {
			for(int column=0; column<tiles[row].length; column++) {  // two loop to go through the array
				if(tiles[row][column] == BLANK_TILE_VALUE ) { // check if the value of the current tile is equals the value of blank tile
					blankRow = row;
					blankColumn = column;
					return; // if the index of the blank tile found, there no need to continue and check the remain tiles  
					}
				}
			}
		}
	
	public boolean isSolvable() { // returns trues is the State is solvable
		
		if(blankRow==-1) {
			searchForBlankTile();
		}
		
		int size = size();
		int nbInversions = nbInversions(); // the possibility of solving the state is depend of the number of Inversions 
	
		// in case the grid is odd that mean the size of tiles is equals odd number like 3 
		if(size%2 == 1) { 
			if(nbInversions% 2 == 0)  //check if the number of Inversions is even					
				return true;
			else
				return false;
		}
		// in case the grid is even that mean the size of tiles is equals even number like 4 
		else {	
			// the position of the blank is effect the possibility of solving the state in even grid
			if(blankRow % 2 == 0) { // in case the blank row is even 
				if(nbInversions % 2 == 1)  //check if the number of Inversions is odd
					return true;		
				else
					return false;
			}else { 			 // in case the blank row is odd 
				if(nbInversions % 2 == 0) //check if the number of Inversions is even
					return true;
				else
					return false;
				}
			}	
	}
	
	public int nbInversions() {  // return the total number of Inversion in the current state 
		
		int size = size();
		totalNumberOfInversions = 0;
		
		for(int row = 0; row<size; row++) {
			for(int column = 0; column < tiles[row].length ;column++) {  // two loop to go through the array
				int valueOfcurrentTile = tiles[row][column];
				totalNumberOfInversions+= findnbInversionsForCuurentTile(column,row,valueOfcurrentTile); // sum the number of inversions for each tile
			}
		}
		return totalNumberOfInversions;
	}

	private int findnbInversionsForCuurentTile(int currentColumn, int cuurentRow,int valueOfcurrentTile ) { // return the number of Inversion of a specific tile in the current state 

		int size = size();
		int numberOfInversionsForCurrentTile = 0;
		int column = currentColumn; // to start from the current column
		
		for( int row = cuurentRow; row < size; row ++) {
			for(; column<tiles[cuurentRow].length;column++) { // two loop to go through the array
				
				int valueOfTileAfterCurrentTile = tiles[row][column];
				
				if(valueOfcurrentTile > valueOfTileAfterCurrentTile && (valueOfTileAfterCurrentTile != BLANK_TILE_VALUE)) { // check if the value of the tile that after current is less than the current tile value 
					numberOfInversionsForCurrentTile++;																		// in this case increase the number of inversions 
				}
			}
			column = 0; //to restart from column 0 at new row
		}
		return numberOfInversionsForCurrentTile;
	}

	public int getHeuristicFunction() { // return the value of the Heuristic function that has been chosen
		return manhattan();
		
	}
	
	public int getCostFunction() {   // return the value of cost function
		return costFunction;	
	}
	
	public void setCostFunction(int g) {  // set the value of the cost function 
		this.costFunction = g ;	
	}
	
	public int getEvaluationFunction() { // return the value of the evaluation function 
		return getCostFunction()+getHeuristicFunction();	
	}
	
	public int size() {
		return tiles.length;
	}
	
	public void setBlankRow(int blankRow) {
		this.blankRow = blankRow;
	}
	
	public void setblankColumn(int blankColumn) {
		this.blankColumn = blankColumn;
	}
	
	private int getBlankRow() {
		return blankRow;
	}

}
