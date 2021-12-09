package solutions.year2019;

import java.util.Arrays;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2019Day01 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		int[] values = input.asSingleIntArray();
		return Arrays.stream(values).map(i -> (i / 3) - 2).sum();
	}

	@Override
	public Object secondPart(InputParser input) {
		int[] values = input.asSingleIntArray();
		return Arrays.stream(values).map(i -> getFuel(i)).sum();
	}

	/*
	 * Recurse the fuel for additional fuel
	 */
	private int getFuel(int i) {
		int fuel = (i / 3) - 2;
		if (fuel < 0)
			return 0;
		return fuel + getFuel(fuel);
	}

}
