package test.net;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Test1 {

	public static void main(String[] args) throws IOException {
		//设置代理IP、端口、协议（请分别替换）
        HttpHost proxy = new HttpHost("",8080);

        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        //实例化CloseableHttpClient对象
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

        //访问目标地址
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        //请求返回
        CloseableHttpResponse httpResp = httpclient.execute(httpGet);
        try {
            int statusCode = httpResp.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("成功");
            }
        } catch (Exception e) {

        } finally {
            httpResp.close();
        }
	}

}
