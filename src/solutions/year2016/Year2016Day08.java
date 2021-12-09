package solutions.year2016;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2016Day08 extends DayX {

	String rect = "rect (.*)x(.*)";
	String rotate = "rotate (.*) [xy]=(.*) by (.*)";

	@Override
	public Object firstPart(InputParser input) {
		String[] i = { "rect 3x2", "rotate column x=1 by 1", };
		char[][] c = new char[3][7];
		for(int x = 0;x<c.length;x++) {
			for(int y = 0;y<c[x].length;y++) {
				c[x][y] = '.';
			}
		}
		for(char[] row : c) {
			System.out.println(Arrays.toString(row).replaceAll(", ",""));
		}
		for (String s : i) {
			System.out.print(s);
			if (s.matches(rect)) {
				System.out.println("-> rect");
				Matcher m = Pattern.compile(rect).matcher(s);
				m.matches();
				int width =  Integer.parseInt(m.group(1));
				int height =  Integer.parseInt(m.group(2));
				System.out.println(" command:"+width +" "+height);
				for(int w = 0;w<width;w++) {
					for(int h = 0;h<height;h++) {
						c[h][w] = '#';
					}
				}
			}else if(s.matches(rotate)) {
				Matcher m = Pattern.compile(rotate).matcher(s);
				m.matches();
				String direction = m.group(1);
				int index = Integer.parseInt(m.group(2));
				int count = Integer.parseInt(m.group(3));

				System.out.println(" command:"+direction +" "+index +" "+count);
				
				
			}
			
			
		}
		for(char[] row : c) {
			System.out.println(Arrays.toString(row).replaceAll(", ",""));
		}
		return NOT_SOLVED;
	}

	@Override
	public Object secondPart(InputParser input) {
		return NOT_SOLVED;
	}
}