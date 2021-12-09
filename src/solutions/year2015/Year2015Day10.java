package solutions.year2015;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2015Day10 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		StringBuilder s = new StringBuilder(input.joinLinesToString(Delimiter.NONE));
		for (int j = 0; j < 40; j++) {

			StringBuilder next = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				int count = 0;
				while (i < s.length() && s.charAt(i) == c) {
					count++;
					i++;
				}

				next.append(count);
				next.append(c);
				i--;
			}
			s = next;
		}
		return s.length();
	}

	@Override
	public Object secondPart(InputParser input) {
		StringBuilder s = new StringBuilder(input.joinLinesToString(Delimiter.NONE));
		for (int j = 0; j < 50; j++) {

			StringBuilder next = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				int count = 0;
				while (i < s.length() && s.charAt(i) == c) {
					count++;
					i++;
				}

				next.append(count);
				next.append(c);
				i--;
			}
			s = next;
		}
		return s.length();
	}
}