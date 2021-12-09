package solutions.year2020;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day02 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<String> rows = input.getLines();

		int correct = 0;
		for (String s : rows) {

			String[] values = s.split("-| |: ");
			int lower = Integer.parseInt(values[0]);
			int upper = Integer.parseInt(values[1]);

			int count = values[3].replaceAll("[^" + values[2] + "]", "").length();
			if (count >= lower && count <= upper) {
				correct++;
			}
		}
		System.out.println(rows.size() + " " + correct);
		return correct;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> rows = input.getLines();
		int correct = 0;
		for (String s : rows) {
			String[] values = s.split("-| |: ");
			int index1 = Integer.parseInt(values[0]) - 1;
			int index2 = Integer.parseInt(values[1]) - 1;

			char c = values[2].charAt(0);
			char first = values[3].charAt(index1);
			char second = values[3].charAt(index2);
			if ((c == first && c != second) || (c != first && c == second)) {
				correct++;
			}
		}
		return correct;
	}
}