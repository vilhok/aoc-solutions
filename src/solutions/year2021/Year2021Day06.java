package solutions.year2021;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2021Day06 extends DayX {

	@Override
	public Object firstPart(InputParser input) {

		long[] ages = new long[9];
		int[] in = input.asSingleIntArray(Delimiter.COMMA);
		for (int i : in) {
			ages[i]++;
		}
		long totalFish = in.length;
		for (int i = 0; i < 80; i++) {
			long newFish = ages[0];
			totalFish+=ages[0];
			for (int swap = 0; swap < 8; swap++) {
				ages[swap] = ages[swap + 1];
			}
			ages[6] += (newFish);
			ages[8] = newFish;

		}
		return totalFish;
	}

	@Override
	public Object secondPart(InputParser input) {
		long[] ages = new long[9];
		int[] in = input.asSingleIntArray(Delimiter.COMMA);
		
		for (int i : in) {
			ages[i]++;
		}
		long totalFish = in.length;
		for (int i = 0; i < 256; i++) {
			long newFish = ages[0];
			totalFish+=ages[0];
			for (int swap = 0; swap < 8; swap++) {
				ages[swap] = ages[swap + 1];
			}
			ages[6] += (newFish);
			ages[8] = newFish;

		}
		return totalFish;
	}
}