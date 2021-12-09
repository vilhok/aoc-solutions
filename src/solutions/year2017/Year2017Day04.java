package solutions.year2017;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day04 extends DayX{


	@Override
	public Object firstPart(InputParser input){
		return validLines(input.getLines(), e -> e);
	}

	@Override
	public Object secondPart(InputParser input){
		return validLines(input.getLines(), e -> sort(e));
	}

	private static int validLines(List<String> lines, Function<? super String, ? extends String> mapper){
		int valid = 0;

		for(String s: lines){
			String[] words = s.split(" ");
			// abstract the similarity checking to a set
			Set<String> hs = Arrays.stream(words).map(mapper).collect(Collectors.toSet());
			if(words.length == hs.size()){
				valid++;
			}
		}
		return valid;
	}

	/*
	 * sorting letters of a string.
	 */
	private static String sort(String word){
		char[] c = word.toCharArray();
		Arrays.sort(c);
		return new String(c);
	}
}
