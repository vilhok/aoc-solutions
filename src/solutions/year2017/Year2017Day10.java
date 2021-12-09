package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2017Day10 extends DayX {

	public static String hash(String input) {
		int[] std = { 17, 31, 73, 47, 23 };
		int[] values = toAsciiValues(input.toCharArray(), std.length);
		System.arraycopy(std, 0, values, input.length(), std.length);

		int[] result = hash(values, 64);

		String s = "";
		for (int b : result) {
			s += Integer.toHexString(b | 0x100).substring(1, 3);
		}
		return s;
	}

	private static int[] toAsciiValues(char[] input, int padding) {
		int[] target = new int[input.length + padding];
		int i = 0;
		for (char c : input) {
			target[i] = (int) c;
			i++;
		}
		return target;
	}

	private static int[] hash(int[] lengths, int rounds) {
		int[] original = new int[256];
		for (int i = 0; i < 256; i++) {
			original[i] = i;
		}
		int skip = 0;
		int pos = 0;
		int[] hash = new int[16];
		for (int k = 0; k < rounds; k++) {
			for (int i = 0; i < lengths.length; i++) {
				// System.out.println(lengths[i] + " " + pos);
				reverse(original, pos % original.length, lengths[i]);
				pos += (lengths[i] + skip) % 256;

				skip++;
			}
		}
		for (int i = 0; i < original.length; i++) {
			// lol at bitwise operation imlicitely casting in
			hash[i / 16] = (hash[i / 16] ^ original[i]);
		}
		return hash;
	}

	public static void reverse(int[] array, int start, int length) {
		for (int i = 0; i < length / 2; i++) {
			int ind = (start + i) % array.length;
			int tmp = array[ind];
			int index = (start + length - i - 1) % array.length;
			array[ind] = array[index];
			array[index] = tmp;

		}
	}

	@Override
	public Object firstPart(InputParser input) {
		int[] lengths = input.asSingleIntArray(Delimiter.COMMA);
		int[] original = new int[256];
		for (int i = 0; i < original.length; i++) {
			original[i] = i;
		}
		int skip = 0;
		int pos = 0;
		for (int i = 0; i < lengths.length; i++) {
			reverse(original, pos % original.length, lengths[i]);
			pos += lengths[i] + skip;
			if (pos >= original.length) {
				pos %= original.length;
			}
			skip++;
		}
//		return NOT_SOLVED;
		return original[0] * original[1];
	}

	@Override
	public Object secondPart(InputParser input) {
		return hash(input.joinLinesToString(Delimiter.NONE).replaceAll("\\s", ""));
	}
}