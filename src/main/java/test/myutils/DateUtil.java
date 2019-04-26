package test.myutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static long getDvalue(String date1,String date2) {
		try {
			Date d1 = dateSdf.parse(date1);
			Date d2 = dateSdf.parse(date2);
			long quot = d1.getTime() - d2.getTime();
			return quot/(1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
