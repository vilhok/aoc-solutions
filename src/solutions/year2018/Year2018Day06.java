package solutions.year2018;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2018Day06 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		/**
		 * Calculate triangles of every three values. If a value is inside a triangle,
		 * it's not infinite? If a value is outside a triangle, it's infinite. 19500
		 * triangles? test each value against all triangles to determine the infinite
		 * values. Then fill the area between min-value and max-value matrix with the
		 * distances. Then calculate areas?
		 * 
		 * This might be unnecessary. If you just fill the area, all that touch the
		 * border will be infinite. Based on reddit, no. Some proofs needed still, but
		 * another alternative is to increase the area
		 * 
		 * 
		 */
		return NOT_SOLVED;
	}

	@Override
	public Object secondPart(InputParser input) {
		return NOT_SOLVED;
	}
	/**
	 * 
	@Override
	public Object firstPart(InputParser input){
		int[] allNumbers = input.asSingleIntArray(Delimiter.COMMA);
		int max = Arrays.stream(allNumbers).max().getAsInt();
		int[][] table = new int[max][max];
		ArrayList<Point> points = new ArrayList<>();
		for(int i = 0; i < allNumbers.length; i += 2){
			int x = allNumbers[i];
			int y = allNumbers[i + 1];
			table[x][y] = i;
			Point p = new Point(x, y);
			points.add(p);
		}
		for(int i = 0; i < max; i++){
			for(int j = 0; j < max; j++){

			}
		}

		return null;
	}

	@Override
	public Object secondPart(InputParser input){
		return NOT_SOLVED;
	}
	 */
}