package solutions.year2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day12 extends DayX{
	private HashMap<Integer, Node> nodes;

	private Integer size;

	// static Set<Node> visited = new HashSet<>();

	public static Set<Node> visit(Node n, Set<Node> visited){
		visited.add(n);
		for(Node k: n.connections){
			if(!visited.contains(k))
				visit(k, visited);
		}
		return visited;

	}

	@Override
	public Object firstPart(InputParser input){
		nodes = new HashMap<>();
		// load data
		List<String> lines = input.getLines();
		for(String s: lines){
			String[] ss = s.split(" <-> ");
			int initial = Integer.parseInt(ss[0]);
			Node n = nodes.getOrDefault(initial, new Node(initial));
			if(!nodes.containsKey(initial)){
				nodes.put(initial, n);
			}
			String[] connections = ss[1].split(", ");

			for(String c: connections){
				Integer i = Integer.parseInt(c);
				if(i != initial){
					Node conn = nodes.getOrDefault(i, new Node(i));
					n.connections.add(conn);
					conn.connections.add(n);
					if(!nodes.containsKey(i)){
						nodes.put(i, conn);
					}
				}
			}
		}
		// map sets to their respective groups
		Set<Node> visited = new HashSet<>();
		int sets = 0;
		int firstSize = 0;
		for(Node n: nodes.values()){
			if(!visited.contains(n)){
				Set<Node> group = visit(n, new HashSet<Node>());
				if(n.id == 0)
					firstSize = group.size(); // first part
				visited.addAll(group);
				sets++;
			}
		}
		size = sets;
		return firstSize;
	}

	@Override
	public Object secondPart(InputParser input){

		if(size == null){
			firstPart(input);
		}
		return size;
	}

}

class Node{
	int id;
	Set<Node> connections = new HashSet<>();

	public Node(int id){
		this.id = id;
	}
}
