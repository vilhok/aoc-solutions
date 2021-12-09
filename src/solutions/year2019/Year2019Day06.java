package solutions.year2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2019Day06 extends DayX {
	HashMap<String, List<String>> orbitGraph;

	@Override
	public Object firstPart(InputParser input) {
		List<List<String>> orbits = input.linesAsLists("\\)");
		orbitGraph = new HashMap<>();

		for (List<String> orb : orbits) {
			List<String> orbit = orbitGraph.getOrDefault(orb.get(0), new ArrayList<>());

			orbit.add(orb.get(1));
			orbitGraph.putIfAbsent(orb.get(0), orbit);
		}
		// do a BFS
		ArrayDeque<String> queue = new ArrayDeque<>();
		queue.addAll(orbitGraph.get("COM"));
		int total = 0;
		int depth = 1;
		do {
			ArrayList<String> tmp = new ArrayList<>();
			while (!queue.isEmpty()) {
				String value = queue.remove();
				tmp.addAll(orbitGraph.getOrDefault(value, List.of()));
				total += depth;
			}
			depth++;
			queue.addAll(tmp);
		} while (!queue.isEmpty());
		return total;
	}

	@Override
	public Object secondPart(InputParser input) {

		if (orbitGraph == null)
			firstPart(input);
		String YOU = "YOU";
		String SANTA = "SAN";
		ArrayList<String> pathToYou = new ArrayList<>();
		findPath(new ArrayDeque<>(List.of("COM")), pathToYou, YOU);

		ArrayList<String> pathToSanta = new ArrayList<>();
		findPath(new ArrayDeque<>(List.of("COM")), pathToSanta, SANTA);


		while (pathToSanta.get(0).equals(pathToYou.get(0))) {
			pathToSanta.remove(0);
			pathToYou.remove(0);
		}
		return pathToYou.size() + pathToSanta.size() - 2;
	}

	public void findPath(ArrayDeque<String> stack, ArrayList<String> result, String search) {

		List<String> l = orbitGraph.get(stack.peek());

		if (stack.peek().equals(search)) {
			for (String string : stack) {
				result.add(string);
			}
			Collections.reverse(result);
			return;
		}
		if (orbitGraph.get(stack.peek()) == null) {
			return;
		}
		if (l != null) {
			for (String s : l) {
				if (!result.isEmpty())
					break;
				stack.push(s);
				findPath(stack, result, search);
				stack.pop();
			}
		}
	}
}