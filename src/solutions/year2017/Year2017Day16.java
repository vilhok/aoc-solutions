package solutions.year2017;

import java.util.ArrayList;
import java.util.Collections;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2017Day16 extends DayX{

	@Override
	public Object firstPart(InputParser input){
		String[] ins = input.joinLineValuesToArray(Delimiter.COMMA);
		ArrayList<Character> chars = new ArrayList<>();
		for(char c = 'a'; c < 'a' + 16; c++){
			chars.add(c);
		}
		dance(ins, chars);
		return chars.toString().replaceAll("[^a-z]", "");

	}
	
	@Override
	public Object secondPart(InputParser input){
		String[] ins = input.joinLineValuesToArray(Delimiter.COMMA);
		String original;
		ArrayList<Character> chars = new ArrayList<>();
		for(char c = 'a'; c < 'a' + 16; c++){
			chars.add(c);
		}
		original = format(chars);
		int cycle = 0;
		do{
			dance(ins, chars);
			cycle++;
		} while(!original.equals(format(chars)));
		Collections.sort(chars);
		for(int i = 0; i < 1000000000 % cycle; i++){
			dance(ins, chars);
		}
		return format(chars);
	}
	

	private void dance(String[] ins, ArrayList<Character> chars){
		for(String s: ins){
			char inst = s.charAt(0);

			switch(inst) {
			case 'x':
				int a = Integer.parseInt(s.substring(1, s.indexOf("/")));
				int b = Integer.parseInt(s.substring(s.indexOf("/") + 1));
				swap(chars, a, b);
				break;
			case 'p':
				swap(chars, chars.indexOf(s.charAt(1)), chars.indexOf(s.charAt(3)));
				break;
			case 's':
				int amount = Integer.parseInt(s.substring(1));
				for(int i = 0; i < amount; i++){
					chars.add(0, chars.remove(chars.size() - 1));
				}
				break;

			}
		}
	}

	private void swap(ArrayList<Character> chars, int a, int b){
		Character c = chars.get(a);
		chars.set(a, chars.get(b));
		chars.set(b, c);
	}



	static <T> String format(ArrayList<T> c){
		return c.toString().replaceAll("[^a-z]", "");
	}
}
