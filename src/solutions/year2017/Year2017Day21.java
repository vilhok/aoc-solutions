package solutions.year2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day21 extends DayX {

	public char[][] rotateLeft(char[][] c) {
		char[][] copy = new char[c.length][c[0].length];

		int maxIndex = copy.length - 1;

		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				copy[maxIndex - j][i] = c[i][j];
			}
		}
		return copy;
	}

	public char[][] flipH(char[][] c) {
		char[][] copy = new char[c.length][c[0].length];
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				copy[i][j] = c[i][copy.length - 1 - j];
			}
		}
		return copy;
	}

	/**
	 * Actually having both flips is a bit unnecessary, as the arrays have only one
	 * "side"
	 * 
	 * @param c
	 * @return
	 */
	public char[][] flipV(char[][] c) {
		char[][] copy = new char[c.length][c[0].length];
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				copy[i][j] = c[copy.length - 1 - i][j];
			}
		}
		return copy;
	}

	public char[][] toCharArray(String s) {
		String[] rows = s.split("/");
		int length = rows[0].length();
		char[][] target = new char[length][];
		for (int i = 0; i < length; i++) {
			target[i] = rows[i].toCharArray();
		}
		return target;
	}

	public void printArray(char[][] array) {
		for (char[] c : array) {
			System.out.println(Arrays.toString(c));
		}
	}

	public char[][] copySquareArea(char[][] source, int x, int y, int size) {
		char[][] area = new char[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				area[i][j] = source[y + i][x + j];
			}
		}
		return area;
	}

	public void placeArea(char[][] source, char[][] target, int x, int y) {
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[i].length; j++) {
				target[y + i][x + j] = source[i][j];
			}
		}
	}

	public String key(char[][] arr) {
		StringBuilder sb = new StringBuilder(arr.length * arr.length);
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				sb.append(arr[i][j]);
			}
		}
		return sb.toString();
	}

	private Object runSimulation(InputParser input,int iterations) {
		List<String> inputLines = input.getLines();
		HashMap<String, char[][]> transformations = new HashMap<>();

		for (String s : inputLines) {
			String[] values = s.split(" => ");
			char[][] right = toCharArray(values[1]);
			char[][] left = toCharArray(values[0]);
			addAllRotations(transformations, right, left);
			left = flipH(left);
			addAllRotations(transformations, right, left);
		}

		String start = ".#./..#/###"; // given in task
		char[][] current = toCharArray(start);
		for (int i = 0; i < iterations; i++) {
			// how large are the chunks
			int splitSize = current.length % 2 == 0 ? 2 : 3;
			// how many chunks
			int chunks = current.length / splitSize;

			int newSize = chunks * (splitSize + 1);

			char[][] target = new char[newSize][newSize];
			for (int x = 0; x < chunks; x++) {
				for (int y = 0; y < chunks; y++) {
					int srcx = x * splitSize;
					int srcy = y * splitSize;
					char[][] area = copySquareArea(current, srcx, srcy, splitSize);

					char[][] replacement = transformations.get(key(area));

					int dstX = (splitSize + 1) * x;
					int dstY = (splitSize + 1) * y;

					placeArea(replacement, target, dstX, dstY);

				}
			}
			// printArray(target);
			current = target;

		}

		int lightsOn = 0;

		for (char[] row : current) {
			for (char value : row) {
				if (value == '#')
					lightsOn++;
			}
		}
		return lightsOn;
	}

	private void addAllRotations(HashMap<String, char[][]> transformations, char[][] right, char[][] left) {
		for (int i = 0; i < 4; i++) {
			String key = key(left);
			if (!transformations.containsKey(key)) {
				transformations.put(key, right);
			}
			left = rotateLeft(left);
		}
	}

	@Override
	public Object firstPart(InputParser input) {

		return runSimulation(input, 5);

	}

	@Override
	public Object secondPart(InputParser input) {
		return runSimulation(input, 18);
	}
}