package solutions.year2015;

import java.util.Arrays;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day01 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		String s = input.string();

		return Arrays.stream(s.split("")).mapToInt(e -> e.equals("(") ? 1 : -1).sum();

	}

	@Override
	public Object secondPart(InputParser input) {
		String s = input.string();
		int[] val = Arrays.stream(s.split("")).mapToInt(e -> e.equals("(") ? 1 : -1).toArray();

		int level = 0;
		int index = 0;
		do {
			level += val[index];
			index++;
		} while (level >= 0);

		return index;
	}

	protected void insertTestsPart1(List<Test> tests) {
		tests.add(new Test("(())", 0));
		tests.add(new Test("(()(()(", 3));
		tests.add(new Test("))(", -1));
		tests.add(new Test(")())())", -3));
	}

	protected void insertTestsPart2(List<Test> tests) {
		tests.add(new Test(")", 1));
		tests.add(new Test("()())", 5));
	}

}
