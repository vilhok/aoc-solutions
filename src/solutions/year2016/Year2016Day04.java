package solutions.year2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2016Day04 extends DayX {
	String regex = "([a-z\\-]*)([0-9]*)\\[([a-z]*)\\]";

	record NameID(String name, int id) {
	}

	ArrayList<NameID> rooms;

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();

		Pattern p = Pattern.compile(regex);
		rooms = new ArrayList<>();
		record Count(Long count, String item) implements Comparable<Count> {

			@Override
			public int compareTo(Count o) {
				int cmp = this.count.compareTo(o.count);
				if (cmp == 0) {
					// the count is the primary (ascending)
					// but the string must be (descending)
					return o.item.compareTo(this.item);
				} else {
					return cmp;
				}
			}

		}
		int sum = 0;

		for (String s : lines) {
			Matcher m = p.matcher(s);
			m.matches();

			String name = m.group(1);
			int id = Integer.parseInt(m.group(2));
			String checksum = m.group(3);

			Map<String, Long> counts = Arrays.stream(name.split(""))//
					.filter(c -> !c.equals("-"))//
					.collect(Collectors.groupingBy(c -> c, Collectors.counting()));

			ArrayList<Count> cc = new ArrayList<>();
			for (String letter : counts.keySet()) {
				cc.add(new Count(counts.get(letter), letter));
			}
			Collections.sort(cc);
			Collections.reverse(cc);

			String calculated = cc.stream().limit(5).map(i -> i.item).collect(Collectors.joining());
			if (calculated.equals(checksum)) {
				sum += id;
				// assuming only valid rooms count
				rooms.add(new NameID(name, id));
			}

		}

		return sum;
	}

	@Override
	public Object secondPart(InputParser input) {
		if (rooms == null)
			firstPart(input);
		String alphabet = "";
		for (char i = 'a'; i <= 'z'; i++) {
			alphabet += i;
		}
		int roomid = 0;
		for (NameID room : rooms) {
			StringBuilder roomName = new StringBuilder();
			for (char letter : room.name.toCharArray()) {
				if (letter == '-') {
					roomName.append(" ");
				} else {
					int i = alphabet.indexOf(letter) + room.id;
					i %= alphabet.length();
					roomName.append(alphabet.charAt(i));
				}

			}
			
			if (roomName.toString().trim().equals("northpole object storage")) {
				roomid = room.id;
				break;
			}

		}
		return roomid;
	}
}