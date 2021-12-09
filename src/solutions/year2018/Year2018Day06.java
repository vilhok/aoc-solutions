package solutions.year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2018Day06 extends DayX {

	record Point(int x, int y) {

		public int manhattan(int x, int y) {
			return Math.abs(x - this.x) + Math.abs(y - this.y);
		}
	}
	/**
	 * Operates under the assumption that an area that touches an edge will be infinite.
	 */

	@Override
	public Object firstPart(InputParser input) {
		List<List<Integer>> pairs = input.linesAsLists(", ", Integer::parseInt);

		int max = pairs.stream().flatMap(List::stream).mapToInt(i -> i).max().orElse(-1);

		int bound = max + 1;

		int[][] map = new int[bound][bound];

		List<Point> points = new ArrayList<>();
		Set<Integer> nonInfinite = new HashSet<>();
		
		int p = 1;
		for (List<Integer> l : pairs) {
			int x = l.get(1);
			int y = l.get(0);
			Point pnt = new Point(x, y);
			points.add(pnt);
			map[x][y] = p;
			p++;
		}

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				
				if (map[y][x] == 0) { //for all empty locations
					ArrayList<Integer> distances = new ArrayList<>();
					int closest = Integer.MAX_VALUE;
					int val = 0;
					
					//figure out distances to all points, along with the shortest
					for (int i = 0; i < points.size(); i++) {
						int dist = points.get(i).manhattan(y, x);
						if (dist <= closest) {
							closest = dist;
							val = i + 1;
						}
						distances.add(dist);
					}

					//find out if there is more than one shortest distance.
					final int value = closest;
					long count = distances.stream().filter(i -> i == value).count();

					if (count == 1) {
						if (x == 0 || y == 0 || x == bound - 1 || y == bound - 1) {
							//if this is at the edge, assume this is infinite.
							nonInfinite.remove(val);
						}
						map[y][x] = val;
					}
				}
			}
		}
		
		boolean[][] visited = new boolean[bound][bound];

		int maxArea = 0;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				if (!visited[y][x] && nonInfinite.contains(map[y][x])) {
					//BFS for all the non-infinite values to find the max area.
					int area = calculateArea(map, visited, y, x, map[y][x]);
					if (area > maxArea) {
						maxArea = area;
					}
				}
			}
		}
		return maxArea;
	}


	@Override
	public Object secondPart(InputParser input) {
		Function<String, Point> f = s -> {
			String[] value = s.split(", ");
			return new Point(Integer.parseInt(value[1]), Integer.parseInt(value[0]));
		};

		List<Point> points = input.<Point>getLines(f);

		int theArea = 0;
		for (Point p : points) {
			int size = calculateAreaWithDistance(new HashSet<Point>(), p.x, p.y, points, 10000);
			if (theArea == 0) {
				theArea = size;
			} else {
				System.out.println("ERROR: two separate areas!");
			}
		}
		return theArea;
	}

	public static int calculateArea(int[][] map, boolean[][] visited, int x, int y, int value) {
		if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
			return 0;
		}
		if (visited[x][y]) {
			return 0;
		}
		if (map[x][y] != value) {
			return 0;
		}
		visited[x][y] = true;

		int area = 1;
		area += calculateArea(map, visited, x + 1, y, value);
		area += calculateArea(map, visited, x - 1, y, value);
		area += calculateArea(map, visited, x, y + 1, value);
		area += calculateArea(map, visited, x, y - 1, value);

		return area;
	}

	public static int calculateAreaWithDistance(Set<Point> visited, int x, int y, List<Point> points, int bound) {

		if (visited.contains(new Point(x, y))) {
			return 0;
		}

		int sum = points.stream().mapToInt(p -> p.manhattan(x, y)).sum();

		if (sum >= bound)
			return 0;
		visited.add(new Point(x, y));

		int area = 1;

		area += calculateAreaWithDistance(visited, x + 1, y, points, bound);
		area += calculateAreaWithDistance(visited, x - 1, y, points, bound);
		area += calculateAreaWithDistance(visited, x, y + 1, points, bound);
		area += calculateAreaWithDistance(visited, x, y - 1, points, bound);

		return area;
	}


	@Override
	public void insertTestsPart1(List<Test> testcase) {
		testcase.add(new Test("""
				1, 1
				1, 6
				8, 3
				3, 4
				5, 5
				8, 9
				""", 17));
	}
}