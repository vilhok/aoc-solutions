package solutions.year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2019Day03 extends DayX {

	private List<Map<Point, Integer>> p2data;
	private Set<Point> intersections;

	@Override
	public Object firstPart(InputParser input) {
		List<List<String>> rows = input.linesAsLists(Delimiter.COMMA);

		List<Set<Point>> points = new ArrayList<>();
		List<Map<Point, Integer>> stepData = new ArrayList<>();

		for (List<String> data : rows) {
			int x = 0;
			int y = 0;
			Set<Point> allPoints = new HashSet<>();
			Map<Point, Integer> stepMap = new HashMap<>();
			int steps = 0;
			for (String value : data) {
				char dir = value.charAt(0);
				int distance = Integer.parseInt(value.substring(1));

				for (int i = 1; i <= distance; i++) {
					switch (dir) {
					case 'U':
						y++;
						break;
					case 'D':
						y--;
						break;
					case 'L':
						x--;
						break;
					case 'R':
						x++;
						break;
					}
					steps++;
					Point p = new Point(x, y);
					allPoints.add(p);
					// for part 2
					stepMap.putIfAbsent(p, steps);

				}
			}
			points.add(allPoints);
			stepData.add(stepMap);
		}
		Set<Point> first = points.get(0);
		Set<Point> second = points.get(1);
		first.retainAll(second);
		second.retainAll(first);
		// for part 2
		p2data = stepData;
		intersections = first;
		int result = first.stream().mapToInt(Point::distance).min().getAsInt();
		return result;
	}

	@Override
	public Object secondPart(InputParser input) {
		if (p2data == null) {
			firstPart(input);
		}

		Map<Point, Integer> row1 = p2data.get(0);
		Map<Point, Integer> row2 = p2data.get(1);

		int min = intersections.stream().map(p -> row1.get(p) + row2.get(p)).min(Integer::compareTo).get();

		return min;
	}

	record Point(int x, int y) implements Comparable<Point> {

		@Override
		public int compareTo(Point o) {
			return this.distance().compareTo(o.distance());
		}

		public Integer distance() {
			return Math.abs(x) + Math.abs(y);
		}

	}
}