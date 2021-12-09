package solutions.year2020;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day05 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<String> seats = input.getLines();

		return seats.stream()//
				.map(p -> p.replaceAll("B|R", "1"))//
				.map(p -> p.replaceAll("F|L", "0"))//
				.map(p -> Integer.parseInt(p, 2))//
				.max(Integer::compareTo).get();
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> seats = input.getLines();

		List<Integer> ids = seats.stream()//
				.map(p -> p.replaceAll("B|R", "1"))//
				.map(p -> p.replaceAll("F|L", "0"))//
				.map(p -> Integer.parseInt(p))//
				.collect(Collectors.toList());

		int myseat = -1;
		for (int i = Collections.min(ids); i <= Collections.max(ids); i++) {
			if (!ids.contains(i)) {
				myseat = i;
			}
		}
		return myseat;
	}
}