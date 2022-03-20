import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Test {


	public static void main(String[] args) throws InterruptedException  {


		//long startTime = System.nanoTime();

		State initialState;
		try {											// try and catch to catch the threw exception
			initialState = readBoard("input_n_8_solvable_2_inversions.txt");		// first call readBoard to read the board form the file
			Solver solver = new Solver(initialState);		// then create object of type Solve and pass the board state through solve constructor
			solver.solve();	 //solve the puzzle
			solver.printStateInformation();
			solver.printSoluation();

		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.print("The size of board does not consistent with entered board");
		}catch (FileNotFoundException e) {
			System.out.print("File not Found");
		}catch (Exception e) {
			e.printStackTrace();
		}

		//long endTime = System.nanoTime();

		//long timeElapsed = endTime - startTime;
		//System.out.println("Execution time in milli"
		//		+ "seconds : " +
		//		timeElapsed / 1000000 +" millisecond");

		//System.out.println("Execution time in seconds : " +
		//		timeElapsed / 1000000000 +" second");

		//System.out.println("Execution time in minuate : " +
		//		((timeElapsed / 1000000000)/60)+" minuate");



	}

	@SuppressWarnings("resource")
	public static State readBoard(String fileName) throws Exception{ // return state the contain the board that has been read form the file

		Scanner board = new Scanner(new File(fileName));

		int boardSize = Integer.parseInt(board.nextLine()); // read the size of the board
		int[][] puzzle = new int[boardSize][boardSize];

		int row = 0;
		int lastColumnInLastRow = 0;

		while(board.hasNext()) {						// loop to go through the entire board

			String boardLine = board.nextLine();
			String array[] = boardLine.split(" ");

			for(int column=0;column< boardSize;column++) {
				puzzle[row][column]=Integer.parseInt(array[column]);

				lastColumnInLastRow = column+1;
			}
			row++;
		}

		if (row != boardSize || lastColumnInLastRow != boardSize) // throw expection in case the size of board does not consistent with entered boar
			throw new ArrayIndexOutOfBoundsException();


		State state = new State(puzzle);
		return state;
	}
}
