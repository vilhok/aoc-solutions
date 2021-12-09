package solutions.year2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2021Day09 extends DayX {
	@Override
	public Object firstPart(InputParser input) {
		int[][] heightmap = input.intMatrix();

		int riskLevel = 0;
		for (int i = 0; i < heightmap.length; i++) {
			for (int j = 0; j < heightmap[i].length; j++) {
				int val = heightmap[i][j];

				if (i > 0 && heightmap[i - 1][j] <= val) {
					continue;
				}
				if (j > 0 && heightmap[i][j - 1] <= val) {
					continue;
				}
				if (i < heightmap.length - 1 && heightmap[i + 1][j] <= val) {
					continue;
				}
				if (j < heightmap[i].length - 1 && heightmap[i][j + 1] <= val) {
					continue;
				}
				riskLevel += val + 1;
			}
		}

		return riskLevel;
	}

	@Override
	public Object secondPart(InputParser input) {

		int[][] heightmap = input.intMatrix();

		boolean[][] lasketut = new boolean[heightmap.length][heightmap[0].length];
		ArrayList<Integer> basins = new ArrayList<>();

		for (int i = 0; i < heightmap.length; i++) {
			for (int j = 0; j < heightmap[i].length; j++) {
				if (heightmap[i][j] == 1 && !lasketut[i][j]) {
					int alue = basinArea(heightmap, lasketut, i, j);
					basins.add(alue);
				}
			}
		}
		Collections.sort(basins);
		Collections.reverse(basins);

		return basins.get(0) * basins.get(1) * basins.get(2);
	}

	public static int basinArea(int[][] heightmap, boolean[][] visited, int x, int y) {
		//check if this square is within the bounds of this map.
		if (x < 0 || y < 0 || x >= heightmap.length || y >= heightmap[0].length) {
			return 0;
		}
		// we already were here, don't calculate it again.
		if (visited[x][y]) {
			return 0;
		}

		// this location has height 9, so it should not be counted.
		if (heightmap[x][y] == 9) {
			return 0;
		}

		// ok, let's calculate this square
		visited[x][y] = true;

		//this square counts as one.
		int area = 1;
		//let's add all the adjacent areas to this.
		area += basinArea(heightmap, visited, x + 1, y);
		area += basinArea(heightmap, visited, x - 1, y);
		area += basinArea(heightmap, visited, x, y + 1);
		area += basinArea(heightmap, visited, x, y - 1);

		return area;
	}

	@Override
	public void insertTestsPart1(List<Test> test) {

		test.add(new Test("2199943210\n" + "3987894921\n" + "9856789892\n" + "8767896789\n" + "9899965678", 15));

	}
}