package test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientUtil {
    public static String getContent(String url, Map<String, String> heads, String charset, String method) throws Exception {
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection(); ;
        if (heads != null) {
            for (Map.Entry entry : heads.entrySet()) {
                httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
        }
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.connect();

        String result = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), charset));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
//    	process();
    	sure();
    }
    
    public static void sure() throws IOException{
    	File file = new File("/work/test/0925/tt1");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line = null;
    	while((line = br.readLine()) != null){
    		String[] arr = line.split("##");
    		System.out.println(arr[0] + "---" + arr[1] + "---" + virefy(arr[1],arr[3]));
    	}
    }
    
    public static void process() throws IOException{
    	File file = new File("/work/test/0925/r1");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line = null;
    	while((line = br.readLine()) != null){
    		String[] arr = line.split("\t");
    		String id = arr[0].trim();
    		String account = arr[1].trim();
    		String refreshtoken = arr[2].trim();
    		String token = arr[3].trim();
    		if(!check(account,token)){
    			System.out.println(id + "##" + account + "##" + refreshToken(refreshtoken));
    		}
    	}
    	
    	br.close();
    }
    
    
    public static String refreshToken(String refreshtoken){
    	String refreshurlTemp = "https://api.e.qq.com/oauth/token?client_id=1106237187&client_secret=BvITHJpxu0gufAxF&grant_type=refresh_token&refresh_token=%s&timestamp=%d&nonce=%d";
        String ret = null;
        Long time = System.currentTimeMillis() / 1000;
        String refreshurl = String.format(refreshurlTemp,refreshtoken,time,time);
        String rt = "";
        String at = "";
		try {
			ret = HttpClientUtil.getContent(refreshurl, null, "utf-8", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//{"code":0,"message":"","data":{"refresh_token":"928d30487ec103b56cd7f7c66b4ee694","access_token":"4f8311d6adb033b449d24bd436b47b2b","access_token_expires_in":86400}}
		Pattern p1 = Pattern.compile("^.*?\"access_token\":\"(.*?)\".*?$");
		Pattern p2 = Pattern.compile("^.*?\"refresh_token\":\"(.*?)\".*?$");
		
		Matcher m1 = p1.matcher(ret);
		Matcher m2 = p2.matcher(ret);
		if(m1.find()){
			at = m1.group(1);
		}
		if(m2.find()) {
			rt = m2.group(1);
		}
		return rt + "##" + at;
    }
    

    public static boolean check(String account,String accesstoken){
    	String urlTemp = "https://api.e.qq.com/v1.1/adgroups/get?access_token=%s&timestamp=%d&nonce=%d&account_id=%s&page=1&page_size=10";
    	Long time = System.currentTimeMillis() / 1000;
    	String url = String.format(urlTemp,accesstoken,time,time,account);
    	String ret = null;
    	try {
			ret = HttpClientUtil.getContent(url, null, "utf-8", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ret.startsWith("{\"data\":{\"list\":[");
    }
    
    public static String virefy(String account,String accesstoken){
    	String urlTemp = "https://api.e.qq.com/v1.1/adgroups/get?access_token=%s&timestamp=%d&nonce=%d&account_id=%s&page=1&page_size=10";
    	Long time = System.currentTimeMillis() / 1000;
    	String url = String.format(urlTemp,accesstoken,time,time,account);
    	String ret = null;
    	try {
			ret = HttpClientUtil.getContent(url, null, "utf-8", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ret;
    }
    
}