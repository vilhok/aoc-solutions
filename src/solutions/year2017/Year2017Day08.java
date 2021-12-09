package solutions.year2017;

import java.util.HashMap;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day08 extends DayX{

	private Integer max;

	@Override
	public Object firstPart(InputParser input){
		max = Integer.MIN_VALUE;
		List<String> lines = input.getLines();
		HashMap<String, Integer> registers = new HashMap<>();
		for(String s: lines){
			String[] ss = s.split(" ");
			int regValue = registers.getOrDefault(ss[4], 0);
			boolean condition = false;
			int expectedValue = Integer.parseInt(ss[6]);
			switch(ss[5]) {
			case "==":
				condition = (regValue == expectedValue);
				break;
			case "!=":
				condition = (regValue != expectedValue);
				break;
			case ">=":
				condition = (regValue >= expectedValue);
				break;
			case "<=":
				condition = (regValue <= expectedValue);
				break;
			case ">":
				condition = (regValue > expectedValue);
				break;
			case "<":
				condition = (regValue < expectedValue);
				break;
			default:
				System.err.println("Unknown operator:" + ss[5]);
			}
			int val = registers.getOrDefault(ss[0], 0);
			int change = Integer.parseInt(ss[2]);
			if(condition){
				if(ss[1].equals("inc")){
					registers.put(ss[0], val + change);
					max = max < val + change ? val + change : max;
				} else{
					registers.put(ss[0], val - change);
					max = max < val - change ? val - change : max;
				}
			}
		}

		return registers.values().stream().max(Integer::compare).get();
	}

	@Override
	public Object secondPart(InputParser input){
		if(max == null){
			firstPart(input);
		}
		return max;
	}
}
