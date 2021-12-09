package solutions.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2021Day08 extends DayX {

	int ONE = 2;
	int FOUR = 4;
	int SEVEN = 3;
	int EIGHT = 7;

	int TWO = 5;
	int THREE = 5;
	int FIVE = 5;
	int SIX = 6;
	int NINE = 6;
	int ZERO = 6;

	@Override
	public Object firstPart(InputParser input) {
		List<String> l = input.getLines();

		int[] allowed = { ONE, FOUR, SEVEN, EIGHT };
		int count = 0;
		for (String s : l) {
			String[] sides = s.split("\\|\\s+");
			String[] values = sides[1].split(" ");

			for (String value : values) {
				int len = value.length();
				for (int a : allowed) {
					if (len == a) {
						count++;
					}
				}
			}
		}

		return count;
	}

	/**
	 * @formatter:off
	 *  000
	 * 1   2 
	 * 1   2 
	 *  333 
	 * 4   5
	 * 4   5 
	 *  666
	 *
	 * @formatter:on
	 */

	@Override
	public Object secondPart(InputParser input) {
		List<String> l = input.getLines();

		int sum = 0;
		for (String s : l) {
			String[] sides = s.split("\\|\\s+");

			int[] digits = new int[4];

			// initialize all the available chars to all places:
			ArrayList<ArrayList<Character>> possibleDigits = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				possibleDigits.add(new ArrayList<>(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g')));
			}

			ArrayList<String> hints = new ArrayList<>(Arrays.asList(sides[0].trim().split(" ")));

			/**
			 * Remove all "easy" values
			 */
			for (String hint : hints) {
				if (hint.length() == ONE) {
					keep(possibleDigits, hint, 2, 5);
					delete(possibleDigits, hint, 0, 1, 3, 4, 6);
				}
				if (hint.length() == SEVEN) {
					keep(possibleDigits, hint, 0, 2, 5);
					delete(possibleDigits, hint, 1, 3, 4, 6);
				}
				if (hint.length() == FOUR) {
					keep(possibleDigits, hint, 1, 2, 3, 5);
					delete(possibleDigits, hint, 0, 4, 6);
				}

			}

			/*
			 * Interestingly, at this point the top letter is always known and each
			 * remaining segment contains two alternatives.
			 */

			String[] values = sides[1].trim().split(" ");

			// deduct each letter.
			for (int i = 0; i < values.length; i++) {
				//simple ones:
				if (values[i].length() == ONE) {
					digits[i] = 1;
				} else if (values[i].length() == SEVEN) {
					digits[i] = 7;
				} else if (values[i].length() == FOUR) {
					digits[i] = 4;
				} else if (values[i].length() == EIGHT) {
					digits[i] = 8;
				} else {
					List<Character> chars = charList(values[i]);

					//does it share both letters with 1?
					if (chars.containsAll(possibleDigits.get(2))) {
						
						// down to 0,3,9
						
						//if it's the only different length of the three
						if (values[i].length() == THREE) {
							digits[i] = 3;
						} else if (charList(values[i]).containsAll(possibleDigits.get(1))) {
							digits[i] = 9;
						} else {
							digits[i] = 0;
						}
						
					//no, it does not share both letters with 1
					} else { // 2,5,6
						if (values[i].length() == SIX) {
							digits[i] = 6;
						} else if (charList(values[i]).containsAll(possibleDigits.get(1))) {
							digits[i] = 5;
						} else {
							digits[i] = 2;
						}
					}
				}
			}

			int mul = 1000;
			int value = 0;
			for (int i : digits) {
				value += i * mul;
				mul /= 10;
			}
			sum += value;
		}

		return sum;

	}

	public void keep(ArrayList<ArrayList<Character>> possibleDigits, String h, int... val) {
		for (int i : val) {
			possibleDigits.get(i).retainAll(charList(h));
		}
	}

	public void delete(ArrayList<ArrayList<Character>> possibleDigits, String h, int... val) {
		for (int i : val) {
			possibleDigits.get(i).removeAll(charList(h));
		}
	}

	// Most stupid thing in java, there is no functional way to get char array to
	// list.
	public List<Character> charList(String string) {

		List<Character> chars = new ArrayList<>();

		for (char ch : string.toCharArray()) {
			chars.add(ch);
		}

		return chars;
	}

	public void insertTestsPart1(List<Test> test) {
		String s = """
				be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
				edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
				fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef |				cg cg fdcagb cbg
				fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega |				efabcd cedba gadfec cb
				aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga |				gecf egdcabf bgf bfgea
				fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf |				gebdcfa ecba ca fadegcb
				dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf |				cefg dcbef fcge gbcadfe
				bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd |				ed bcgafe cdgba cbgef
				egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg |				gbdfcae bgc cg cgb
				gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc |				fgae cfgab fg bagce
				""";

		test.add(new Test(s, 26));
	}

	public void insertTestsPart2(List<Test> test) {
		String s = """
				be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
				edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
				fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
				fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
				aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
				fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
				dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
				bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
				egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
				gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
				""";

		int[] rowValues = { 8394, 9781, 1197, 9361, 4873, 8418, 4548, 1625, 8717, 4315 };

		int i = 0;
		for (String row : s.split("\n")) {
			test.add(new Test(row, rowValues[i++]));
		}

		// final result
//		test.add(new Test(s, 61229));
	}
}