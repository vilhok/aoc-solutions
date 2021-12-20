package utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static List<Point> fourNeighbors() {
		return List.of(new Point(-1, 0), new Point(1, 0), new Point(0, 1), new Point(0, -1));
	}

	public static List<Point> eightNeighbors() {
		ArrayList<Point> p = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x != 0 && y != 0) {
					p.add(new Point(x, y));
				}
			}
		}
		return p;
	}

	public static List<Point> nineNeighbors() {
		ArrayList<Point> p = new ArrayList<>();
		for (int y = -1; y <= 1; y++) {
			for (int x = -1; x <= 1; x++) {

				p.add(new Point(x, y));

			}
		}
		return p;
	}
}
