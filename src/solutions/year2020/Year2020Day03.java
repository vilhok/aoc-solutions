package solutions.year2020;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day03 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();

		return slope(lines, 3, 1);

	}

	private int slope(List<String> lines, int right, int down) {
		int trees = 0;
		int current = 0;
		
		for (int i = 0; i < lines.size(); i += down) {

			String line = lines.get(i);
			char tree = line.charAt(current % line.length());
			if (tree == '#') {
				trees++;
			}
			current += right;

		}
		return trees;
	}

	@Override
	public Object secondPart(InputParser input) {
		/*
		 * 
		 * Right 1, down 1. Right 3, down 1. (This is the slope you already checked.)
		 * Right 5, down 1. Right 7, down 1. Right 1, down 2.
		 * 
		 */
		List<String> lines = input.getLines();
		int[] rights = { 1, 3, 5, 7, 1 };
		int[] downs = { 1, 1, 1, 1, 2 };
		long result = 1;
		for (int i = 0; i < rights.length; i++) {
			result *= slope(lines, rights[i], downs[i]);
		}
		return result;
	}
}