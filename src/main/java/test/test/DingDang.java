package test.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import test.myutils.FWriter;
import test.myutils.FileProcesser;
import test.myutils.FileUtil;
import test.net.HttpUtil;

public class DingDang {
	public static void main(String[] args) {
		String sourceFilePath = "/work/test/2019/0220/dingdang.dat";
		
		FileUtil.scanFile(sourceFilePath,new FileProcesser() {
			private String output = "/work/test/2019/0220/dingdang.csv";
			private FWriter fw = new FWriter(output);
			
			public void init(String fileName) {
				fw.init();
			}

			public void process(String fileName, long lineNum, String line) {
				fw.writeLine(String.format("%s,%d",line,idfaCheck(line)));
				if(lineNum % 200 == 0) {
					System.out.println(lineNum);
				}
				if(lineNum % 5 == 0) {
					try {
						Thread.sleep(200L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
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
		String url = "http://52.80.31.16:8083/dmp-fec/fec/v1/ads/get";
		String params = "{\"media_id\": \"fec-test\",\"camp_ids\":[ \"903a5d67d20d9bbe\",\"93e560696e2082e6\"],\"device_id\": \"%s\",\"deviceid_type\": 1}";
		return HttpUtil.doPost(url, JSON.parseObject(String.format(params, idfa)));
	}
}
