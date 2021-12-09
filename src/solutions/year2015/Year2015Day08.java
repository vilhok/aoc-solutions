package solutions.year2015;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day08 extends DayX {

	int part2 = -1;

	@Override
	public Object firstPart(InputParser input) {
		int total = 0;
		int truLength = 0;
		int encodedLength = 0;
		List<String> lines = input.getLines();

		for (String s : lines) {
			total += s.length();
			// System.out.println(s+" "+ s.length());
			truLength += getLength(s);

			encodedLength += getEncondedLenght(s);

		}
		part2 = encodedLength - total;
		return total - truLength;
	}

	@Override
	public Object secondPart(InputParser input) {
		if(part2 == -1) {
			firstPart(input);
		}
		return part2;
	}

	private static int getEncondedLenght(String s) {
		int length = s.length() + 2;
		for (int i = 0; i < s.length(); i++) {

			switch (s.charAt(i)) {

			case '\"':
				length++;
				break;
			case '\\':
				length++;
				break;
			default:
			}

		}
		return length;
	}

	public static int getLength(String s) {
		s = s.substring(1, s.length() - 1);
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\\') {

				switch (s.charAt(i + 1)) {
				case '\"':
					length++;
					i++;
					break;
				case '\\':
					length++;
					i++;
					break;
				case 'x':
					length++;
					i += 3;
					break;
				default:
					System.out.println("wat");
				}
			} else {
				length++;
			}

		}
		return length;
	}
}