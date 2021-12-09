package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day05 extends DayX{



	private int findExit(int[] numbers, int decrementOffset){

		int current = 0;
		int steps = 0;
		while(current < numbers.length){
			int temp = current;
			current += numbers[current];
			if(numbers[temp] >= decrementOffset){ // only positive offsets count!
				numbers[temp]--;
			} else{
				numbers[temp]++;
			}
			steps++;
		}
		return steps;
	}

	@Override
	public Object firstPart(InputParser input){
		int[] data = input.asSingleIntArray();
		return findExit(data, Integer.MAX_VALUE);
	}

	@Override
	public Object secondPart(InputParser input){
		int[] data = input.asSingleIntArray();
		return findExit(data, 3);
	}
}
