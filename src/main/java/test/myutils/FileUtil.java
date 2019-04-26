package test.myutils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileUtil {
	
	public static void scanFile(String filePathName,FileProcesser processer) {
		File file = new File(filePathName);
		if(!file.exists() || !file.isFile()) {
			return;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			processer.init(filePathName);
			long lineNum = 0;
			String line = null;
			while((line = br.readLine()) != null) {
				processer.process(filePathName, ++lineNum, line);
			}
			processer.end(filePathName, lineNum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean convertEncodingByDir(String src,String dist,String srcCharset,String distCharset) throws IOException {
		File srcDir = new File(src);
		File distDir = new File(dist);
		if(!srcDir.exists() || !srcDir.isDirectory())
			return false;
		if(!distDir.exists()) {
			distDir.mkdirs();
		}
		String srcPrefix = srcDir.getAbsolutePath();
		File[] files = srcDir.listFiles();
		for(File file : files) {
			if(file.isDirectory()) {
				String d = file.getAbsolutePath();
				String nd = d.replace(srcPrefix,dist);
				convertEncodingByDir(d,nd,srcCharset,distCharset);
			}else if(file.isFile()) {
				String n = file.getAbsolutePath();
				String nn = n.replace(srcPrefix,dist);
				convertEncoding(file,new File(nn),srcCharset,distCharset);
				System.out.println(nn);
 			}
		}
		return true;
	}
	
	public static boolean convertEncoding(File src,File dist,String srcCharset,String distCharset) throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(src),srcCharset);
		BufferedReader br = null;
		OutputStream fout = null;
		try {
			br = new BufferedReader(isr);
			fout = new FileOutputStream(dist);
			String line = null;
			while((line = br.readLine()) != null) {
				fout.write(line.getBytes(distCharset));
				fout.write('\n');
			}
		}finally {
			if(br != null)
				br.close();
			if(fout != null)
				fout.close();
		}
		return true;
	}
	
	/**
	 * 文件内转转成字符串(回车自动去掉)
	 * @param srcFile
	 * @return
	 */
	public static String file2Str(String srcFile) {
		StringBuffer sb = new StringBuffer();
		FileUtil.scanFile(srcFile,new FileProcesser() {
			@Override
			public void process(String fileName, long lineNum, String line) {
				sb.append(line);
			}
		});
		return sb.toString();
	}
	
	/**
	 * 文件内转转成字符串(保留回车)
	 * @param srcFile
	 * @return
	 */
	public static String file2StrWithln(String srcFile) {
		StringBuffer sb = new StringBuffer();
		FileUtil.scanFile(srcFile,new FileProcesser() {
			@Override
			public void process(String fileName, long lineNum, String line) {
				sb.append(line).append("\n");
			}
		});
		return sb.toString();
	}
	
	public static void str2File(String src,String distFile) {
		FWriter fw = new FWriter(distFile);
		fw.writeLine(src);
		fw.close();
	}
	
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes("utf-8"));
		gzip.close();
		return out.toString("ISO-8859-1");
	}
	
	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
		return out.toString("utf-8");
	}
	
	/**
	 * 文件求交集
	 * 
	 * 默认每一行一个元素
	 * @return
	 */
	public static void getFileIntersection(String f1,String f2,String dist) {
		String s1 = file2StrWithln(f1);
		String s2 = file2StrWithln(f2);
		List<String> list1 = Arrays.asList(s1.split("\n"));
		List<String> list2 = Arrays.asList(s2.split("\n"));
		List<String> intersection =  CollectionUtil.getIntersection(list1,list2);
		String s = StringUtil.join(intersection,"\n");
		str2File(s, dist);
	}
	
	public static void main(String[] args) {
		/*
		FileUtil.scanFile("/tmp/log",new FileProcesser() {
			private Map<String,Integer> map = new HashMap<>();
			@Override
			public void process(String fileName, long lineNum, String line) {
				String key = line;
				if(map.get(key) == null) {
					map.put(key,0);
				}
				int value = map.get(key).intValue() + 1;
				map.put(key,value);
			}
			@Override
			public void end(String fileName, long totalNum) {
				System.out.println(map);
			}
		});
		*/
		
		
		try {
//			FileUtil.convertEncoding("/work/ws/merge_temp/cmiic4/src/km/com/beatles/km/common/DateConvert.java",
//					"/work/ws/merge_temp/cmiic4/src/km/com/beatles/km/common/DateConvert_2.java", "gbk", "utf-8");
			
			FileUtil.convertEncodingByDir("/work/ws/merge_temp/cmiic4/test","/work/ws/merge_temp/cmiic4_2/test","gbk","utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
