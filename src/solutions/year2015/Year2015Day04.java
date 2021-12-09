package solutions.year2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2015Day04 extends DayX {
	int result2 = 0;

	@Override
	public Object firstPart(InputParser input) {
		String seed = input.joinLinesToString(Delimiter.NONE);

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		int result = 0;
		boolean first = false, second = false;
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			md.update((seed + i).getBytes());

			byte[] pls = md.digest();

			if (!first && pls[0] == 0 && pls[1] == 0 && (pls[2] & 0xf0) == 0) {
				result = i;
				first = true;
			}
			int start = pls[0] + pls[1] + pls[2];
			if (!second && start == 0) {

				result2 = i;
				second = true;
			}
			if (first && second)
				break;
		}
		return result;
	}

	@Override
	public Object secondPart(InputParser input) {
		if (result2 == 0) {
			firstPart(input);

		}
		return result2;
	}
}