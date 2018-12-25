import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceCapacity {

	public static void main(String[] args) {
		int[][] A = {{1, 7000}, {2, 2000}, {3, 5000}, {4, 10000}};
		int[][] B = {{1, 3000}, {2, 5000}, {3, 5000}, {4, 3000}};
		
		List<int[]> result = findPair(A, B, 10000);
		for(int[] p : result){
			System.out.println(p[0] +"," + p[1]);
		}
	}
	
	/*
	 * front=[[1,3000],[2,5000],[3,7000],[4,10000]] 
	 *  back=[[1,2000],[2,3000],[3,4000],[4,50000]] ,
	 * 给一个最大的 capacity限制例如10000，*/
	
	public static List<int[]> findPair(int[][] front, int[][] back, int capacity){
		//Declare return res
		List<int[]> res = new ArrayList<>();
		
		//invalid input
		if(front == null || front.length == 0 || back == null || back.length == 0){
			return res;
		}
		
		//sort front and back
		Arrays.sort(front, (a,b)->(a[1] - b[1]));
		Arrays.sort(back, (a,b)->(a[1] - b[1]));
		
		//use two pointer to scan array front and array back. i from smallest to largest in font, j from largest to smallest in back
		int len1 = front.length;
		int len2 = back.length;
		int i1 = 0, i2 = i1; //i1 is the start index of the same element, i2 is the end index of the same element
		int j1 = len2 -1, j2 = j1;
		
		//move i2 and j1 to the right place
		while(i2 + 1 < len1 && front[i2+1][1] == front[i2][1]){
			i2++;
		}
		while(j1 - 1 >= 0 && back[j1-1][1] == back[j1][1]){
			j1--;
		}
		
		int tmp = front[0][1] + back[0][1];//smallest tmp, tmp sum which is smaller and closest to capacity
		List<int[]> tmpList = new ArrayList<>();//current closest pair 
		
		while(i2 < len1 && j1 >= 0){
			int sum = front[i2][1] + back[j1][1];
			if(sum <= capacity){
				//update current closest tmp and tmpList if got a closer sum 
				if(sum > tmp){
					tmpList.clear();
					tmp = sum;
				}
				addAllPairs(i1, i2, j1, j2, tmpList);//add all combination to tmpList
				i2++;//move i2 to right
				i1 = i2;
				while(i2 + 1 < len1 && front[i2+1][1] == front[i2][1]){
					i2++;
				}
			}else{
				j1--;
				j2 = j1;
				while(j1 - 1 >= 0 && back[j1-1][1] == back[j1][1]){
					j1--;
				}
			}
		}
		
		//convert tmpList to the return value as required
		for(int[] pair : tmpList ){
			int i = pair[0];
			int j = pair[1];
			res.add(new int[]{front[i][0], back[j][0]});
		}
		
		return res;
	}
	
	public static void addAllPairs(int i1, int i2, int j1, int j2, List<int[]> tmpList){
		//System.out.println(i1 + "" + i2 +"," + j1 +" " + j2);
		for(int i = i1; i <= i2; i++){
			for(int j = j1; j <= j2; j++){
				tmpList.add( new int[]{i, j} );
			}
		}
		return;
	}

}
