package solutions.year2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

/**
 * <p>
 * AUTOGENERATED BY DayGenerator.java
 * </p>
 *
 * <p>
 * https://github.com/vilhok/aoc-lib
 * </p>
 *
 * <p>
 * Edits in this file will not be overwritten.
 * </p>
 *
 */
public class Year2015Day16 extends DayX {

	String tickertape = """
			children: 3
			cats: 7
			samoyeds: 2
			pomeranians: 3
			akitas: 0
			vizslas: 0
			goldfish: 5
			trees: 3
			cars: 2
			perfumes: 1""";

	String regex = "Sue (.*?): (.*), (.*), (.*)";
	String regex2 = "Sue (.*?): (.+): (.+), (.+): (.+), (.+): (.+)";

	@Override
	public Object firstPart(InputParser input) {
		List<String> aunts = input.getLines();
		List<String> results = Arrays.stream(tickertape.split("\n")).toList();
		int result = 0;

		for (String sue : aunts) {
			Matcher m = Pattern.compile(regex).matcher(sue);
			m.matches();
			int n = Integer.parseInt(m.group(1));
			if (results.containsAll(List.of(m.group(2), m.group(3), m.group(4)))) {
				result = n;
				break;
			}

		}
		return result;

	}

	@Override
	public Object secondPart(InputParser input) {

		List<String> aunts = input.getLines();
		List<String> results = Arrays.stream(tickertape.split("\n")).toList();

		HashMap<String, Integer> counts = new HashMap<>();
		for (String s : results) {
			String[] v = s.split(": ");
			counts.put(v[0], Integer.parseInt(v[1]));
		}
		int result = 0;
		record Aunt(int n, HashMap<String, Integer> items) {

		}
		ArrayList<Aunt> aList = new ArrayList<>();
		for (String sue : aunts) {
			Matcher m = Pattern.compile(regex2).matcher(sue);
			m.matches();

			int n = Integer.parseInt(m.group(1));
			Aunt a = new Aunt(n, new HashMap<>());

			for (int i = 2; i < 7; i += 2) {
				a.items.put(m.group(i), Integer.parseInt(m.group(i + 1)));
			}
			aList.add(a);
		}

		String[] regular = { "children", "samoyeds", "akitas", "vizslas", "cars", "perfumes" };
		String[] lessThan = { "pomeranians", "goldfish" };
		String[] greaterThan = { "cats", "trees" };

		nextAunt:
		for (Aunt a : aList) {
			HashMap<String, Integer> props = a.items;
			for (String s : regular) {
				Integer required = counts.get(s);
				Integer present = props.getOrDefault(s, -1);
				if (present == -1)
					continue;
				if (required != present) {
					continue nextAunt;
				}
			}
			for (String s : lessThan) {
				Integer required = counts.get(s);
				Integer present = props.getOrDefault(s, -1);
				if (present == -1)
					continue;
				if (present >= required) {
					continue nextAunt;
				}
			}
			for (String s : greaterThan) {
				Integer required = counts.get(s);
				Integer present = props.getOrDefault(s, -1);
				if (present == -1)
					continue;
				if (present <= required) {
					continue nextAunt;
				}
			}
			result = a.n;
			break;
		}

		return result;
	}

	/*
	 * Optional: add tests for each part in the following methods
	 *
	 * These methods have blank implementations in superclass as well and can be
	 * deleted if you don't want to include tests.
	 *
	 * Add test as follows:
	 *
	 * new Test("sampleinput", expectedSolution);
	 *
	 * Collect the tests from the task web page.
	 */

	@Override
	protected void insertTestsPart1(List<Test> tests) {

	}

	@Override
	protected void insertTestsPart2(List<Test> tests) {

	}
}
