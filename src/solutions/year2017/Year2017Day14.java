package solutions.year2017;

import java.math.BigInteger;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2017Day14 extends DayX{


	@Override
	public Object firstPart(InputParser input){
		String s = input.joinLinesToString(Delimiter.NONE);
		int used = 0;
		for(int i = 0; i < 128; i++){
			String hash = Year2017Day10.hash(s + "-" + i);
			// ���������������������p� <-this comment was made by a cat, who
			// was sitting on the computer

			String bin = format(hash);
			used += bin.replaceAll("[^1]", "").length();

		}
		return used;

	}

	@Override
	public Object secondPart(InputParser input){

		String s = input.joinLinesToString(Delimiter.NONE);
		int[][] disk = new int[128][128];
		for(int i = 0; i < 128; i++){
			String hash = Year2017Day10.hash(s + "-" + i);
			String bin = format(hash);
			for(int j = 0; j < bin.length(); j++){
				disk[i][j] = bin.charAt(j) == '1' ? 1 : 0;

			}
		}
		boolean[][] counted = new boolean[128][128];
		int amount = 0;
		for(int i = 0; i < 128; i++){
			for(int j = 0; j < 128; j++){
				if(disk[i][j] == 1 && !counted[i][j]){
					countNeighbors(counted, disk, i, j, 1);
					amount++;
				}
			}
		}
		return amount;
	}
	

	public static String format(String s){
		BigInteger b = new BigInteger("f" + s, 16);
		return b.toString(2).substring(4);

	}

	public static int countNeighbors(boolean[][] counted, int[][] table, int x, int y, int val){
		int sum = 0;
		if(table[x][y] != val){
			return sum;
		}
		counted[x][y] = true;
		sum++;
		if(x != 0 && !counted[x - 1][y]){
			sum += countNeighbors(counted, table, x - 1, y, val);
		}
		if(y != 0 && !counted[x][y - 1]){
			sum += countNeighbors(counted, table, x, y - 1, val);
		}
		if(y < table[0].length - 1 && !counted[x][y + 1]){
			sum += countNeighbors(counted, table, x, y + 1, val);
		}
		if(x < table.length - 1 && !counted[x + 1][y]){
			sum += countNeighbors(counted, table, x + 1, y, val);
		}
		return sum;
	}
}
