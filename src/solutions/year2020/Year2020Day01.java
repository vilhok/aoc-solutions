package solutions.year2020;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day01 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		int[] values = input.asSingleIntArray();
		int a = 0, b = 0;
	
		for (int i : values) {
			for (int j : values) {
				if (i + j == 2020) {
					return i*j;
				}
			}
		}
		return a * b;
	}

	@Override
	public Object secondPart(InputParser input) {
		int[] values = input.asSingleIntArray();

		for (int i : values) {
			for (int j : values) {
				for (int k : values) {
					if (i + j + k == 2020) {
						
						return i*j*k;
					}
				}
			}
		}
		return -1;
	}
}