package test.net;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.myutils.FWriter;

public class WikileaksSpider {
	public static ExecutorService executor = Executors.newFixedThreadPool(20);
	
	public static String BASE_DIR = "/work/test/2019/0416/wikileaks/fs5";
	
	public static void main(String[] args) throws Exception {
//		String domin = "https://file.wikileaks.org/file/";
		String domin = "https://file.wikileaks.org/file/";
//		String domin = "https://file.wikileaks.org/file/budapest-gay-rights-riot-2008/";
		spide(domin);
		
	}
	
	public static void spide(String url) {
		SpiderTask task = new SpiderTask(url);
		executor.submit(task);
	}
}

class SpiderTask implements Runnable{
	final public static FWriter fw = new FWriter("/work/test/2019/0416/wikileaks/log");
	final public static FWriter flist = new FWriter("/work/test/2019/0416/wikileaks/log_list");
	final public static FWriter fovered = new FWriter("/work/test/2019/0416/wikileaks/log_overed");
	private static Pattern urlPattern = Pattern.compile("<a.*?href=\"(.*?)\"");
	private String url;
	
	public SpiderTask(String url) {
		this.url = url;
	}
	
	@Override
	public void run() {
		try {
//			url = URLDecoder.decode(url,"utf-8");
			if(!HttpUtil.isHttpUrl(url)) {
				fw.writeLine("[INVALID_URL]url=" + url);
				System.out.println("[INVALID_URL]url=" + url);
				fw.flush();
				fovered.writeLine(url);
				fovered.flush();
				return ;
			}
				
			if(HttpUtil.isHtml(url)) {
				parseAFromHtm(url);
				fw.writeLine("[PARSEED]url=" + url);
				System.out.println("[PARSEED]url=" + url);
				fw.flush();
			}else {
				downloadFile(url);
				fw.writeLine("[DOWNLOADED]url=" + url);
				System.out.println("[DOWNLOADED]url=" + url);
				fw.flush();
			}
			fovered.writeLine(url);
			fovered.flush();
		} catch (Throwable e) {
			fw.writeLine("[ERROR]url=" + url);
			System.out.println("[ERROR]url=" + url);
		}
	}
	
	public void parseAFromHtm(String url) throws Throwable {
		try {
			String result = HttpUtil.get(url);
			Matcher m = urlPattern.matcher(result);
			while(m.find()) {
				String u = m.group(1);
				if("../".equals(u) || u.startsWith("../")) {
					continue;
				}
				String subUrl = null;
				if(url.endsWith("/")) {
					subUrl = url + u;
				}else {
					subUrl = url + "/" + u;
				}
				SpiderTask task = new SpiderTask(subUrl);
				flist.writeLine(subUrl);
				flist.flush();
				WikileaksSpider.executor.submit(task);
			}
		} catch (Throwable e) {
			throw e;
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


