package solutions.year2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2016Day07 extends DayX {

	String abbaRegex = "(.)(.)\\2\\1";

	String aba_bab = "(?=((.)(.)\\2))";

	String hypernet = "\\[.*?\\]";

	private boolean hasTLS(String s) {

		Pattern abba = Pattern.compile(abbaRegex);

		Matcher a = abba.matcher(s);
		while (a.find()) {
			if (!a.group(1).equals(a.group(2))) {

				return true;
			}
		}
		return false;
	}


	private List<String> getAllBlocks(String s, String regex) {
		return getAllBlocks(s, regex, 0);
	}

	private List<String> getAllBlocks(String s, String regex, int groupindex) {
		Pattern hnet = Pattern.compile(regex);
		Matcher mach = hnet.matcher(s);
		ArrayList<String> array = new ArrayList<>();
		while (mach.find()) {
			array.add(mach.group(groupindex));
		}
		return array;
	}

	private List<String> getAllNonTLSBlocks(String s) {
		return Arrays.asList(s.split(hypernet));

	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();

		Pattern hnet = Pattern.compile(hypernet);
		int count = 0;
		outer:
		for (String s : lines) {
			Matcher h = hnet.matcher(s);
			while (h.find()) {
				if (hasTLS(h.group())) {
					// an ABBA inside hypernet
					continue outer;
				}
			}

			for (String ipSegment : s.split(hypernet)) {
				if (hasTLS(ipSegment)) {
					count++;
					continue outer;
				}
			}

		}

		return count;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> lines = input.getLines(); // List.of("aba[bab]xyz", "xyx[xyx]xyx", "aaa[kek]eke", "zazbz[bzb]cdb");

		int count = 0;
		outer:
		for (String s : lines) {
			List<String> hypernetblocks = getAllBlocks(s, hypernet);
			List<String> regular = getAllNonTLSBlocks(s);

			List<String> abaSequences = new ArrayList<String>();
			List<String> babSequences = new ArrayList<String>();

			for (String h : hypernetblocks) {
				babSequences.addAll(getAllBlocks(h, aba_bab, 1).stream()//
						.filter(i -> i.charAt(1) != i.charAt(0))//
						.toList());
			}

			for (String r : regular) {
				abaSequences.addAll(getAllBlocks(r, aba_bab, 1).stream()//
						.filter(i -> i.charAt(1) != i.charAt(0))//
						.toList());
			}
			for (String a : abaSequences) {
				for (String b : babSequences) {
					if (a.substring(1).equals(b.substring(0, 2))) {
						count++;
						continue outer;
					}
				}
			}
		}
		return count;
	}
}