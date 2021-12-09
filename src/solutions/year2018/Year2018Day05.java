package solutions.year2018;

import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2018Day05 extends DayX {
	@Override
	public Object firstPart(InputParser input) {
		String s = input.joinLinesToString(Delimiter.NONE);

		return reactPolymer(s);
	}
	
	@Override
	public Object secondPart(InputParser input) {
		String s = input.joinLinesToString(Delimiter.NONE);

		//determine used characters from the input string
		String alphabet = s.chars()
				.mapToObj(i -> ((char) i + "").toLowerCase()) //chars() gives an int stream, map to string
				.distinct()
				.collect(Collectors.joining());

		//find the most reducing polymer
		return alphabet.chars()
				.mapToObj(i -> (char) i + "|" + Character.toUpperCase((char) i)) //create a regex in form of a|A
				.map(regex -> s.replaceAll(regex, ""))
				.mapToInt(polymer -> reactPolymer(polymer))
				.min()
				.getAsInt();
	}

	/**
	 *
	 * @param s
	 *            polymer as a string representation
	 * @return lenght of polymer after reaction (reduction)
	 */
	private Integer reactPolymer(String s) {
		StringBuilder sb = new StringBuilder(s);
		int distance = 32;
		boolean reduced;
		do {
			reduced = false;
			for (int i = 0; i < sb.length() - 1; i++) {
				if (Math.abs(sb.charAt(i) - sb.charAt(i + 1)) == distance) {
					sb.replace(i, i + 2, "");
					reduced = true;
					i--;
				}
			}
			
		} while (reduced);
		return sb.length();
	}
}