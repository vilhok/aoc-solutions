package solutions.year2015;

import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

	public class Year2015Day06 extends DayX {

		@Override
		public Object firstPart(InputParser input) {
			List<String> l = input.getLines();
			int[][] lights = new int[1000][1000];
			for(String s : l){
				int[] dir = parseOrders(s);
				
				for(int i = dir[1];i<=dir[3];i++){
					for(int j = dir[2];j<=dir[4];j++){
						
						switch(dir[0]){
							case 0: lights[i][j] =0;break;
							case 1: lights[i][j] =1;break;
							case 2: if(lights[i][j] ==1){
								lights[i][j] =0;
								
							}else{
								lights[i][j]=1;
							}break;
							default:System.out.print("malformed parameter"); System.exit(0);
						}
						
					}
				}
			}
			int sum = 0;
			for(int i = 0;i<1000;i++){
				for(int j = 0;j<1000;j++){
					if(lights[i][j]==1){
						sum++;
					}
				}
			}
			
			return sum;
		}

		@Override
		public Object secondPart(InputParser input) {
			List<String> l = input.getLines();
			int[][] lights = new int[1000][1000];
			for(String s : l){
				int[] dir = parseOrders(s);
				
				for(int i = dir[1];i<=dir[3];i++){
					for(int j = dir[2];j<=dir[4];j++){
						
						switch(dir[0]){
							case 0: if(lights[i][j]>0)lights[i][j]--;break;
							case 1: lights[i][j]++;break;
							case 2: lights[i][j]+=2;break;
							default:System.out.print("malformed parameter"); System.exit(0);
						}
						
					}
				}
			}
			int sum = 0;
			for(int i = 0;i<1000;i++){
				for(int j = 0;j<1000;j++){
					sum+=lights[i][j];
						
					
				}
			}
			
			return sum;
		}
		
		private static int[] parseOrders(String s) {
			
			String[] orders = s.split(" |,");
			int[] dir = new int[5];
			if(orders[1].equals("on")){
				dir[0]=1;
			}else if(orders[1].equals("off")){
				
			}else{
				dir[0] =2;
			}
			int index = 1;
			for(int i =0;i<orders.length;i++){
				try{
					int a = Integer.parseInt(orders[i]);
					dir[index] = a;
					index++;
					
				}catch(Exception e){
					
				}
			}
			
			return dir;
		}
	}