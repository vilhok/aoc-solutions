package solutions.year2017;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day22 extends DayX {

	class Point {
		int x;
		int y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Year2017Day22 getEnclosingInstance() {
			return Year2017Day22.this;
		}

	}

	enum Direction {

		UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);

		int dx;
		int dy;

		
		private Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

	}

	@Override
	public Object firstPart(InputParser input) {
		ArrayList<String> l = new ArrayList<>(input.getLines());
		Collections.reverse(l);
		HashMap<Point, Character> map = new HashMap<>();

		for (int i = 0; i < l.size(); i++) {
			String s = l.get(i);
			for (int j = 0; j < s.length(); j++) {
				char node = s.charAt(j);
				map.put(new Point(j, i), node);
			}
		}

		int x = l.get(0).length() / 2;
		int y = l.size() / 2;

		Direction[] d = Direction.values();
		// manually set a high value to get positive/negative indexes from the d by
		// useing remainder
		int currentDir = 0;

		int result = 0;
		System.out.println(x + " " + y);
		for (int i = 0; i < 10000; i++) {
			Point key = new Point(x, y);
			char node = map.getOrDefault(key, '.');
			if (node == '#') {
				currentDir++;
				node = '.';
			} else {
				currentDir--;
				node = '#';
				result++;
			}
			if (currentDir < 0) {
				currentDir = d.length - 1;
			}
			map.put(key, node);
			x += d[currentDir % 4].dx;
			y += d[currentDir % 4].dy;
		}
		return result;
	}

	@Override
	public Object secondPart(InputParser input) {
		return NOT_SOLVED;
	}
}