package solutions.year2015;

import java.util.HashSet;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2015Day11 extends DayX {

	private void increment(StringBuilder sb, int index) {
		char c = sb.charAt(index);
		c++;
		if (c > 'z') {
			c = 'a';
			if (index != 0)
				increment(sb, index - 1);
		}
		sb.setCharAt(index, c);
	}

	private void nextPW(StringBuilder sb) {

		int index = sb.length() - 1;
		increment(sb, index);
	}

	private boolean valid(StringBuilder sb) {
		// consecutive
		boolean isValid = false;
		for (int i = 0; i < sb.length() - 2; i++) {
			if (sb.charAt(i) == sb.charAt(i + 1) - 1 && sb.charAt(i) == sb.charAt(i + 2) - 2) {
				isValid = true;
			}
		}
		if (!isValid)
			return isValid;

		// all characters are something else than i, o and l
		if (!sb.toString().matches("[^iol]*"))
			return false;

		// two pairs

		Set<String> s = new HashSet<>();
		for (int i = 0; i < sb.length() - 1; i++) {
			String sub = sb.substring(i, i + 2);

			if (sub.matches("(.)\\1")) {
				s.add(sub);
			}
		}

		return s.size() > 1;
	}

	@Override
	public Object firstPart(InputParser input) {
		StringBuilder passwd = new StringBuilder(input.joinLinesToString(Delimiter.NONE));
		passwd = new StringBuilder("vzbxkghb");
		do {
			nextPW(passwd);
		} while (!valid(passwd));
		return passwd;
	}

	@Override
	public Object secondPart(InputParser input) {
		StringBuilder passwd = new StringBuilder(firstPart(input).toString());
		do {
			nextPW(passwd);
		} while (!valid(passwd));
		return passwd;
		
	}
}