package solutions.year2020;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2020Day09 extends DayX {

	long part1 = 0;

	@Override
	public Object firstPart(InputParser input) {
		long[] numbers = input.asSingleLongArray(Delimiter.NEWLINE);
		long current = -1;
		for (int i = 25; i < numbers.length; i++) {
			current = numbers[i];
			boolean found = false;
			outer: for (int j = i - 25; j < i; j++) {
				for (int k = i - 25; k < i; k++) {
					if (numbers[j] != numbers[k] && numbers[j] + numbers[k] == current) {
						found = true;
						break outer;
					}
				}

			}
			if (!found)
				break;
		}
		part1 = current;
		return current;
	}

	@Override
	public Object secondPart(InputParser input) {
		long[] numbers = input.asSingleLongArray(Delimiter.NEWLINE);

		List<Long> s = Arrays.stream(numbers)//
				.boxed()//
				.collect(Collectors.toList());
		
		s = s.subList(0, s.indexOf(part1));

		long target = part1;
		long sum = 0;
		for (int i = 2; i < s.size(); i++) {
			for (int start = 0; start < s.size() - i; start++) {
				List<Long> sublist = s.subList(start, start + i);
				if (target == sublist.stream().mapToLong(l -> l).sum()) {
					sum = Collections.max(sublist) + Collections.min(sublist);
					break;
				}
			}
		}

		return sum;
	}
}