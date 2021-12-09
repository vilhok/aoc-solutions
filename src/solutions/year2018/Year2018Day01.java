package solutions.year2018;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2018Day01 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		return Arrays.stream(input.asSingleIntArray()).sum();

	}

	@Override
	public Object secondPart(InputParser input) {
		int[] values = input.asSingleIntArray();
		Set<Integer> results = new TreeSet<>();
		int sum = 0;
		int index = 0;
		do {
			sum += values[index % values.length]; // loop list infinitely
			index++;
		} while (results.add(sum)); // if addition fails, set contains this sum already

		return sum;

	}

}
