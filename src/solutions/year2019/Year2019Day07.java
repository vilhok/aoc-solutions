package solutions.year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

import solutions.year2019.intcodecomputer.IntcodeComputer;

public class Year2019Day07 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		long[] program = input.intCodeProgram();

		List<Integer> sequence = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
		IntcodeComputer[] computers;
		long max = 0;
		do {
			computers = new IntcodeComputer[5];
			for (int i = 0; i < computers.length; i++) {
				computers[i] = IntcodeComputer.day5capable(program);
			}
			computers[0].writeInput(sequence.get(0)).writeInput(0);
			computers[0].runProgram();

			for (int i = 0; i < computers.length - 1; i++) {
				long out = computers[i].readNext();
				computers[i + 1].writeInput(sequence.get(i + 1)).writeInput(out);
				computers[i + 1].runProgram();
			}

			long mx = computers[4].readNext();
			if (mx > max) {
				max = mx;
			}
		} while (hasNextPermutation(sequence));

		return max;
	}

	@Override
	public Object secondPart(InputParser input) {

		long[] program = input.intCodeProgram();

		List<Integer> sequence = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9));
		IntcodeComputer[] computers = new IntcodeComputer[5];

		long max = 0;
		do {
			char id = 'A';
			// init all computers
			for (int i = 0; i < computers.length; i++) {
				computers[i] = IntcodeComputer.day5capable(program);
				computers[i].setID(id++);

			}
			// connect the input streams to each other
			for (int i = 0; i < 5; i++) {
				computers[(i + 1) % 5].connect(computers[i]);

			}

			for (int i = 0; i < 5; i++) {
				computers[i].writeInput(sequence.get(i));
				computers[i].start();
			}
			computers[0].writeInput(0);
			for (IntcodeComputer ic : computers) {
				ic.join();
			}
			long next = computers[4].readNext();
			if (next > max)
				max = next;
		} while (hasNextPermutation(sequence));
		System.out.println(max);

		return max;
	}

	public boolean hasNextPermutation(List<Integer> list) {
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

	private void swap(int k, int i, List<Integer> list) {
		Integer tmp = list.get(i);
		list.set(i, list.get(k));
		list.set(k, tmp);
	}

	private void reverse(int start, List<Integer> list) {
		List<Integer> endl = new ArrayList<Integer>(list.subList(start, list.size()));
		list.removeAll(endl);
		Collections.reverse(endl);
		list.addAll(endl);
	}

}