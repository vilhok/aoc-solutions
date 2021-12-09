package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day02 extends DayX {

	public Object firstPart(InputParser input) {
		int sum = 0;
		for (String s : input.getLines()) {
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (String number : s.split("\\s")) {
				int num = Integer.parseInt(number);
				if (num < min) {
					min = num;
				}
				if (num > max) {
					max = num;
				}
			}
			sum += max - min;

		}
		return sum;
	}

	public Object secondPart(InputParser input) {
		int sum = 0;
		line: for (String s : input.getLines()) {
			String[] strings = s.split("\\s");
			int[] numbers = new int[strings.length];
			for (int i = 0; i < strings.length; i++) {
				numbers[i] = Integer.parseInt(strings[i]);

			}
			for (int k = 0; k < numbers.length; k++) {
				for (int j = 0; j < numbers.length; j++) {
					if (k == j)
						continue;
					if (numbers[k] % numbers[j] == 0) {
						sum += numbers[k] / numbers[j];
						continue line;
					}
				}
			}
		}
		return sum;
	}
}
