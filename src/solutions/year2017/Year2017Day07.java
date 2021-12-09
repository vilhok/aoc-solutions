package solutions.year2017;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day07 extends DayX {

	HashMap<String, Node> nodes = new HashMap<String, Node>();
	Node root;

	public static <T, K> Map<K, Long> countKeys(Collection<T> collection, Function<T, K> keyFunction) {
		return collection.stream() //
				.collect( //
						Collectors.groupingBy(//
								keyFunction, Collectors.mapping(//
										keyFunction, Collectors.counting())));
	}

	static void printNames(Node n) {
		for (Node k : n.children) {
			System.out.println(k.value + " " + k.getWeight() + "(" + k.parent.value + ")"
					+ (k.children.size() > 0 ? "true" : "false") + " " + k.weight);
		}
		System.out.println();
		for (Node k : n.children) {
			printNames(k);
		}

	}

	static class Node {
		String value;
		ArrayList<Node> children = new ArrayList<Node>();
		Node parent;
		int weight;

		public Node(String name, int weight) {
			this.value = name;
			this.weight = weight;
		}

		public int getWeight() {
			int w = weight;
			for (Node s : children) {
				w += s.getWeight();
			}
			return w;
		}

	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();

		for (String s : lines) {
			String name = s.substring(0, s.indexOf("(") - 1);
			nodes.put(name, new Node(name, Integer.parseInt(s.substring(s.indexOf("(") + 1, s.indexOf(")")))));
		}
		String name = "";
		for (String s : lines) {
			name = s.substring(0, s.indexOf("(") - 1);
			if (s.contains("->")) {
				String[] names = s.split(" -> ");
				String[] children = names[1].split(", ");

				Node n = nodes.get(name);

				for (String k : children) {
					n.children.add(nodes.get(k));
					nodes.get(k).parent = n;
				}
			}

		}

		Node n = nodes.get(name);// get the latest

		while (n.parent != null) {
			n = n.parent;
		}
		this.root = n;
		return n.value;
	}

	@Override
	public Object secondPart(InputParser input) {
		if (root == null) {
			firstPart(input);
		}
		int difference = 0;
		while (root.children.size() != 0) {
			// count weights in to weight,amount map

			Map<Integer, Long> weightAmountPairs = countKeys(root.children, Node::getWeight);

			// break if there is only one type of weight left
			if (weightAmountPairs.keySet().size() == 1) {
				break;
			}

			// check difference between the required weight. This would be
			// necessary only once though.
			difference = Math.abs(//
					weightAmountPairs.keySet().stream().max(Integer::compare).get() - //
							weightAmountPairs.keySet().stream().min(Integer::compare).get());

			// update n to the child that has different weight (accept only such
			// weights that there is one of).
			root = root.children.stream().filter(e -> weightAmountPairs.get(e.getWeight()) == 1).findFirst().get();
		}

		return root.weight - difference;
	}

}
