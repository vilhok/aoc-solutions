package solutions.year2017;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day06 extends DayX{

	private static int loopLength(int[] numbers){
		int iterations = 0;
		ArrayList<String> previousStates = new ArrayList<String>();
		previousStates.add(Arrays.toString(numbers));
		boolean seenBefore = false;
		while(!seenBefore){
			int max = biggestIndex(numbers);
			int iterate = numbers[max];
			numbers[max] = 0;
			for(int i = max + 1; i <= max + iterate; i++){
				numbers[i % numbers.length]++;
			}
			iterations++;
			String state = Arrays.toString(numbers);
			seenBefore = previousStates.contains(state);
			previousStates.add(state);
		}
		return iterations;
	}

	private static int biggestIndex(int[] numbers){
		int index = 0;
		for(int i = 0; i < numbers.length; i++){
			index = numbers[i] > numbers[index] ? i : index;
		}
		return index;
	}

	@Override
	public Object firstPart(InputParser input){

		int[] numbers = input.asSingleIntArray();
		return loopLength(numbers);
	}

	@Override
	public Object secondPart(InputParser input){

		int[] numbers = input.asSingleIntArray();
		loopLength(numbers);
		return loopLength(numbers);
	}
}
