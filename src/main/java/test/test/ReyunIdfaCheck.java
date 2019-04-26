package test.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import test.myutils.FWriter;
import test.myutils.FileProcesser;
import test.myutils.FileUtil;
import test.net.HttpUtil;

public class ReyunIdfaCheck {
	public static void main(String[] args) {
		check("/work/test/2019/0409/feicui_no_match.csv","/work/test/2019/0409/feicui_result_0416_2.csv");
	}
	
	public static void check(String srcFile,String distFile) {
		String url = "http://idfa.trackingio.com/tkioIdfa";
		String paramTemp = "appid=1380567884&idfa=%s";
		FWriter fw = new FWriter(distFile);
		fw.init();
		FileUtil.scanFile(srcFile,new FileProcesser() {
			@Override
			public void process(String fileName, long lineNum, String line) {
				try {
					String result = HttpUtil.post(url,String.format(paramTemp,line));
					JSONObject json = JSON.parseObject(result);
					int flag = json.getInteger(line);
					fw.writeLine(String.format("%s,%d",line,flag));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void end(String fileName, long totalNum) {
				
			}
		});
		fw.close();
	}
}
