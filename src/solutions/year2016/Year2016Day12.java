package solutions.year2016;

import java.util.HashMap;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2016Day12 extends DayX {

	@Override
	public Object firstPart(InputParser input) {

		List<List<String>> in = input.linesAsLists(Delimiter.WHITESPACE);
		Runnable[] cpu = new Runnable[in.size()];
		int i = 0;

		HashMap<String, Integer> memory = new HashMap<>();

		for (List<String> cmnd : in) {
			String s = cmnd.get(0);
			switch (s) {
			case "cpy": {
				cpu[i] = () -> {
					if (cmnd.get(1).matches("[[:alpha:]]")) {
//						memory.put(cmnd.get(2), cmnd.get(i));
					}
				};
			}

				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + s);
			}
		}
		return NOT_SOLVED;
	}

	@Override
	public Object secondPart(InputParser input) {
		return NOT_SOLVED;
	}

	protected void insertTestsPart1(List<Test> tests) {
		tests.add(new Test("cpy 41 a\n" + "inc a\n" + "inc a\n" + "dec a\n" + "jnz a 2\n" + "dec a", 42));
	}
}