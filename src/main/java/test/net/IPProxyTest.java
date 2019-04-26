package test.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPProxyTest {
	public static void main(String[] args) throws Exception {
		Pattern pattern = Pattern.compile("\"cip\":\\s*\"(.*?)\"");
		String result = HttpUtil.get("http://pv.sohu.com/cityjson");
		Matcher m = pattern.matcher(result);
		if(m.find()) {
			System.out.println(m.group(1));
		}
		
		for(int i=3;i<13;i++) {
			String host = "http-proxy-t1.dobel.cn";
			int port = 9180;
			String user = "RYNETHTT" + i;
			String password = "gL0I092UHjf";
			
			URL url = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host,port)); 
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
	        
	        String headerKey = "Proxy-Authorization";
	        String up = user + ":" + password;
	        String headerValue = "Basic " + Base64.getEncoder().encodeToString(up.getBytes("utf-8")); 
	        
	        
	        conn.setRequestProperty(headerKey, headerValue); 
	        
	        InputStream in = conn.getInputStream();
	        BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
	        String valueString = null;
	        StringBuffer bufferRes = new StringBuffer();
	        while ((valueString = read.readLine()) != null) {
	            bufferRes.append(valueString);
	        }
	        
	        System.out.println(bufferRes.toString());
		}
		
	}
}
