package solutions.year2021;

import java.util.ArrayList;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2021Day03 extends DayX {

	int bitLength = 12;// can I assume this? Any possible test value in the input would fail.

	@Override
	public Object firstPart(InputParser input) {
		List<Integer> values = input.getLines(i -> Integer.parseInt(i, 2));

		int len = values.size();

		int[] ones = new int[bitLength];
		for (int i : values) {
			int x = 1;
			int ind = 0;
			while (x <= i) {
				ones[ind++] += (i & x) > 0 ? 1 : 0;
				x <<= 1;
			}
		}

		int result = 0;
		for (int i = ones.length - 1; i >= 0; i--) {
			result <<= 1;
			if (ones[i] > len / 2) {
				result += 1;
			}

		}

		int gamma = result;
		int epsilon = ~result & ((Integer.highestOneBit(result) << 3) - 1);

		return gamma * epsilon;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<Integer> values = input.getLines(i -> Integer.parseInt(i, 2));

		ArrayList<Integer> first = new ArrayList<>(values);
		int mask = 1 << (bitLength - 1);

		int oxygen = getFilteredList(first, mask, true, 1);

		ArrayList<Integer> second = new ArrayList<>(values);

		int co2 = getFilteredList(second, mask, false, 0);
		return oxygen * co2;
	}

	public static Integer getFilteredList(ArrayList<Integer> first, int mask, boolean majority, int tiebreaker) {
		while (first.size() > 1) {
			first = remove(mask, first, majority, tiebreaker);
			mask >>= 1;
		}
		return first.get(0);
	}

	public static ArrayList<Integer> remove(int mask, ArrayList<Integer> in, boolean keepMostCommon, int tiebreaker) {
		ArrayList<Integer> ones = new ArrayList<>();
		ArrayList<Integer> zeros = new ArrayList<>();
		for (Integer i : in) {
			if ((i & mask) == mask) {
				ones.add(i);
			} else {
				zeros.add(i);
			}
		}

		if (ones.size() == zeros.size()) {
			if (tiebreaker == 1) {
				return ones;
			} else {
				return zeros;
			}
		}

		if (keepMostCommon == ones.size() > zeros.size()) {
			return ones;
		} else {
			return zeros;

		}
	}

}