package test.others;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import test.myutils.FWriter;
import test.myutils.FileProcesser;
import test.myutils.FileUtil;
import test.myutils.HttpUtil;

public class Checker {

	public static void main(String[] args) {
//		check("/work/test/2019/0624/0621.dat","/work/test/2019/0624/0621_result.csv");
//		check("/work/test/2019/0624/0622.dat","/work/test/2019/0624/0622_result.csv");
//		check("/work/test/2019/0624/0623.dat","/work/test/2019/0624/0623_result.csv");
//		check("/work/test/2019/0624/0625.dat","/work/test/2019/0624/0625_result.csv");
		check("/work/test/2019/0704/0703.dat","/work/test/2019/0704/0703_result.csv");
	}

	public static void check(String input, String output) {
		FWriter fw = new FWriter(output);
		FileUtil.scanFile(input, new FileProcesser() {
			private Map<String,Long> map = new HashMap<>();
			
			public void process(String fileName, long lineNum, String line) {
				String[] arr = line.split(",");
				String idfa = arr[0];
				String intname = arr[1];
				boolean result = isOk(idfa);
				String newLine = line + "," + (result ? "1" : "0");
				fw.writeLine(newLine);
				Long flag = map.get(intname);
				if(flag == null) {
					flag = 0L;
				}
				Long n = result ? 1000L : 1L;
				flag += n;
				map.put(intname,flag);
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void end(String fileName, long totalNum) {
				map.forEach((k,v) -> {
					long y = v/1000;
					long n = v % 1000;
					float rate = (float)y/(y+n);
					System.out.println(String.format("%s -> [%d,%d] %f",k,y,(y+n),rate));
				});
			}
		});
		fw.close();
	}

	public static boolean isOk(String idfa) {
		String checkUrl = String.format(
				"http://api2.91sd.com/Aso/checkDevice/wanxian/1018/126994?source=%s&appid=%s&idfa=%s&ip=%s", "wanxian",
				"1447479561", idfa, "127.0.0.1");
		String result = HttpUtil.doGet(checkUrl);
		JSONObject json = JSON.parseObject(result);
		if (json != null && json.getJSONArray("data") != null && json.getJSONArray("data").size() > 0) {
			for (Map.Entry<String, Object> entry : json.getJSONArray("data").getJSONObject(0).entrySet()) {
				if (idfa.equals(entry.getKey())) {
					if ("1".equals(String.valueOf(entry.getValue()))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
