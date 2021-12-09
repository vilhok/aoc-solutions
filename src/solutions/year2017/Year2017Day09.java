package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2017Day09 extends DayX{
	private Integer garbage;

	private Integer solve(InputParser input){
		StringBuilder s = new StringBuilder(input.joinLinesToString(Delimiter.NONE));

		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '!'){

				s = s.replace(i, i + 2, "");
				i -= 2;
			}
		}

		String ss = new String(s.toString());
		int garbageCount = 0;
		String tmp = ss;

		do{
			ss = new String(tmp);
			tmp = ss.replaceFirst("<[^>]*>", "");
			if(ss.length() != tmp.length())
				garbageCount += ss.length() - tmp.length() - 2;
		} while(!tmp.equals(ss));


		int i = 1;
		int sum = 0;
		for(char c: tmp.toCharArray()){
			if(c == '{'){
				sum += i;
				i++;
			}
			if(c == '}')
				i--;

		}
		garbage = garbageCount;
		return sum;

	}

	@Override
	public Object firstPart(InputParser input){
		return solve(input);
	}

	@Override
	public Object secondPart(InputParser input){
		if(garbage == null){
			solve(input);
		}
		return garbage;
	}

}
