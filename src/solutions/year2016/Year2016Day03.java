package solutions.year2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2016Day03 extends DayX {

	@Override
	public Object firstPart(InputParser input) {

		return input//
				.linesAsLists(Delimiter.WHITESPACE, Integer::parseInt)//
				.stream()//
				//sort the numbers into ascending order
				.map(i -> {
					Collections.sort(i);
					return i;
				})//
				//Three lines cannot be a triangle,
				.filter(i -> i.get(0) + i.get(1) > i.get(2))//
				.count();
	}

	@Override
	public Object secondPart(InputParser input) {
		List<List<Integer>> lines = input.linesAsLists(Delimiter.WHITESPACE, Integer::parseInt);

		List<List<Integer>> translated = new ArrayList<List<Integer>>();

		ArrayList<Integer> values = new ArrayList<>();
		for (int i : new int[] { 0, 1, 2 }) {
			for (int row = 0; row < lines.size(); row++) {
				values.add(lines.get(row).get(i));
				if (values.size()==3) {
					translated.add(values);
					values = new ArrayList<>();
				}
			}
		}

		return translated//
				.stream()//
				.map(i -> {
					Collections.sort(i);
					return i;
				})//
				.filter(i -> i.get(0) + i.get(1) > i.get(2))//
				.count();
		
	}
}