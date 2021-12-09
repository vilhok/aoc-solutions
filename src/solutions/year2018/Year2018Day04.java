package solutions.year2018;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
//TODO: remove the deperated Date

public class Year2018Day04 extends DayX {
	private HashMap<Integer, Integer> sleeptimes = new HashMap<>();
	private HashMap<Integer, int[]> sleepminutes = new HashMap<>();
	private boolean firstDone = false;

	@Override
	public Object firstPart(InputParser input) {
		List<String> data = input.getLines();
		ArrayList<Event> events = new ArrayList<>();

		// parse events into objects
		for (String s : data) {
			events.add(new Event(s));

		}
		// sort chronologically
		Collections.sort(events);

		int id = -1;
		Date previousTime = null;
		for (Event e : events) {
			if (e.etype == EventType.BEGIN) {
				id = e.guardID;
			} else if (e.etype == EventType.ASLEEP) {
				previousTime = e.d;
			} else if (e.etype == EventType.WAKEUP) {
				Date c = e.d;
				int sleeptime = (int) ((c.getTime() - previousTime.getTime()) / 60000);
				int newtime = sleeptimes.getOrDefault(id, 0) + sleeptime;
				sleeptimes.put(id, newtime);
				int[] minuteTable = sleepminutes.getOrDefault(id, new int[60]);
				for (int i = previousTime.getMinutes(); i < c.getMinutes(); i++) {
					minuteTable[i]++;
				}
				sleepminutes.put(id, minuteTable);
			}
		}
		int longestID = -1;
		int max = -1;
		for (Integer i : sleeptimes.keySet()) {
			if (sleeptimes.get(i) > max) {
				max = sleeptimes.get(i);
				longestID = i;
			}
		}
		int[] minutes = sleepminutes.get(longestID);
		int maxValue = -1;
		int maxIndex = -1;
		for (int i = 0; i < minutes.length; i++) {
			if (minutes[i] > maxValue) {
				maxValue = minutes[i];
				maxIndex = i;

			}
		}
		firstDone = true;
		return maxIndex * longestID;
	}

	@Override

	public Object secondPart(InputParser input) {
		if (!firstDone) {
			firstPart(input);
		}
		int maxvalue = 0;
		int maxMinute = 0;
		int id = 0;

		for (int i : sleepminutes.keySet()) {
			int[] table = sleepminutes.get(i);
			int maxv = Arrays.stream(table).max().getAsInt();
			int maxm = Arrays.stream(table).boxed().collect(Collectors.toList()).indexOf(maxv);

			if (maxv > maxvalue) {
				maxvalue = maxv;
				maxMinute = maxm;
				id = i;
			}

		}
		return id * maxMinute;

	}

	class Event implements Comparable<Event> {

		Date d;
		int guardID = -1;
		EventType etype;

		public Event(String e) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");

			String[] data = e.split("] ");

			try {
				d = formatter.parse(data[0].replaceAll("1518", "2018").substring(1));

			} catch (ParseException e1) {

				e1.printStackTrace();
			}

			String[] eventData = data[1].split(" ");
			switch (eventData[0]) {
			case "wakes":
				etype = EventType.WAKEUP;
				break;
			case "falls":
				etype = EventType.ASLEEP;
				break;
			case "Guard":
				etype = EventType.BEGIN;
				guardID = Integer.parseInt(eventData[1].substring(1));
				break;
			default:
				break;
			}
		}

		@Override
		public String toString() {
			return d.toString() + " " + etype + " " + guardID;
		}

		@Override
		public int compareTo(Event o) {

			return d.compareTo(o.d);
		}

	}

	enum EventType {
		BEGIN, ASLEEP, WAKEUP;
	}
}