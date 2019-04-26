package test.others;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindWord {
	
	private static Map<String,String> MAP = new HashMap<String,String>();
	
	private static StringBuffer sb = new StringBuffer();
	
	public static void main(String[] args) {
//		processReport();
//		replaceReport();
		searchUntran();
	}
	
	public static void searchUntran() {
		File inputPath = new File("/work/ws/git/report/src/main/resources/tpl/trackingio/");
		File[] files = inputPath.listFiles();
		String regex = "<en>(.*?[\\u4e00-\\u9fa5].*?)</en>";
		Pattern pattern = Pattern.compile(regex);
		int count = 1;
		for(File file : files) {
			if(file.isFile() && file.getName().endsWith(".xml")) {
				String content = file2Str(file);
				Matcher m = pattern.matcher(content);
				while(m.find()) {
					System.out.println(m.group(1));
				}
			}
		}
	}
	
	public static void replaceReport() {
		//step1:加载映射文件
		File relFile = new File("/work/test/1105/c4.txt");
		Map<String,String> relMap = new HashMap<String,String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(relFile));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				relMap.put(arr[0].trim(),arr[1].trim());
			}
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
		
		//step2:遍历模板文件，进行替换和输出
//		<column name="number">
//			<zh>推广活动总数</zh>
//		</column>
		Pattern pattern = Pattern.compile("(<\\s*?column\\s+name=\"[a-zA-Z0-9_]+?\"\\s*?>\\s*<zh>(.*?)</zh>)(\\s*</column>)",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		
		File inputPath = new File("/work/ws/git/report/src/main/resources/tpl/trackingio_origin/");
		File outputPath = new File("/work/ws/git/report/src/main/resources/tpl/trackingio/");
		
		File[] files = inputPath.listFiles();
		int count = 1;
		for(File file : files) {
			if(file.isFile() && file.getName().endsWith(".xml")) {
				System.out.println(file.getName() + " start!");
				
				File newFile = new File(outputPath,file.getName());
				String content = file2Str(file);
				//对特殊字符进行处理
//				content = java.util.regex.Matcher.quoteReplacement(content);
				content = content.replaceAll("\\$","#ZHANGSY#");
//				System.out.println(content);
				
				Matcher m = pattern.matcher(content);
				StringBuffer sb = new StringBuffer();
				while(m.find()) {
					String key = m.group(2);
					String replacement;
					if(relMap.get(key) != null) {
						replacement = relMap.get(key);
					}else {
						replacement = key;
					}
					m.appendReplacement(sb,"$1\n\t\t\t<en>" + replacement + "</en>$3");
				}
				m.appendTail(sb);
				
				String result = sb.toString().replace("#ZHANGSY#","$");
				str2File(result,newFile);
				
				System.out.println((count++) + ":" + file.getName() + " done!");
			}
		}
		
	}
	
	public static void processReport() {
		sb = new StringBuffer();
		
		File dir = new File("/work/ws/git/report/src/main/resources/tpl/trackingio/");
		File[] files = dir.listFiles();
		for(File file : files) {
			if(file.exists() && file.isFile()) {
				findWordFromReportTemplate(file.getAbsolutePath());
			}
		}
		File outFile = new File("/work/test/findword/reportout.csv");
		str2File(sb.toString(),outFile);
	}

	public static String findWordFromReportTemplate(String filePathName) {
		File file = new File(filePathName);
		String content = file2Str(file);
		Pattern pattern = Pattern.compile("<columns>(.*?)</columns>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Pattern p1 = Pattern.compile("<column.*?>.*?<zh>(.*?)</zh>.*?</column>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		String templateName = filePathName.replaceAll(".*?\\/([^\\/]*?)\\.xml","$1");
		Matcher m = pattern.matcher(content);
		if(m.find()) {
			Matcher m1 = p1.matcher(m.group(1));
			while(m1.find()) {
				String key = m1.group(1);
				sb.append(templateName + "##" + key + "##" + (MAP.get(key) == null ? "否" : "是")).append("\n");
				MAP.put(key,"");
			}
		}
		
		return null;
	}
	
	public static void processFront() {
		String result = searchFromDir("/work/ws/git/trackingio/src/main/websrc/template/");
		
		String outPath = "/work/test/findword/out.txt";
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(outPath)));
			bw.write(result);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String searchFromDir(String dir) {
		StringBuffer sb = new StringBuffer();
		File d = new File(dir);
		if(d.exists() && d.isDirectory()) {
			File[] files = d.listFiles();
			List<File> subdirs = new ArrayList<File>();
			for(File file : files) {
				if(file.exists() && file.isFile()&&file.getName().endsWith(".html")) {
					sb.append("==================================\n");
					sb.append("filename=" + file.getAbsolutePath() + "\n");
					sb.append("==================================\n");
					sb.append(findWordFromFile(file));
					sb.append("\n\n\n");
				}else if(file.isDirectory()) {
					String dr = searchFromDir(file.getAbsolutePath());
					sb.append(dr);
				}
			}
		}
		return sb.toString();
	}
	
	public static String findWordFromFile(File file) {
		StringBuffer sb = new StringBuffer();
		String regex = ">\\s*([^<]*?[\\u4e00-\\u9fa5]+[^<]*?)\\s*<";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		
		Pattern p1 = Pattern.compile("\\{\\{.*?\\}\\}");
		Pattern p11 = Pattern.compile("[\\u4e00-\\u9fa5]+");
		String content = file2Str(file);
		Matcher m = pattern.matcher(content);
		while(m.find()) {
			String target = m.group(1);
			//处理类似
			//{{info.platform==systemList[0].id ? systemList[0].name:(info.platform==systemList[1].id ?systemList[1].name:'未知系统')}}
			//这种
			Matcher m1 = p1.matcher(target);
			if(m1.find()) {
				String t1 = m1.group();
				Matcher m11 = p11.matcher(t1);
				String item = "";
				while(m11.find()) {
					item += m11.group() + "\n";
				}
				if(item != "") {
					target = target.replace(t1,"\n" + item);
				}else {
					target = target.replace(t1,"");
				}
				
				sb.append(target);
			}else {
				sb.append(m.group(1).replaceAll("\n","")).append("\n");
			}
		}
		return sb.toString();
	}
	
	public static void str2File(String content,File file) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			fout.write(content.getBytes("utf-8"));
			fout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String file2Str(File file) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
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
		return sb.toString();
	}
}
