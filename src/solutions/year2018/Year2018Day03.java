package solutions.year2018;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2018Day03 extends DayX {

	static ArrayList<Rectangle> rect;

	@Override
	public Object firstPart(InputParser input) {
		rect = new ArrayList<>();
		List<String> data = input.getLines();

		// size was assumed in the puzzle
		int[][] array = new int[1000][1000];

		for (String s : data) {
			String[] values = s.split(" @ |: "); // split by the separators in the data: #85 @

			// split the 320,598 and 27x11 into integers
			int[] start = Arrays.stream(values[1].split(",")).mapToInt(i -> Integer.parseInt(i)).toArray();
			int[] size = Arrays.stream(values[2].split("x")).mapToInt(i -> Integer.parseInt(i)).toArray();
			rect.add(new Rectangle(start[0], start[1], size[0], size[1])); // this is for the second part
			// increment the locations in the table
			for (int x = start[0]; x < start[0] + size[0]; x++) {
				for (int y = start[1]; y < start[1] + size[1]; y++) {
					array[x][y]++;
				}
			}
		}

		// return how many values are bigger than 1
		return Arrays.stream(array).flatMapToInt(e -> Arrays.stream(e)).filter(i -> i > 1).count();

	}

	@Override
	public Object secondPart(InputParser input) {
		if (rect == null) {
			firstPart(input);
		}
		int rectCount = rect.size();
		out: for (int i = 0; i < rectCount; i++) {
			Rectangle r = rect.get(i);
			for (int j = 0; j < rectCount; j++) {
				if (i == j)
					continue;
				if (r.intersects(rect.get(j))) {
					continue out;
				}

			}
			// this is reached if the rectangle at index i did not collide without any other
			// as they were put in the list in order, we assume the position matches the id
			// (correcting for 0-indexing)
			return (i + 1);
		}
		return null;
	}
}