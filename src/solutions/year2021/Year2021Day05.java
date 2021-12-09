package solutions.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2021Day05 extends DayX {

	private String lineRegex = "(.*),(.*) -> (.*),(.*)";

	private long part2Solution = 0;

	class Line {

		final Point p1;
		final Point p2;
		final int dx;
		final int dy;

		Line(Point p1, Point p2) {
			// nested ternary best: figure out the direction of the line.
			dx = p1.x == p2.x ? 0 : p1.x < p2.x ? 1 : -1;
			dy = p1.y == p2.y ? 0 : p1.y < p2.y ? 1 : -1;
			this.p1 = p1;
			this.p2 = p2;
		}

		boolean isStraight() {
			return p1.x == p2.x || p1.y == p2.y;
		}
	}

	record Point(int x, int y) {
	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> in = input.getLines();
		// save all numbers to determine the max array size needed.
		// seems to be 1000 based on the input though.
		ArrayList<Integer> allNums = new ArrayList<>(in.size() * 4);

		ArrayList<Line> lines = new ArrayList<>(in.size());

		for (String s : in) {
			Matcher m = Pattern.compile(lineRegex).matcher(s);
			m.matches();
			MatchResult mr = m.toMatchResult();

			int x1 = Integer.parseInt(mr.group(1));
			int y1 = Integer.parseInt(mr.group(2));
			int x2 = Integer.parseInt(mr.group(3));
			int y2 = Integer.parseInt(mr.group(4));

			allNums.addAll(List.of(x1, y1, x2, y2));
			lines.add(new Line(new Point(x1, y1), new Point(x2, y2)));
		}

		int size = 1 + allNums.stream()//
				.max(Integer::compareTo)//
				.get()//
				.intValue();

		int[][] arr = new int[size][size];
		int[][] part2arr = new int[size][size];

		for (Line line : lines) {
			boolean straightLine = line.isStraight();
			int x = line.p1.x;
			int y = line.p1.y;
			do {
				if (straightLine) {
					arr[y][x]++;
				}
				part2arr[y][x]++;
				x += line.dx;
				y += line.dy;
			} while (x != line.p2.x || y != line.p2.y);

			// the loop ends before the final point is incremented
			if (straightLine) {
				arr[y][x]++;
			}
			part2arr[y][x]++;
		}


		part2Solution = Arrays.stream(part2arr)//
				.flatMapToInt(Arrays::stream)//
				.filter(i -> i > 1)//
				.count();


		return Arrays.stream(arr)//
				.flatMapToInt(Arrays::stream)//
				.filter(i -> i > 1)//
				.count();
	}

	@Override
	public Object secondPart(InputParser input) {
		if (part2Solution == 0) {
			firstPart(input);
		}
		return part2Solution;
	}
}