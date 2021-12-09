package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2017Day11 extends DayX{
	Integer max = Integer.MIN_VALUE;

	/*
	 * Using three axial coordinate systemm on the hex grid
	 * https://www.redblobgames.com/grids/hexagons/
	 */
	@Override
	protected Object firstPart(InputParser input){

		String[] data = input.joinLineValuesToArray(Delimiter.COMMA);
		int x = 0;
		int y = 0;
		int z = 0;
		for(String move: data){

			switch(move) {

			case "n":
				y++;
				z--;
				break;
			case "nw":
				y++;
				x--;
				break;
			case "ne":
				z--;
				x++;
				break;
			case "sw":
				x--;
				z++;
				break;
			case "s":
				y--;
				z++;
				break;
			case "se":
				x++;
				y--;
				break;
			}
			int distance = (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2;
			max = distance > max ? distance : max;
		}
		return (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2;
	}

	@Override
	protected Object secondPart(InputParser input){
		if(max == null){
			firstPart(input);
		}
		return max;
	}

}
