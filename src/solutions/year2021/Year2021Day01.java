package solutions.year2021;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2021Day01 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		int[] depths = input.asSingleIntArray();
		int increases = 0;
		for (int i = 1; i < depths.length; i++) {
			if (depths[i - 1] < depths[i])
				increases++;
		}
		return increases;
	}

	@Override
	public Object secondPart(InputParser input) {
		int[] depths = input.asSingleIntArray();
		int increases = 0;
		int prevSum = 0;

		for (int i = 0; i < depths.length - 2; i++) {
			int s = 0;
			for (int c = i; c < i + 3; c++) {
				s += depths[c];
			}
			if (prevSum == 0) {
				prevSum = s;
				continue;
			}
			if (s > prevSum) {
				increases++;
			}
			prevSum = s;
		}
		return increases;
	}
}