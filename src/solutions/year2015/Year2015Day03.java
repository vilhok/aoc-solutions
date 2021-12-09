package solutions.year2015;

import java.util.ArrayList;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day03 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<String> line = input.getLines();
		ArrayList<String> houses = new ArrayList<String>();
		int x = 0;
		int y = 0;
		int sameHouses = 1;
		String g = line.get(0);
		houses.add(x + "," + y);

		for (int i = 0; i < g.length(); i++) {
			char next = g.charAt(i);

			switch (next) {

			case '^':
				y++;
				break;
			case '<':
				x--;
				break;
			case '>':
				x++;
				break;
			case 'v':
				y--;
				break;
			default:
				System.out.println("unexpected character in input");

			}

			if (!houses.contains(x + "," + y)) {

				sameHouses++;
				houses.add(x + "," + y);
			}
		}

		return sameHouses;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> lines = input.getLines();
		ArrayList<String> houses = new ArrayList<String>();
		int xsanta = 0;
		int ysanta = 0;
		int robox = 0;
		int roboy = 0;
		int sameHouses = 1;
		String g = lines.get(0);
		houses.add(xsanta + "," + ysanta);

		boolean santaTurn = true;
		for (int i = 0; i < g.length(); i++) {
			char next = g.charAt(i);
			if (santaTurn) {
				switch (next) {

				case '^':
					ysanta++;
					break;
				case '<':
					xsanta--;
					break;
				case '>':
					xsanta++;
					break;
				case 'v':
					ysanta--;
					break;
				default:
					System.out.println("unexpected character in input");

				}

				if (!houses.contains(xsanta + "," + ysanta)) {

					sameHouses++;
					houses.add(xsanta + "," + ysanta);
				}
			} else {
				switch (next) {

				case '^':
					roboy++;
					break;
				case '<':
					robox--;
					break;
				case '>':
					robox++;
					break;
				case 'v':
					roboy--;
					break;
				default:
					System.out.println("unexpected character in input");
				}

				if (!houses.contains(robox + "," + roboy)) {

					sameHouses++;
					houses.add(robox + "," + roboy);
				}

			}
			santaTurn = !santaTurn;
		}
		return sameHouses;
	}
}