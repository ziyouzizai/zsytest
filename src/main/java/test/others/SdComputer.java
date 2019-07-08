package test.others;

import test.myutils.FileProcesser;
import test.myutils.FileUtil;

public class SdComputer {
	private static int mask = (1 << 9) - 1;
	
	public static void main(String[] args) {
		String src = "/work/test/2019/0429/abc_mid";
		doComputer(src);
	}
	
	public static void doComputer(String srcFile) {
		final int[][] input = new int[9][9];
		FileUtil.scanFile(srcFile,new FileProcesser() {
			public void process(String fileName, long lineNum, String line) {
				if(lineNum < 10) {
					String[] arr = line.split(",");
					for(int i=0;i<9;i++) {
						int val = Integer.parseInt(arr[i]);
						input[(int)lineNum-1][i] = val ==0 ? 0 : (1 << (val - 1));
					}
				}
			}
		});
		display(input);
		int loop = 1;
		while(!process(input)) {
			loop ++;
			display(input);
		}
		display(input);
		System.out.println("loop = " + loop);
	}
	
	public static void display(int[][] input) {
		for(int i=0;i<9;i++) {
			String s = "";
			for(int j=0;j<9;j++) {
				int v = !isPower(input[i][j]) ? 0 : ((int)(Math.log(input[i][j]) / Math.log(2)) + 1);
				if(j == 0) {
					s += v;
				}else {
					s += "," + v;
				}
			}
			System.out.println(s);
		}
		System.out.println();
	}
	
	/**
	 * 遍历
	 * @param input
	 * @return 返回是否全部成功
	 */
	public static boolean process(int[][] input){
		boolean isOver = true;
		int c = 0;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(!isPower(input[i][j])) {//如果已经幂，说明已经ok
					int v = computer(input,i,j);
					if(isPower(v)) {
						c++;
					}else {
						isOver = false;//计算完后，再计算一下，如果ok，不用进行下一轮
					}
					input[i][j] = v;
				}
			}
		}
		System.out.println("change = " + c);
		return isOver;
	}
	
	/**
	 * 
	 * @param input
	 * @param i	行标
	 * @param j	列标
	 * @return
	 */
	public static int computer(int[][] input,int i,int j) {
		int ret = input[i][j];
		for(int w=0;w<9;w++) {//行校验
			if(j != w) {
				int v = input[i][w];
				if(isPower(v)) {
					ret |= v;
				}
			}
		}
		System.out.println(Integer.toBinaryString(ret));
		for(int k=0;k<9;k++) {//列校验
			if(i != k) {
				int v = input[k][j];
				if(isPower(v)) {
					ret |= v;
				}
			}
		}
		System.out.println(Integer.toBinaryString(ret));
		for(int k=3*(i/3);k<3*(i/3) + 3;k++) {//九宫格校验
			for(int w=3*(j/3);w<3*(j/3) + 3;w++) {
				if(k != i || w != j) {
					int v = input[k][w];
					if(isPower(v)) {
						ret |= v;
					}
				}
			}
		}
		//对每一行，每一列，每个九宫格中，未确定的值，进行同或运算，值为0的位，即为排他后，最可能的值
		for(int w=0;w<9;w++) {//行校验
			if(j != w) {
				int v = input[i][w];
				if(!isPower(v)) {
					ret = ~(ret ^ v);
				}
			}
		}
		System.out.println(Integer.toBinaryString(ret));
		for(int k=0;k<9;k++) {//列校验
			if(i != k) {
				int v = input[k][j];
				if(!isPower(v)) {
					ret = ~(ret ^ v);
				}
			}
		}
		System.out.println(Integer.toBinaryString(ret));
		for(int k=3*(i/3);k<3*(i/3) + 3;k++) {//九宫格校验
			for(int w=3*(j/3);w<3*(j/3) + 3;w++) {
				if(k != i || w != j) {
					int v = input[k][w];
					if(!isPower(v)) {
						ret = ~(ret ^ v);
					}
				}
			}
		}
		return mask ^ ret;
	}
	
	/**
	 * 判断是否是2的幂数
	 * @return
	 */
	public static boolean isPower(int n) {
		int k=0;
		for(int i=0;i<9;i++) {
			k += (n & (1 << i)) > 0 ? 1 : 0;
		}
		return k == 1;
	}
}
