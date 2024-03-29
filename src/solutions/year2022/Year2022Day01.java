package solutions.year2022;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
public class Year2022Day01 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		List<List<Integer>> elfgroups = input.getGroups(Integer::parseInt);
		
		//extract list sum to other function to clean up this line
		return elfgroups.stream().mapToInt(this::sum).max().getAsInt();
	}

	private int sum(List<Integer> list) {
		return list.stream().mapToInt(i -> i).sum();
	}
	
	
	@Override
	public Object secondPart(InputParser input) {
		List<List<Integer>> elfgroups = input.getGroups(Integer::parseInt);

		List<Integer> li = elfgroups.stream()//
				.mapToInt(this::sum)//
				.sorted()//
				.boxed()//
				.toList();
		
		return li.subList(li.size() - 3, li.size())//
				.stream()//
				.mapToInt(i -> i)// 
				.sum();
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
	protected void insertTestsPart1(List<Test> tests) {

	}

	@Override
	protected void insertTestsPart2(List<Test> tests) {

	}
}
