package solutions.year2015;

import java.util.ArrayList;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2015Day05 extends DayX {
	static ArrayList<String> forbidden = new ArrayList<String>();
	
	
	@Override
	public Object firstPart(InputParser input) {
		forbidden.add("ab");
		forbidden.add("cd");
		forbidden.add("pq");
		forbidden.add("xy");
	
		int niceStrings =0;
		List<String> list = input.getLines();
		
		for(String s : list){
			if(testString1(s)){
				niceStrings++;
			}
		}
		return niceStrings;
	}

	@Override
	public Object secondPart(InputParser input) {
		forbidden.add("ab");
		forbidden.add("cd");
		forbidden.add("pq");
		forbidden.add("xy");
		
		
		
		int niceStrings =0;
		List<String> list = input.getLines();
		
		for(String s : list){
			if(testString2(s)){
				niceStrings++;
			}
		}
		return niceStrings;
	}
	
private static boolean testString1(String s) {
		
		for(String g : forbidden){
			if(s.contains(g))return false;
		}
		String vowels = "aeiuo";
		int nro =0;
		for(int i = 0;i<s.length();i++){
			for(int j  =0;j<vowels.length();j++){
				if(s.charAt(i) == vowels.charAt(j)){
					nro++;
				}
			}
		}
		if(nro<3)return false;
		
	
		for(int i = 0;i<s.length()-1;i++){
			if(s.charAt(i) == s.charAt(i+1)){
				return true;
			}
		}
		
		
		return false;
	}
	
	private static boolean testString2(String s) {
		
	
		boolean found = false;
		outer:
		for(int i = 0;i<s.length()-2;i++){
			for(int j = i+2;j<s.length()-1;j++){
				if(s.substring(i,i+2).equals(s.substring(j, j+2))){
					found = true;
					break outer;
				}
			}
		}
		if(!found)return false;
		
		/*
		
		int nro =0;
		for(int i = 0;i<s.length();i++){
			for(int j  =0;j<vowels.length();j++){
				if(s.charAt(i) == vowels.charAt(j)){
					nro++;
				}
			}
		}
		if(nro<3)return false;
		*/
	
		for(int i = 0;i<s.length()-2;i++){
			if(s.charAt(i) == s.charAt(i+2)){
				return true;
			}
		}
		
		
		return false;
	}
}