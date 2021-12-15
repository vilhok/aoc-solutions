package solutions.year2021;

import java.util.ArrayDeque;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

/**
 * <p>
 * AUTOGENERATED BY DayGenerator.java
 * </p>
 *
 * <p>
 * https://github.com/vilhok/aoc-lib
 * </p>
 *
 * <p>
 * Edits in this file will not be overwritten.
 * </p>
 *
 */
public class Year2021Day15 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		int[][] values = input.intMatrix();
		int[][] costs = new int[values.length][values[0].length];
		boolean[][] visited = new boolean[values.length][values[0].length];

		for (int[] row : costs) {
			for (int i = 0; i < row.length; i++) {
				row[i] = Integer.MAX_VALUE;
			}
		}

		costs[0][0] = 0;
		cheapestRoute(values, visited, costs, 0, 0);

		return costs[values.length - 1][values[0].length - 1];
	}

	@Override
	public Object secondPart(InputParser input) {
		int[][] values = input.intMatrix();
		int[][] realmap = new int[values.length * 5][values[0].length * 5];
		int[][] costs = new int[realmap.length][realmap[0].length];

		boolean[][] visited = new boolean[realmap.length][realmap[0].length];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int startx = values[0].length * j;
				int starty = values.length * i;
				copyMap(startx, starty, values, realmap);
			}
		}

		for (int[] row : costs) {
			for (int i = 0; i < row.length; i++) {
				row[i] = Integer.MAX_VALUE;
			}
		}

		costs[0][0]=0;
		cheapestRoute(realmap, visited, costs, 0, 0);

		return costs[realmap.length - 1][realmap[0].length - 1];
	}

	private void copyMap(int startx, int starty, int[][] values, int[][] realmap) {
		int offset = startx / values[0].length + starty / values.length;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				realmap[starty + i][startx + j] = (values[i][j] + offset);
				if (realmap[starty + i][startx + j] > 9) {
					realmap[starty + i][startx + j] -= 9;
				}
			}
		}

	}

	record Point(int x, int y) {
	}
	
	
	//dijkstra
	public void cheapestRoute(int[][] values, boolean[][] visited, int[][] costs, int initialx, int initialy) {

		ArrayDeque<Point> queue = new ArrayDeque<>();

		queue.add(new Point(initialx, initialy));

		while (!queue.isEmpty()) {
			Point p = findMin(queue, costs);


			if (p.x < 0 || p.y < 0 || p.x == values[0].length || p.y == values.length) {

				continue;
			}
			if (visited[p.y][p.x]) {
				continue;
			}

			
			if (boundcheck(p.x + 1, p.y, costs)) {
				int newcost = costs[p.y][p.x] + values[p.y][p.x + 1];
				if (newcost <= costs[p.y][p.x + 1]) {
					costs[p.y][p.x + 1] = newcost;
				}
			}
			if (boundcheck(p.x - 1, p.y, costs)) {
				int newcost = costs[p.y][p.x] + values[p.y][p.x - 1];
				if (newcost <= costs[p.y][p.x - 1]) {
					costs[p.y][p.x - 1] = newcost;
				}
			}
			if (boundcheck(p.x, p.y + 1, costs)) {
				int newcost = costs[p.y][p.x] + values[p.y + 1][p.x];
				if (newcost <= costs[p.y + 1][p.x]) {
					costs[p.y + 1][p.x] = newcost;
				}
			}

			if (boundcheck(p.x, p.y - 1, costs)) {
				int newcost = costs[p.y][p.x] + values[p.y - 1][p.x];
				if (newcost <= costs[p.y - 1][p.x]) {
					costs[p.y - 1][p.x] = newcost;
				}
			}

			visited[p.y][p.x] = true;

			if (boundcheck(p.x + 1, p.y, costs) && !visited[p.y][p.x + 1]) {
				queue.add(new Point(p.x + 1, p.y));
			}
			if (boundcheck(p.x - 1, p.y, costs) && !visited[p.y][p.x - 1]) {
				queue.add(new Point(p.x - 1, p.y));
			}
			if (boundcheck(p.x, p.y + 1, costs) && !visited[p.y + 1][p.x]) {
				queue.add(new Point(p.x, p.y + 1));
			}
			if (boundcheck(p.x, p.y - 1, costs) && !visited[p.y - 1][p.x]) {
				queue.add(new Point(p.x, p.y - 1));
			}
		}

	}

	
	
	
	private Point findMin(ArrayDeque<Point> queue, int[][] costs) {
		Point min = queue.peek();
		for (Point p : queue) {
			if (costs[min.y][min.x] > costs[p.y][p.x]) {
				min = p;
			}
		}
		queue.remove(min);
		return min;
	}

	public boolean boundcheck(int x, int y, int[][] table) {
		return x >= 0 && y >= 0 && x < table[0].length && y < table.length;
	}
	/*
	 * Optional: add tests for each part in the following methods
	 *
	 * These methods have blank implementations in superclass as well and can be
	 * deleted if you don't want to include tests.
	 *
	 * Add test as follows:
	 *
	 * new Test("sampleinput", expectedSolution);
	 *
	 * Collect the tests from the task web page.
	 */

	@Override
	protected void insertTestsPart1(List<Test> tests) {
		tests.add(new Test("""
				1163751742
				1381373672
				2136511328
				3694931569
				7463417111
				1319128137
				1359912421
				3125421639
				1293138521
				2311944581""", 40));
		tests.add(new Test("""
				5919
				6339
				5517
				6388
				7843
				1424""", 31));
		tests.add(new Test("""
				1119
				6139
				5117
				6118
				7913
				1114""", 11));
	}

	@Override
	protected void insertTestsPart2(List<Test> tests) {
//		tests.add(new Test("""
//				123
//				456
//				789
//				""", 5));
		tests.add(new Test("""
				1163751742
				1381373672
				2136511328
				3694931569
				7463417111
				1319128137
				1359912421
				3125421639
				1293138521
				2311944581""", 315));
	}
}
