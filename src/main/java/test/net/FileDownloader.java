package test.net;

import java.io.IOException;

import test.myutils.FileProcesser;
import test.myutils.FileUtil;

public class FileDownloader {
	public static void main(String[] args) {
//		FileUrlConvertUtils.download("https://file.wikileaks.org/file/1000-us-marines-in-georgia-2008.zip","/work/test/2019/0416/wikileaks/files");
		
		downloadByListFile("/work/test/2019/0416/wikileaks/lists/filelist_1","/work/test/2019/0416/wikileaks/files");
	}
	
	public static void downloadByListFile(String listFile,String savePath) {
		FileUtil.scanFile(listFile,new FileProcesser() {
			public void process(String fileName, long lineNum, String line) {
				try {
					FileUrlConvertUtils.download(line, savePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(lineNum + "##" + line + " end");
			}
			public void end(String fileName, long totalNum) {
			}
			
		});
	}
}
