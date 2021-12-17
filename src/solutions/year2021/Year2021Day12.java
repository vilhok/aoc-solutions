package solutions.year2021;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

/**
 * <p>
 * AUTOGENERATED BY DayGenerator.java
 * </p>
 *
 * <p>
 * https://github.com/vilhok/aoc-lib
 * </p>
 *
 * <p>
 * Edits in this file will not be overwritten.
 * </p>
 *
 */
public class Year2021Day12 extends DayX {

	private class CaveGraph {

		private final String START = "start";
		private final String END = "end";

		private CaveNode start;
		private ArrayList<CaveNode> smallNodes = new ArrayList<>();

		public CaveGraph(String rootname) {
			this.start = new CaveNode(rootname);
		}

		public int countRoutes(){
			ArrayList<CaveNode> route = new ArrayList<>();
			route.add(start);
			return countRoutes(route, start);
		}

		public int uniqueRoutesOneSmallAllowedTwice(){
			ArrayList<CaveNode> route = new ArrayList<>();
			route.add(start);
			int sum = 0;
			HashSet<ArrayList<CaveNode>> unique = new HashSet<>();
			for (CaveNode s : smallNodes) {
				sum += uniqueRoutesOneSmallAllowedTwice(
					route, start, s, unique);
			}
			return sum;
		}

		private int uniqueRoutesOneSmallAllowedTwice(ArrayList<CaveNode> route,
				CaveNode tail, CaveNode specialSmallCave,
				Set<ArrayList<CaveNode>> knownRoutes){

			int routes = 0;
			if (tail.id.equals(END)) {
				if (knownRoutes.contains(route)) {
					return 0;
				}
				knownRoutes.add(route);
				return 1;
			}

			for (CaveNode current : tail.neighbors) {
				if (current.id.equals(START))
					continue;

				if (!route.contains(current)
						|| current.isBig()
						|| (current == specialSmallCave
								&& countInstances(
									route, specialSmallCave) <= 1)) {

					ArrayList<CaveNode> copy = new ArrayList<CaveNode>(route);
					copy.add(current);
					routes += uniqueRoutesOneSmallAllowedTwice(
						copy, current, specialSmallCave, knownRoutes);
				}
			}

			return routes;
		}

		private int countInstances(ArrayList<CaveNode> route, CaveNode c){
			int count = 0;

			for (CaveNode n : route) {
				if (n == c) {
					count++;
				}
			}
			return count;
		}

		private int countRoutes(ArrayList<CaveNode> route, CaveNode tail){
			int routes = 0;
			if (tail.id.equals(END)) {
				return 1;
			}
			for (CaveNode c : tail.neighbors) {
				if (!route.contains(c) || c.isBig()) {
					ArrayList<CaveNode> copy = new ArrayList<CaveNode>(route);
					copy.add(c);
					routes += countRoutes(copy, c);
				}
			}

			return routes;
		}

		public boolean addNode(String newNode, String connection){
			CaveNode c = findNodeById(connection);

			// cannot add to a nonexistent connection
			if (c == null)
				return false;

			CaveNode n = findNodeById(newNode);

			// if both nodes exist, just add the connection
			if (n != null) {
				c.addNeighbor(n);
				return true;
			}

			CaveNode newN = new CaveNode(newNode);

			// non-"end" lowercase nodes collected to a list for part2
			if (!newNode.equals(END) && newNode.toLowerCase().equals(newNode)) {
				smallNodes.add(newN);
			}

			c.addNeighbor(newN);
			return true;
		}

		private CaveNode findNodeById(String id){
			if (id.equals(start.id)) {
				return start;
			}
			ArrayDeque<CaveNode> queue = new ArrayDeque<>();
			HashSet<CaveNode> visited = new HashSet<>();

			queue.addAll(start.neighbors);
			visited.add(start);
			while (!queue.isEmpty()) {
				CaveNode c = queue.poll();
				visited.add(c);
				if (c.id.equals(id)) {
					return c;
				} else {
					queue.addAll(
						c.neighbors.stream().filter(i -> !visited.contains(i))
								.toList());
				}
			}
			return null;
		}

