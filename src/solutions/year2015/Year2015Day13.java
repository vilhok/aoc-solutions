package solutions.year2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day13 extends DayX {
	String regex = "(.*) would (.*) (.*) happiness units by sitting next to (.*)\\.";

	record Key(String p1, String p2) {
	}

	public HashMap<Key, Integer> parseHappiness(List<String> lines) {
		Pattern p = Pattern.compile(regex);
		HashMap<Key, Integer> happiness = new HashMap<>();
		for (String s : lines) {
			Matcher m = p.matcher(s);
			m.matches();
			int abs = Integer.parseInt(m.group(3));
			int points = m.group(2).equals("gain") ? abs : -abs;
			happiness.put(new Key(m.group(1), m.group(4)), points);

		}
		return happiness;
	}

	private ArrayList<String> getNames(HashMap<Key, Integer> happiness) {
		Set<String> nameSet = new HashSet<>();

		for (Key k : happiness.keySet()) {
			nameSet.add(k.p1);
			nameSet.add(k.p2);
		}
		return new ArrayList<>(nameSet);
	}

	static int findSolution(List<String> people, HashMap<Key, Integer> happiness, int k, int knownMax) {
		// permutation algorithm from stack overflow.
		// modified to return the known max happiness value.
		for (int i = k; i < people.size(); i++) {
			Collections.swap(people, i, k);
			int max = findSolution(people, happiness, k + 1, knownMax);
			if (max > knownMax) {
				knownMax = max;
			}
			Collections.swap(people, k, i);
		}
		if (k == people.size() - 1) {
			int max = calculateHappiness(people, happiness);
			if (max > knownMax) {
				knownMax = max;
			}
		}
		return knownMax;
	}

	private static int calculateHappiness(List<String> people, HashMap<Key, Integer> happiness) {
		int total = 0;
		for (int i = 0; i < people.size(); i++) {
			Key k1 = new Key(people.get(i), people.get((i + 1) % people.size()));
			Key k2 = new Key(people.get((i + 1) % people.size()), people.get(i));

//			System.out.println(k1 + " " + k2);
			total += happiness.get(k1);
			total += happiness.get(k2);
		}
		return total;
	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();
		HashMap<Key, Integer> happiness = parseHappiness(lines);
		ArrayList<String> nameList = getNames(happiness);
		int result = findSolution(nameList, happiness, 0, Integer.MIN_VALUE);

		return result;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> lines = input.getLines();
		HashMap<Key, Integer> happiness = parseHappiness(lines);
		ArrayList<String> nameList = getNames(happiness);

		String ME = "Me";
		for (String s : nameList) {
			happiness.put(new Key(ME, s), 0);
			happiness.put(new Key(s, ME), 0);
		}
		nameList.add(ME); // as per task

		int result = findSolution(nameList, happiness, 0, Integer.MIN_VALUE);

		return result;
	}
}