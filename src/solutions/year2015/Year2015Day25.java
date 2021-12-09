package solutions.year2015;

import java.math.BigInteger;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day25 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		String s = input.getLines().get(0);

		// target coordinates as per task
		int row = Integer.parseInt(s.split(" ")[16].replace(",", ""));
		int col = Integer.parseInt(s.split(" ")[18].replace(".", ""));

		// initial values as per task
		BigInteger current = BigInteger.valueOf(20151125);
		BigInteger multiplier = BigInteger.valueOf(252533);
		BigInteger divider = BigInteger.valueOf(33554393);
		int currentRow = 1, currentColumn = 1;

		
		//travel diagonally until correct location reached
		while (!((currentRow == row) && (currentColumn == col))) {
			current = current.multiply(multiplier).remainder(divider);

			if (currentRow == 1) {
				currentRow = currentColumn + 1;
				currentColumn = 1;
			} else {
				currentColumn++;
				currentRow--;
			}
		}

		return current;
	}

	@Override
	public Object secondPart(InputParser input) {
		return NOT_SOLVED;
	}
}