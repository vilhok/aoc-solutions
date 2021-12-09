package solutions.year2017;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day19 extends DayX {

	private int steps = 0;

	@Override
	public Object firstPart(InputParser input) {
		steps = 1; // as explained in the task
		List<String> lines = input.getLines();

		String[][] values = new String[lines.size()][];
		int i = 0;
		for (String s : lines) {
			String[] line = s.split("");
			values[i++] = line;
		}
		int x = 0;
		for (String s : values[0]) {
			if (s.equals("|")) {
				break;
			}
			x++;

		}
		int y = 0;
		int dx = 0;
		int dy = 1;
		String solution = "";
		boolean ready = false;
		while (!ready) {
			String current;
			do {

				y += dy;
				x += dx;
				current = values[y][x];
				steps++;
				if (current.matches("[A-Z]")) {
					solution += current;
					if (checkEnd(values, x, y)) {
						return solution;
					}
				}
			} while (!current.equals("+"));

			if (dx == 0) {
				if (values[y][x + 1].equals("-")) {
					dx = 1;

				} else {
					dx = -1;

				}
				dy = 0;
			} else {
				if (values[y + 1][x].equals("|")) {
					dy = 1;

				} else {
					dy = -1;

				}
				dx = 0;
			}

		}
		return solution;
	}

	private boolean checkEnd(String[][] values, int x, int y) {
		int empty = 0;
		empty += values[y + 1][x].equals(" ") ? 1 : 0;
		empty += values[y - 1][x].equals(" ") ? 1 : 0;
		empty += values[y][x + 1].equals(" ") ? 1 : 0;
		empty += values[y][x - 1].equals(" ") ? 1 : 0;
		return empty == 3;
	}

	@Override
	public Object secondPart(InputParser input) {
		if (steps == 0) {
			firstPart(input);
		}
		return steps;
	}

}
