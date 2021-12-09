package solutions.year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day14 extends DayX {
	String regex = "(.*) can fly (.*) km/s for (.*) seconds, but then must rest for (.*) seconds.";

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();

		Pattern p = Pattern.compile(regex);

		int arbitraryTargetTime = 2503;
		int maxDistance = 0;
		for (String s : lines) {
			Matcher m = p.matcher(s);
			m.matches();
			int speed = Integer.parseInt(m.group(2));
			int seconds = Integer.parseInt(m.group(3));
			int rest = Integer.parseInt(m.group(4));
			int fullCycles = arbitraryTargetTime / (seconds + rest);
			int timeLeft = arbitraryTargetTime % (seconds + rest);

			int travelled = speed * seconds * fullCycles;

			travelled += speed * ((timeLeft > seconds) ? seconds : timeLeft);
			if (travelled > maxDistance) {
				maxDistance = travelled;
			}

		}
		return maxDistance;
	}

	class Reindeer {
		final int speed;
		final int travelTime;
		final int restTime;
		boolean sleeping;
		int counter;
		int distance;
		int score;

		public Reindeer(int speed, int travelTime, int restTime) {
			super();
			this.speed = speed;
			this.travelTime = travelTime;
			this.restTime = restTime;
			sleeping = false;
			counter = travelTime;
		}

		public void move() {
			if (!sleeping) {
				distance += speed;
			}
			counter--;
			if (counter == 0) {
				if (sleeping) {
					counter = travelTime;
				} else {
					counter += restTime;
				}
				sleeping = !sleeping;
			}
		}

		@Override
		public String toString() {
			return String.valueOf(score);
		}
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> lines = input.getLines();

		Pattern p = Pattern.compile(regex);
		int arbitraryTargetTime = 2503;
		ArrayList<Reindeer> rd = new ArrayList<>();
		// same parsing from task 1
		for (String s : lines) {
			Matcher m = p.matcher(s);
			m.matches();
			int speed = Integer.parseInt(m.group(2));
			int seconds = Integer.parseInt(m.group(3));
			int rest = Integer.parseInt(m.group(4));
			rd.add(new Reindeer(speed, seconds, rest));
		}

		for (int i = 0; i < arbitraryTargetTime; i++) {
			rd.forEach(Reindeer::move);

			// Group the reindeer per points they have
			Map<Integer, List<Reindeer>> groups = rd.stream()//
					.collect(Collectors.groupingBy(r -> r.distance));
			// get the list of reindeer with max distance
			List<Reindeer> best = groups.get(groups.keySet().stream().mapToInt(k -> k).max().getAsInt());
			
			best.forEach(r -> r.score++);
		}
		return rd.stream().mapToInt(i -> i.score).max().getAsInt();
	}
}