package solutions.year2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day06 extends DayX {

	@Override
	public Object firstPart(InputParser input) {

		return input.getGroups()//
				.stream()//
				.mapToInt(group -> group.stream()//
						.flatMap(i -> Arrays.stream(i.split("")))//
						.collect(Collectors.toSet())//
						.size())//
				.sum();
	}
	

	private Set<String> createSet(String toAdd) {
		HashSet<String> set = new HashSet<>();
		String[] answers = toAdd.split("");
		set.addAll(Arrays.asList(answers));
		return set;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> in = input.getLines();

		ArrayList<Set<String>> yes = new ArrayList<>();

		int sum = 0;

		for (String line : in) {

			if (line.isBlank()) {
				Set<String> result = yes.get(0);

				for (int i = 1; i < yes.size(); i++) {
					result.retainAll(yes.get(i));
				}
				sum += result.size();
				yes = new ArrayList<>();
			} else {
				yes.add(createSet(line));
			}
		}

		Set<String> result = yes.get(0);

		for (int i = 1; i < yes.size(); i++) {
			result.retainAll(yes.get(i));
		}
		sum += result.size();
		System.out.println(sum);
		return sum;

	}
}