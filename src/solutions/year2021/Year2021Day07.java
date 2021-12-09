package solutions.year2021;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2021Day07 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		int[] crabs = input.asSingleIntArray(Delimiter.COMMA);
		int cheapest = Integer.MAX_VALUE;
		for (int i = 0; i < crabs.length; i++) {
			int fuelcost = 0;
			for (int j = 0; j < crabs.length; j++) {
				fuelcost += Math.abs(crabs[j] - i);
			}
			if (fuelcost < cheapest) {
				cheapest = fuelcost;
			}
		}

		return cheapest;
	}

	// To shave of couple of milliseconds, this could easily be added to the first
	// part
	@Override
	public Object secondPart(InputParser input) {
		int[] crabs = input.asSingleIntArray(Delimiter.COMMA);
		int cheapest = Integer.MAX_VALUE;
		for (int i = 0; i < crabs.length; i++) {
			int fuelcost = 0;
			for (int j = 0; j < crabs.length; j++) {
				int distance = Math.abs(crabs[j] - i);
				fuelcost += ((1 + distance) / 2.0) * distance;
			}
			if (fuelcost < cheapest) {
				cheapest = fuelcost;
			}
		}
		return cheapest;
	}

	/**
	 * Tests for part 1
	 * 
	 * Add tests to your solution as following:
	 * 
	 * {@code new Test(multiLineStringInput,solutionString)}
	 * 
	 * These tests will run before your actual input. If any test fails, the actual
	 * data is not used.
	 * 
	 * @param tests a list where tests should be inserted..
	 */
	protected void insertTestsPart1(List<Test> tests) {
		tests.add(new Test("16,1,2,0,4,2,7,1,2,14", 37));
	}

	/**
	 * Tests for part 2
	 * 
	 * Add tests to your solution as following:
	 * 
	 * {@code new Test(multiLineStringInput,solutionString)}
	 * 
	 * These tests will run before your actual input. If any test fails, the actual
	 * data is not used.
	 * 
	 * @param tests a list where tests should be inserted..
	 */
	protected void insertTestsPart2(List<Test> tests) {
		tests.add(new Test("16,1,2,0,4,2,7,1,2,14", 168));
	}
}