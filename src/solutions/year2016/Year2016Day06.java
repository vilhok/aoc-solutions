package solutions.year2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2016Day06 extends DayX {
	String part2 = null;

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();
		List<HashMap<Character, Integer>> frequencies = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			frequencies.add(new HashMap<>());
		}
		
		for (String s : lines) {
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);

				int count = frequencies.get(i).getOrDefault(c, 0) + 1;
				frequencies.get(i).put(c, count);
			}
		}

		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		for (HashMap<Character, Integer> c : frequencies) {

			char ch = c.entrySet()//
					.stream()//
					.max(Map.Entry.comparingByValue())//
					.get()//
					.getKey();
			sb.append(ch);

			char ch2 = c.entrySet()//
					.stream()//
					.min(Map.Entry.comparingByValue())//
					.get()//
					.getKey();
			sb2.append(ch2);
		}
		this.part2 = sb2.toString();
		return sb;
	}

	@Override
	public Object secondPart(InputParser input) {
		if (part2 == null) {
			firstPart(input);
		}
		return part2;
	}
}