package solutions.year2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day09 extends DayX {

	private int part2=0;
	@Override
	public Object firstPart(InputParser input) {
		List<String> inputlines = input.getLines();
		
		Set<String> cities = new HashSet<>();
		
		//add all cities to a set
		for (String s : inputlines) {
			String[] values = s.split(" to | = ");
			cities.add(values[0]);
			cities.add(values[1]);
		}
		
		//add cities to an alphabetical list
		List<String> vert = new ArrayList<>(cities);
		Collections.sort(vert);
		
		//matrix for all the distances
		int[][] dist = new int[vert.size()][vert.size()];
		
		for (String s : inputlines) {
			String[] values = s.split(" to | = ");
			int start = vert.indexOf(values[0]);
			int end = vert.indexOf(values[1]);
			
			//add the distances both ways to the table
			dist[start][end] = Integer.parseInt(values[2]);
			dist[end][start] = Integer.parseInt(values[2]);
		}
		
		// Operating under the assumption that all nodes are connected
		
		ArrayList<String> copy = new ArrayList<String>(vert);
		int min = Integer.MAX_VALUE;
		
		//part 2 :D
		int max = Integer.MIN_VALUE;
		do {
			int totaldistance = 0;

//			System.out.println(copy);
			for (int i = 0; i < vert.size()-1; i++) {
				int start = vert.indexOf(copy.get(i));
				int end = vert.indexOf(copy.get(i+1));
				int distance = dist[start][end];
				if(distance==0) {
					//no route, though this was not required in the task
					//totaldistance=0;
					break;
				}
				totaldistance+=distance;
			}
			if(totaldistance<min) {
				min = totaldistance;
			}
			if(totaldistance>max) {
				max=totaldistance;
			}
		} while (hasNextPermutation(copy));
		this.part2 = max;
		return min;
	}

	public boolean hasNextPermutation(List<String> list) {
		int k = -1;
		for (int i = list.size() - 1; i > 0; i--) {
			if (list.get(i - 1).compareTo(list.get(i)) < 0) {
				k = i - 1;
				break;
			}
		}
		if (k == -1) {
			return false;
		}
		int l = list.size();
		for (int i = list.size() - 1; i > k; i--) {
			if (list.get(k).compareTo(list.get(i)) < 0) {
				l = i;
				break;
			}
		}
		swap(k, l, list);
		reverse(k + 1, list);
		return true;
	}

	private void swap(int k, int i, List<String> list) {
		String tmp = list.get(i);
		list.set(i, list.get(k));
		list.set(k, tmp);
	}

	private void reverse(int start, List<String> list) {
		List<String> endl = new ArrayList<String>(list.subList(start, list.size()));
		list.removeAll(endl);
		Collections.reverse(endl);
		list.addAll(endl);
	}

	@Override
	public Object secondPart(InputParser input) {
		if(part2==0) {
			firstPart(input);
		}
		return part2;
	}
}