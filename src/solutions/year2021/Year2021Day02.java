package solutions.year2021;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2021Day02 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<List<String>> lines = input.linesAsLists(Delimiter.SPACE);

		int distance = 0;
		int depth = 0;
		for (List<String> command : lines) {
			String s = command.get(0);
			switch (s) {
			case "forward":
				distance += Integer.parseInt(command.get(1));
				break;
			case "up":
				depth -= Integer.parseInt(command.get(1));
				break;
			case "down":
				depth += Integer.parseInt(command.get(1));
				break;
			}
		}
		return distance * depth;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<List<String>> lines = input.linesAsLists(Delimiter.SPACE);

		int distance = 0;
		int depth = 0;
		int aim = 0;
		for (List<String> command : lines) {
			String s = command.get(0);
			switch (s) {
			case "forward":
				int value = Integer.parseInt(command.get(1));
				distance += value;
				depth += aim * value;

				break;
			case "up":
				aim -= Integer.parseInt(command.get(1));
				break;
			case "down":
				aim += Integer.parseInt(command.get(1));
				break;
			}
		}
		return distance * depth;
	}
}