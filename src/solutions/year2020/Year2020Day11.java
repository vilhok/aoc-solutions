package solutions.year2020;

import java.util.HashMap;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day11 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		char[][] table = input.charMatrix();

		record Point(int x, int y) {
		}
		// omit bounds checking with hash map
		HashMap<Point, Character> seats = new HashMap<>();

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				seats.put(new Point(i, j), table[i][j]);
			}
		}

		HashMap<Point, Character> newSeats = new HashMap<>();

		int changes = 0;
		int iterations = 0;
		do {
			changes = 0;
			for (Point p : seats.keySet()) {
				char seat = seats.get(p);
				if (seat == '.')
					continue;
				int neighbors = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i == 0 && j == 0) {
							continue;
						}
						Point key = new Point(p.x + i, p.y + j);
						if (seats.getOrDefault(key, '.') == '#') {
							neighbors++;
						}
					}
				}

				if (neighbors == 0 && seat == 'L') {
					newSeats.put(p, '#');
					changes++;
				} else if (neighbors >= 4 && seat == '#') {
					newSeats.put(p, 'L');
					changes++;
				} else {
					newSeats.put(p, seat);
				}
			}

			seats = new HashMap<>(newSeats);
			iterations++;
		} while (changes > 0);

		System.out.println(iterations);
		long cnt = seats.values().stream().filter(e -> e.equals('#')).count();
		System.out.println(cnt);
		return cnt;
	}

	@Override
	public Object secondPart(InputParser input) {
		char[][] table = input.charMatrix();

		record Point(int x, int y) {
		}
		// omit bounds checking with hash map
		HashMap<Point, Character> seats = new HashMap<>();

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				seats.put(new Point(i, j), table[i][j]);
			}
		}

		HashMap<Point, Character> newSeats = new HashMap<>();

		int changes = 0;
		int iterations = 0;
		do {
			changes = 0;
			for (Point p : seats.keySet()) {
				char seat = seats.get(p);
				if (seat == '.')
					continue;
				int neighbors = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i == 0 && j == 0) {
							continue;
						}
						Point key = new Point(p.x + i, p.y + j);
						if (seats.getOrDefault(key, '.') == '#') {
							neighbors++;
						}
					}
				}

				if (neighbors == 0 && seat == 'L') {
					newSeats.put(p, '#');
					changes++;
				} else if (neighbors >= 4 && seat == '#') {
					newSeats.put(p, 'L');
					changes++;
				} else {
					newSeats.put(p, seat);
				}
			}

			seats = new HashMap<>(newSeats);
			iterations++;
		} while (changes > 0);

		System.out.println(iterations);
		long cnt = seats.values().stream().filter(e -> e.equals('#')).count();
		System.out.println(cnt);
		return NOT_SOLVED;
	}
}