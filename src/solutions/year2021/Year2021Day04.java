package solutions.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2021Day04 extends DayX {

	class BingoGrid {

		int[][] grid;
		boolean[][] marked;

		public BingoGrid(List<String> lst) {

			grid = new int[5][];
			marked = new boolean[5][];
			int i = 0;
			for (String s : lst) {
				grid[i] = Arrays.stream(s.strip().split("\\s+"))//
						.mapToInt(Integer::parseInt)//
						.toArray();
				marked[i] = new boolean[grid[i].length];
				i++;
			}
		}

		public void offerNumber(int x) {
			outer:
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == x) {
						marked[i][j] = true;
						break outer;
					}
				}
			}
		}

		public boolean checkWin() {
			for (int i = 0; i < marked.length; i++) {
				boolean oneRow = true;
				boolean oneCol = true;
				for (int j = 0; j < marked[i].length; j++) {
					if (!marked[i][j]) {
						oneRow = false;
					}
					if (!marked[j][i]) {
						oneCol = false;
					}

				}
				if (oneRow || oneCol) {
					return true;
				}
			}
			return false;
		}

		public Integer unmarkedSum() {
			int sum = 0;
			for (int i = 0; i < marked.length; i++) {
				for (int j = 0; j < marked[i].length; j++) {
					if (!marked[i][j]) {
						sum += grid[i][j];
					}
				}
			}
			return sum;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < grid.length; i++) {
				sb.append(Arrays.toString(grid[i]));
				sb.append(Arrays.toString(marked[i]));
				sb.append("\n");
			}
			return sb.toString();
		}

	}

	@Override
	public Object firstPart(InputParser input) {
		List<List<String>> in = input.getGroups();

		ArrayList<BingoGrid> bingo = new ArrayList<>();
		for (int i = 1; i < in.size(); i++) {
			bingo.add(new BingoGrid(in.get(i)));
		}

		List<Integer> bingoNumbers = Arrays.stream(in.get(0).get(0).split(",")).map(Integer::parseInt).toList();

		int result = 0;
		outer:
		for (Integer i : bingoNumbers) {

			for (BingoGrid b : bingo) {
				b.offerNumber(i);
				if (b.checkWin()) {
					result = b.unmarkedSum() * i;
					break outer;
				}
			}

		}

		return result;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<List<String>> in = input.getGroups();

		List<BingoGrid> grids = new ArrayList<>();
		for (int i = 1; i < in.size(); i++) {
			grids.add(new BingoGrid(in.get(i)));
		}

		List<Integer> bingoNumbers = Arrays.stream(in.get(0).get(0).split(",")).map(Integer::parseInt).toList();

		int result = 0;
		for (Integer i : bingoNumbers) {
			for (BingoGrid b : grids) {
				b.offerNumber(i);
			}
			BingoGrid b = grids.get(0);
			if (grids.size() == 1 && b.checkWin()) {
				result = i * b.unmarkedSum();
				break;
			} else
				grids = grids.stream().filter(x -> !x.checkWin()).toList();
		}

		return result;
	}
}