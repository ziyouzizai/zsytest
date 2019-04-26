package test.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import test.myutils.FWriter;
import test.myutils.FileProcesser;
import test.myutils.FileUtil;
import test.net.HttpUtil;

public class IdfaCheck {
	public static void main(String[] args) {
		if(args != null && args.length == 2) {
			doCheck(args[0],args[1]);
		}else {
//			doCheck("/work/test/2019/0220/idfa_check.dat","/work/test/2019/0220/idfa_check.csv");
//			doCheck("/work/test/2019/0322/force.dat","/work/test/2019/0322/force.csv");
//			doCheck("/work/test/2019/0220/dingdang.dat","/work/test/2019/0220/dingdang2.csv");
			
//			doCheck("/work/test/2019/0326/aiyingli_0401_idfa.csv","/work/test/2019/0326/aiyingli_0401_idfa_result.csv");
			doCheck("/work/test/2019/0326/aiyingli_all_idfa.csv","/work/test/2019/0326/aiyingli_all_idfa_result.csv");
		}
	}
	
	public static void doCheck(String sourcePathName,String distPathName) {
		FileUtil.scanFile(sourcePathName,new FileProcesser() {
			private String output = distPathName;
			private FWriter fw = new FWriter(output);
			
			public void init(String fileName) {
				fw.init();
			}

			public void process(String fileName, long lineNum, String line) {
				try {
					String[] array = line.split(",");
					String idfa = array[0];
					fw.writeLine(String.format("%s,%d",line,idfaCheck(idfa)));
				}catch(Throwable e) {
					System.out.println("exception:" + line);
					try {
						Thread.sleep(3000L);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
				if(lineNum % 200 == 0) {
					System.out.println(lineNum);
				}
//				if(lineNum % 50 == 0) {
//					try {
//						Thread.sleep(50L);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
			}

			public void end(String fileName, long totalNum) {
				fw.close();
			}
		});
	}

	public static int idfaCheck(String idfa) {
		JSONObject res = req(idfa);
		JSONArray arr = res.getJSONArray("camp_ids");
		int result = 0;
		if(arr.size() == 0)
			return result;
		if(arr.contains("93e560696e2082e6")) {
			result += 1;
		}
		if(arr.contains("903a5d67d20d9bbe")) {
			result += 2;
		}
		return result;
	}

	public static JSONObject req(String idfa) {
//		String url = "http://52.80.31.16:8083/dmp-fec/fec/v1/ads/get";
		String url = "http://52.80.213.154:8083/dmp-fec/fec/v1/ads/get";
		String params = "{\"media_id\": \"fec-test\",\"camp_ids\":[ \"903a5d67d20d9bbe\",\"93e560696e2082e6\"],\"device_id\": \"%s\",\"deviceid_type\": 1}";
		return HttpUtil.doPost(url, JSON.parseObject(String.format(params, idfa)));
	}
}
