package solutions.year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day07 extends DayX {

	static final String SHINY = "shiny gold";

	class Bag {
		String type;
		boolean canCarryGold;
		ArrayList<Bag> canCarry;

		public Bag() {
			canCarry = new ArrayList<>();
		}	

		public void addBag(Bag b) {
			canCarry.add(b);
		}

		public void setCarryGold() {
			this.canCarryGold = true;
		}

		public boolean canCarryShinyGold() {
			if (this.canCarryGold) {
				return canCarryGold;
			}
			for (Bag b : canCarry) {
				if (b.canCarryShinyGold()) {
					return true;
				}
			}
			return false;
		}

		public int countChildren() {
			int count = 1;
			for (Bag b : canCarry) {
				count += b.countChildren();
			}
			return count;
		}

	}

	class Part2Bag extends Bag {
		public void addBag(Bag b, int count) {
			for (int i = 0; i < count; i++)
				canCarry.add(b);
		}
	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();
		String regex = "bags contain";

		HashMap<String, Bag> bags = new HashMap<>();

		for (String line : lines) {
			String[] bagStats = line.split(regex);
			String bagtype = bagStats[0].strip();
			Bag b = bags.getOrDefault(bagtype, new Bag());
			bags.put(bagtype, b);
			String[] bagTypes = bagStats[1].split(",");
			for (String type : bagTypes) {
				type = type.strip();
				if (!type.equals("no other bags.")) {
					String newBagType = type.substring(type.indexOf(" ") + 1, type.lastIndexOf(" "));
					Bag child = bags.getOrDefault(newBagType, new Bag());
					bags.put(newBagType, child);
					b.addBag(child);

					if (newBagType.equals(SHINY)) {
						b.setCarryGold();
					}
				}
			}

		}

		long ccg = bags.values().stream().filter(i -> i.canCarryShinyGold()).count();

		return ccg;
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> lines = input.getLines();

		String regex = "bags contain";

		HashMap<String, Part2Bag> bags = new HashMap<>();

		for (String line : lines) {
			String[] bagStats = line.split(regex);
			String bagtype = bagStats[0].strip();
			Part2Bag b = bags.getOrDefault(bagtype, new Part2Bag());
			bags.put(bagtype, b);
			String[] bagTypes = bagStats[1].split(",");
			for (String type : bagTypes) {
				type = type.strip();
				if (!type.equals("no other bags.")) {
					String newBagType = type.substring(type.indexOf(" ") + 1, type.lastIndexOf(" "));
					int count = Integer.parseInt(type.split(" ")[0]);
					Part2Bag child = bags.getOrDefault(newBagType, new Part2Bag());
					bags.put(newBagType, child);
					b.addBag(child, count);

					if (newBagType.equals(SHINY)) {
						b.setCarryGold();
					}
				}
			}

		}

		Part2Bag bag = bags.get(SHINY);

		return bag.countChildren() - 1;
	}
}