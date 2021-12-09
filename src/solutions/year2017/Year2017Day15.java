package solutions.year2017;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day15 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		return matches(input, 1, 1, 40_000_000);
	}

	@Override
	public Object secondPart(InputParser input) {
		return matches(input, 4, 8, 5_000_000);
	}

	private Integer matches(InputParser input, int amod, int bmod, int iterations) {
		List<String> strings = input.getLines();
		long a = Long.parseLong(strings.get(0).substring(strings.get(0).lastIndexOf(" ") + 1));
		long b = Long.parseLong(strings.get(1).substring(strings.get(1).lastIndexOf(" ") + 1));
		final long aConst = 16807;
		final long bConst = 48271;
		final long intMax = Integer.MAX_VALUE;
		Integer result = 0;
		for (int i = 0; i < iterations; i++) {
			// wait until match
			do {
				a = (a * aConst) % intMax;
			} while (a % amod != 0);
			do {
				b = (b * bConst) % intMax;
			} while (b % bmod != 0);

			// bitwise compare low 16 bits
			if ((a & 0xffff) == (b & 0xffff)) {
				result++;
			}
		}
		return result;
	}
}
