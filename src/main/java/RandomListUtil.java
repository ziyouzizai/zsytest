
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomListUtil<T> {
	private List<T> target;
	private Random random;
	
	public RandomListUtil(List<T> list) {
		this.target = list;
		random = new SecureRandom();
	}
	
	public List<T> genRandomList(){
		int size = target.size();
		List<T> result = new ArrayList<T>();
		if(size < 1)
			return result;
		int start = random.nextInt(size - 1);
		
		result.add(target.get(start));
		int k = 1;
		int flag = -1;//符号位
		while(start + k < size || start - k >= 0) {
			if(start + flag*(k) >= 0 && start + flag*(k) < size) {
				result.add(target.get(start + flag*(k)));
			}
			flag = -flag;
			if(flag < 0)
				k++;
		}
		return result;
	}
	
	public static void main(String[] args) {
		RandomListUtil<Integer> util = new RandomListUtil<Integer>(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}));
		for(int i=0;i<5;i++) {
			System.out.println(util.genRandomList());
		}
	}
}
