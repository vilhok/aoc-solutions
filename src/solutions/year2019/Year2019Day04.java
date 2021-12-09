package solutions.year2019;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2019Day04 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		String s = input.string();
		int low = Integer.parseInt(s.split("-")[0]);
		int high = Integer.parseInt(s.split("-")[1]);

		String same = "(.)\\1";
		Pattern p = Pattern.compile(same);
		int cnt = 0;
		outer:
		for (int i = low; i <= high; i++) {
			String num = "" + i;

			Matcher m = p.matcher(num);
			if (m.find()) {
				for (int c = 0; c < num.length() - 1; c++) {
					int diff = num.charAt(c + 1) - num.charAt(c);
					if (diff < 0) {
						continue outer;
					}
				}
				cnt++;
			}

		}
		return cnt;
	}

	@Override
	public Object secondPart(InputParser input) {
		String nDigits = "(.)\\1+";
		String s = input.string();
		int low = Integer.parseInt(s.split("-")[0]);
		int high = Integer.parseInt(s.split("-")[1]);

		Pattern p = Pattern.compile(nDigits);
		int cnt = 0;

		outer:
		for (int i = low; i <= high; i++) {
			String num = "" + i;
			boolean hasTwoLong = false;
			Matcher m = p.matcher(num);
			while (m.find()) {

				for (int c = 0; c < num.length() - 1; c++) {
					int diff = num.charAt(c + 1) - num.charAt(c);
					if (diff < 0) {
						continue outer;
					}

				}
				if (m.group().length() == 2) {
					hasTwoLong = true;
				}
				
			}
			if(hasTwoLong)cnt++;

		}
		return cnt;
	}
}