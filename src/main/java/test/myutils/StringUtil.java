package test.myutils;

import java.util.Arrays;
import java.util.List;

public class StringUtil {
	public final static String EMPTY_STRING = "";
	
	public static String join(List<String> list,String separator) {
		if(list == null || list.size() < 1)
			return null;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size() -1;i++) {
			sb.append(list.get(i)).append(separator);
		}
		sb.append(list.get(list.size()-1));
		return sb.toString();
	}
	
	public static String join(String[] array,String separator) {
		if(array == null || array.length < 1)
			return null;
		List<String> list = Arrays.asList(array);
		return join(list,separator);
	}
}
