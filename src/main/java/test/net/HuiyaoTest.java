package test.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuiyaoTest {

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = HuiyaoTest.class.getClassLoader();
		InputStream in = classLoader.getResourceAsStream("dat");
		System.out.println(in);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		Pattern pattern = Pattern.compile("\\{\"(.*?)\":(\\d)\\}");
		String urltemp = "http://tk.hygame.cc/aso/repeat?appid=1398&idfa=%s";
		
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		
		int count = 0;
		while((line = br.readLine()) != null) {
			count++;
			String result = HttpUtil.get(String.format(urltemp,line));
			Matcher m = pattern.matcher(result);
			if(m.find()) {
				String idfa = m.group(1);
				String flag = m.group(2);
				if("1".equals(flag) && idfa.equals(line)) {
					successList.add(line);
				}else if("0".equals(flag)) {
					errorList.add(line);
				}
			}
			if(count % 100 == 0) {
				System.out.println(count);
			}
		}
		System.out.println("========================success:");
		for(String item : successList) {
			System.out.println(item);
		}
		System.out.println();
		System.out.println();
		System.out.println("========================error:");
		for(String item : errorList) {
			System.out.println(item);
		}
		System.out.println();
		System.out.println(String.format("成功%d个，失败%d个",successList.size(),errorList.size()));
	}

}
