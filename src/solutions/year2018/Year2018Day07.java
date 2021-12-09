package solutions.year2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2018Day07 extends DayX {


	class Node implements Comparable<Node> {
		private String nodeName;
		HashSet<Node> parents;

		public Node(String s) {
			this.nodeName = s;
			parents = new HashSet<>();
		}

		public String toString() {
			return nodeName + " >- " + parents.stream().map(i -> i.nodeName).collect(Collectors.joining(","));
		}

		public void removeParent(Node n) {
			parents.remove(n);
		}

		public String getValue() {
			return nodeName;
		}

		@Override
		public int compareTo(Node o) {
			return this.nodeName.compareTo(o.nodeName);
		}

		// assume ascii encoding
		public int time() {
			return 60 + nodeName.charAt(0) - 64;
		}
	}

	@Override
	public Object firstPart(InputParser input) {

		ArrayList<Node> remainingNodes = generateTaskList(input);
		// available nodes are such that don't have any parent tasks remaining anymore
		ArrayList<Node> available = new ArrayList<>();

		String result = "";
		while (!remainingNodes.isEmpty()) {
			// figure out any new nodes without parents
			for (Node n : remainingNodes) {
				if (!available.contains(n) && n.parents.size() == 0) {
					available.add(n);
				}
			}
			Collections.sort(available); // sort to find the alphabetically first easily
			Node next = available.remove(0);

			// remove this node from being handled in the future
			remainingNodes.remove(next);

			result += next.getValue();

			// remove the current node from the parent list of any node
			// we could also store the child nodes to any parent to make this faster
			for (Node n : remainingNodes) {
				n.removeParent(next);
			}
		}
		return result;
	}

	class NodeTime implements Comparable<NodeTime> {
		Node node;
		int timeLeft;

		public NodeTime(Node n) {
			this.node = n;
			this.timeLeft = n.time();
		}

		@Override
		public int compareTo(NodeTime o) {
			return Integer.valueOf(timeLeft).compareTo(o.timeLeft);
		}

		public String toString() {
			return node.nodeName + " " + timeLeft;
		}

	}

	@Override
	public Object secondPart(InputParser input) {
		ArrayList<NodeTime> inProgress = new ArrayList<>();
		ArrayList<Node> remainingNodes = generateTaskList(input);
		ArrayList<Node> available = new ArrayList<>();
		int totalTime = 0;
		while (!remainingNodes.isEmpty()) {
			for (Node n : remainingNodes) {
				if (!available.contains(n) && n.parents.size() == 0) {
					available.add(n);
				}
			}
			Collections.sort(available); // sort to find the alphabetically first easily

			// collect up to 5 tasks
			while (inProgress.size() < 5 && available.size() > 0) {
				Node next = available.remove(0);
				remainingNodes.remove(next);
				inProgress.add(new NodeTime(next));
			}
			
			// find out which job completes first
			Collections.sort(inProgress);
			
			//remove that from 
			NodeTime completed = inProgress.remove(0);
			for (Node n : remainingNodes) {
				n.removeParent(completed.node);
			}
			for (NodeTime n : inProgress) {
				n.timeLeft -= completed.timeLeft;
			}
			totalTime += completed.timeLeft;


		}
		return totalTime;
	}

	private ArrayList<Node> generateTaskList(InputParser input) {
		HashMap<String, Node> hm = new HashMap<>();
		List<String> ls = input.getLines();
		// format: Step X must be finished before step Y can begin.

		for (String line : ls) {
			String[] values = line.split(" ");
			String nodename = values[1];
			String childNode = values[7];

			Node later = hm.getOrDefault(childNode, new Node(childNode));
			Node earlier = hm.getOrDefault(nodename, new Node(nodename));
			later.parents.add(earlier);
			hm.put(earlier.nodeName, earlier);
			hm.put(later.nodeName, later);

		}
		return new ArrayList<>(hm.values());
	}
}