		private class CaveNode {

			private String id;
			private boolean big;
			private HashSet<CaveNode> neighbors;

			public CaveNode(String id) {
				this.id = id;
				big = id.toUpperCase().equals(id);
				neighbors = new HashSet<>();
			}

			public void addNeighbor(CaveNode n){
				this.neighbors.add(n);
				n.neighbors.add(this);
			}

			public boolean isBig(){
				return big;
			}

			@Override
			public boolean equals(Object o){

				if (o instanceof CaveNode c) {
					return c.id.equals(this.id);
				}
				return false;
			}

			@Override
			public String toString(){
				return id;
			}
		}
	}

	private CaveGraph buildCave(InputParser input){
		ArrayDeque<String[]> caveConnections = input
				.getLines(i -> i.split("-"), new ArrayDeque<String[]>());

		CaveGraph g = new CaveGraph("start");

		while (caveConnections.size() != 0) {
			String[] row = caveConnections.poll();
			if (!g.addNode(row[0], row[1])) {
				if (!g.addNode(row[1], row[0])) {
					caveConnections.add(row);
				}
			}
		}
		return g;
	}

	@Override
	public Object firstPart(InputParser input){
		return buildCave(input).countRoutes();
	}

	@Override
	public Object secondPart(InputParser input){
		return buildCave(input).uniqueRoutesOneSmallAllowedTwice();
	}

	/*
	 * Optional: add tests for each part in the following methods
	 *
	 * These methods have blank implementations in superclass as well and can be
	 * deleted if you don't want to include tests.
	 *
	 * Add test as follows:
	 *
	 * new Test("sampleinput", expectedSolution);
	 *
	 * Collect the tests from the task web page.
	 */

	@Override
	protected void insertTestsPart1(List<Test> tests){
		tests.add(
			new Test("start-A\n" + "start-b\n" + "A-c\n" + "A-b\n" + "b-d\n"
					+ "A-end\n" + "b-end", 10));
		tests.add(
			new Test("dc-end\n" + "HN-start\n" + "start-kj\n" + "dc-start\n"
					+ "dc-HN\n" + "LN-dc\n" + "HN-end\n" + "kj-sa\n" + "kj-HN\n"
					+ "kj-dc", 19));
		tests.add(
			new Test("fs-end\n" + "he-DX\n" + "fs-he\n" + "start-DX\n"
					+ "pj-DX\n" + "end-zg\n" + "zg-sl\n" + "zg-pj\n" + "pj-he\n"
					+ "RW-he\n" + "fs-DX\n" + "pj-RW\n" + "zg-RW\n"
					+ "start-pj\n" + "he-WI\n" + "zg-he\n" + "pj-fs\n"
					+ "start-RW", 226));
	}

	@Override
	protected void insertTestsPart2(List<Test> tests){
		tests.add(
			new Test("start-A\n" + "start-b\n" + "A-c\n" + "A-b\n" + "b-d\n"
					+ "A-end\n" + "b-end", 36));
		tests.add(
			new Test("dc-end\n" + "HN-start\n" + "start-kj\n" + "dc-start\n"
					+ "dc-HN\n" + "LN-dc\n" + "HN-end\n" + "kj-sa\n" + "kj-HN\n"
					+ "kj-dc", 103));
		tests.add(
			new Test("fs-end\n" + "he-DX\n" + "fs-he\n" + "start-DX\n"
					+ "pj-DX\n" + "end-zg\n" + "zg-sl\n" + "zg-pj\n" + "pj-he\n"
					+ "RW-he\n" + "fs-DX\n" + "pj-RW\n" + "zg-RW\n"
					+ "start-pj\n" + "he-WI\n" + "zg-he\n" + "pj-fs\n"
					+ "start-RW", 3509));
	}
}
