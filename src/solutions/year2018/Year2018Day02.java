package solutions.year2018;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2018Day02 extends DayX {



	@Override
	public Object firstPart(InputParser input){
		List<String> strings = input.getLines();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		int two = 0; // how many strings have a pair
		int three = 0; // how many strings have a triplet

		next: for(String s: strings){
			boolean twoFound = false;
			boolean threeFound = false;

			for(int i = 0; i < alphabet.length(); i++){
				int l = s.length();
				s = s.replace("" + alphabet.charAt(i), "");

				if(!twoFound && s.length() + 2 == l){ // find first instance where length is changed by 2 after
														// replacing (pair is found)
					two++;
					twoFound = true;
				}
				if(!threeFound && s.length() + 3 == l){ // same for triplet
					three++;
					threeFound = true;
				}
				if(twoFound && threeFound)
					continue next;
			}
		}
		return two * three;
	}

	@Override
	public Object secondPart(InputParser input){
		List<String> strings = input.getLines();
		int strLen = strings.get(0).length();// assuming all are same length

		// so many for loops
		for(int i = 0; i < strings.size(); i++){
			for(int j = i + 1; j < strings.size(); j++){
				for(int k = 0; k < strLen; k++){
					// try to replace a character in both strings
					StringBuilder a = new StringBuilder(strings.get(i));
					StringBuilder b = new StringBuilder(strings.get(j));
					a.setCharAt(k, ' ');
					b.setCharAt(k, ' ');
					// if they match, return the unchanged character (parse out the space)
					if(a.toString().equals(b.toString())){
						return a.toString().replaceAll(" ", "");
					}
				}
			}
		}
		return null;
	}

}