package solutions.year2015;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day02 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<String> rows = input.getLines();
		int l, w, h;
		int total = 0;
		for (String s : rows) {
			String[] values = s.split("x");
			l = Integer.parseInt(values[0]);
			w = Integer.parseInt(values[1]);
			h = Integer.parseInt(values[2]);
			total += 2 * l * w + 2 * w * h + 2 * l * h;

			total += Math.min(l * w, Math.min(w * h, l * h));
		}
		return total;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> rows = input.getLines();
		int total = 0;
		for (String s : rows) {
			Integer[] sides = Arrays.stream(s.split("x")).map(i -> Integer.parseInt(i)).collect(Collectors.toList()).toArray(new Integer[0]);
			Arrays.sort(sides);
			
			int length = sides[0]*2+sides[1]*2 + sides[0]* sides[1]*sides[2] ;
			total+=length;
		}
		
		
		return total;
	}
}