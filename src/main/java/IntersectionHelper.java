import test.myutils.FileUtil;

public class IntersectionHelper {
	public static void main(String[] args) {
		test1();
	}
	
	public static void test1() {
		FileUtil.getFileIntersection("/work/test/2019/0419/active_device.csv","/work/test/2019/0419/ours.csv","/work/test/2019/0419/result.csv");
	}
}
