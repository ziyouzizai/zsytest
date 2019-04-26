package test.net;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikileaksSpider {
	public static ExecutorService executor = Executors.newFixedThreadPool(20);
	
	public static String BASE_DIR = "/work/test/2019/0416/wikileaks/fs4";

	public static void main(String[] args) throws Exception {
		String domin = "https://file.wikileaks.org/file/";
		spide(domin);
	}
	
	public static void spide(String url) {
		SpiderTask task = new SpiderTask(url);
		executor.submit(task);
	}
}

class SpiderTask implements Runnable{
	private static Pattern urlPattern = Pattern.compile("<a.*?href=\"(.*?)\"");
	private String url;
	
	public SpiderTask(String url) {
		this.url = url;
	}
	
	@Override
	public void run() {
		try {
			downloadFile(url);
			if(HttpUtil.isHtml(url)) {
				parseAFromHtm(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseAFromHtm(String url) {
		try {
			String result = HttpUtil.get(url);
			Matcher m = urlPattern.matcher(result);
			while(m.find()) {
				String u = m.group(1);
				String subUrl = null;
				if(url.endsWith("/")) {
					subUrl = url + u;
				}else {
					subUrl = url + "/" + u;
				}
				SpiderTask task = new SpiderTask(subUrl);
				WikileaksSpider.executor.submit(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadFile(String url) throws IOException {
		String savePath = parseSavePath(url);
		File file = new File(savePath);
		File dir = new File(file.getParent());
		if(!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		FileUrlConvertUtils.download2(url, savePath);
	}
	
	public String parseSavePath(String url) {
		if(url.equals("https://file.wikileaks.org/file/")) {
			return WikileaksSpider.BASE_DIR + "/root.html";
		}
		return url.replace("https://file.wikileaks.org/file",WikileaksSpider.BASE_DIR);
	}
}


