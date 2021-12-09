package solutions.year2016;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2016Day02 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		char[][] pad = { //
				{ '1', '2', '3' }, //
				{ '4', '5', '6' }, //
				{ '7', '8', '9' } //
		};

		int x = 1, y = 1;
		return traverseKeypad(pad, x, y, input.getLines());
	}

	@Override
	public Object secondPart(InputParser input) {
		char[][] pad = { //
				{ '0', '0', '1', '0', '0' }, //
				{ '0', '2', '3', '4', '0' }, //
				{ '5', '6', '7', '8', '9' }, //
				{ '0', 'A', 'B', 'C', '0' }, //
				{ '0', '0', 'D', '0', '0' } //
		};

		int x = 0, y = 2;

		return traverseKeypad(pad, x, y, input.getLines());
	}

	private String traverseKeypad(char[][] pad, int x, int y, List<String> commands) {
		String result = "";
		for (String line : commands) {
			for (char c : line.toCharArray()) {
				switch (c) {
				case 'U':
					if (y > 0 && pad[x][y - 1] != '0')
						y--;
					break;
				case 'D':
					if (y < pad.length - 1 && pad[x][y + 1] != '0')
						y++;
					break;
				case 'R':
					if (x < pad.length - 1 && pad[x + 1][y] != '0')
						x++;
					break;
				case 'L':
					if (x > 0 && pad[x - 1][y] != '0')
						x--;
					break;
				default:
					break;
				}
			}
			result += pad[y][x];
		}
		return result;
	}
}