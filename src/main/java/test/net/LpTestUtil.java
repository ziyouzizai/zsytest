package test.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

/**
 * 类说明
 *
 * @author zhangsy
 * @date 2018年7月10日
 */
public class LpTestUtil {
	public static Node doGet(String url, Map<String,String> headerMap) {
		Node result = new Node(NodeType.EMPTY,null);
		byte[] content = null;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		if (headerMap != null) {
			// 头部请求信息
			if (headerMap != null) {
				Iterator<Entry<String,String>> iterator = headerMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String,String> entry = iterator.next();
					getMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
				}
			}
		}
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		InputStream inputStream = null;
		try {
			if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {
				String type = getMethod.getResponseHeader("Content-Type").getValue();
				if("application/vnd.android.package-archive".equals(type)) {
					return new Node(NodeType.MATCH_APK,null);
				}else {
					// 读取内容
					inputStream = getMethod.getResponseBodyAsStream();
					content = IOUtils.toByteArray(inputStream);
					result = new Node(NodeType.OK,new String(content,"utf-8"));
				}
			} else {
				System.err.println("Method failed: " + getMethod.getStatusLine());
				return new Node(NodeType.ERROR_OTHER,null);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			getMethod.releaseConnection();
		}
		return result;
	}
	
	public static Node doGet(String url) {
		return doGet(url,null);
	}
	
	public static int verify(String url) {
		try {
			List<Pattern> patternList = new ArrayList<Pattern>();
			patternList.add(Pattern.compile("setTimeout\\s*?\\(.*?\\{.*?location\\.href\\s*?=.*?,.+?\\)",Pattern.CASE_INSENSITIVE | Pattern.DOTALL));
			Node node = doGet(url);
			if(node.type == NodeType.OK) {
				for(Pattern p : patternList) {
					Matcher m = p.matcher(node.data);
					if(m.find()) {
						System.out.println(m.group());
 						return NodeType.MATCH_APK.getType();
					}
				}
				return NodeType.OK.getType();
			}
			return node.type.getType();
		}catch(Throwable t) {
			t.printStackTrace();
			return NodeType.ERROR_OTHER.getType();
		}
	}
	
	public static void main(String[] args) {
//		String url = "http://td01.iruifu.com.cn/tracking/promote/datang/fWvSTz4vmOTz.html";
//		String url = "http://huichuan.sm.cn/j?data=AKUAAFcFAAAjVwoN7sM5lHiKQsGu4K47Xlp0R7FFh4CHpdSXGm3q2LiSblId8LtgL6119CD-OhkmvAJqK4JhAF6AgEROkMwV6rO6Ldx3AcEn-QOP5KI4mKBluQB1NGRbiqV3KNIGpLLj0dFSDj4AmtarW9npT1aQTGqZ-2rvY8zHOTab8rLbO59KDV-oLHpQkKHfKE8ezPA96i7ygh0pJGC0HYQDtpnspoL7G137HuLRuzOcJm05F2crQkYp2HeljsC37FDeboEbtOcdS4Y5r2RMuwwikhzdRr5ovzr6KH1OIpp2Kx3fZy0sGQe6t2Cv_n9rwvF2KXXVt8NK1WH3Zl8pef-sBiar-lyIbESyaeT7znHlXesPh4HEC2hBptJHyba6xOvfu1eCxNY4RBxXu-x7pThyr_khYPcupFUrB2Gag0gYAySsfhLj7bqvyiDTXGhf4wtKpmkZb9D2l8XHCJjBkJBoeOV94iNDE0aDNh069eueURZk8LAHPCGXSMJ3DCum_Bwmi-FPLPJVrHTVTFbCGpd5UbCfYvvcZwnJSKLxWPorcdskip1FqqCM3EZqe0ocFngl8kwVvvu-mqfqoXD-VFeFNKO-jf5dDB7csqxNfCxZwVaOvjMcYXGYGgoSu_SJ0MZY5wqS8PW39cUarEDSD4a0XNC9tMQjBsHTO7SqoDKhp9ics1jdTlM5shGUjQDjjQzaUA3yXh3zj56CG9EP1SMuuAYxRpFxec6MnPWPw8u7Yfmt67SsmAgdUxN3Clq7HGd3mXmmHnsAfWCv_iAqkM9cJRaJakRTWh6SomoiHL_6GrSFSAo5e1RaqbWoCXrg1p4gpDxxek4rPnVa2PT8dqTtqI4w3CnaKXdspwiY0zLzVgI4wASbREs4UbUbTdCnNB8k5gbewFvck-reBoxkcV4u-hfA2mU0BKgNwij9TUdRDM45tmkpho8REwFBKISjYXbs8c7baS6javI-40QoUj8JrXIlK7rAejlhaK9QKvQzhkUS5kBA5eS-G92yw8gDAsCp-YLYwYZd98sGTEuEAXGpeKb9-RnFD8BuIRdeEgU_ESshleFv2kxSWlptdnCyVVMtE0ew_T2F8xc3rT4cg2JcPfuww7d-EoL6BaWztlxLJx_-_q9KRnezhMsbDPlYmpgRbCVNMdNMrr2ZGfBVUDoRPmwEZBB2rgiKLaz1ZA5KJbfslcvV-AZwCaxHTgqHs5XXqtCyx3BZsr_Lgdhz2p1B5FN9X4no6kUhVQvamRVVKMEZ6zTv3a3_Pd-hySpFTQa3FSJpYvVOBx0emKsZzrSQSrRZs9W8OXKNSNvHLSdpc89AkCF23EEUaRGNHoFEFGF35o9u7DqPuVS3Ks0M_zzUvvAvSy7ScC0HQ70uNpp_XCD-ILoM516Mpgmkwcpran6Z5Q3FbfF4YFROuhhYJDBss13cQuCM7HsoKHte925x8Ym0dKuoPBe0kLq_qgX_WvIwj6TF8sCidMmkR2BSs0WUywOysINtMQ_tJbeGw-yMeLjBX-CbWYfHkPkAKh8msdh3yehO_18pxl3ObOmI2bNtp19Jd3GL0BPfAtQr25gIPBvGVBvbkq8Ya42h_cUL8qqCapDybwDi5XywukAojY7Vz8Buz0I-VjjRyc7e3gjAXzOYzfTGJzGGdQCmti-nXc65YWaEdaynYBmZCfzv4N6od6nsLMLM1zx8BncPVjtW9goYl53gAiE4xxAl7jKZ_64bxZ3JbRQ8uVlTZTQzGUGKx3pkOLXOTjFnNTRFK7IPEWrz0uG7Z8xFlN4RgOgfqlUGTnfGO8_0FOt7kKV1vN8ea94b5v89keYyJw::&ad_pattern_string=h_3992978566989840929_32551522&ext=pos%5Fid%3D4&uc_biz_str=S%3Acustom%7CC%3Aiflow%5Fad%7CK%3Atrue";
//		String url = "https://dl.hz.37.com.cn/upload/1_1003035_10205/longyulingzhu_1003.apk";
		String url = "https://tg.37.com.cn/ldypage/index/?c=26245&p=1_1002894_10135&n=26245_1050";
		System.out.println(verify(url));
	}
}

class Node{
	public NodeType type;
	public String data;
	public Node(NodeType type,String data) {this.type = type;this.data = data;}
}

enum NodeType{
	EMPTY(0),
	OK(1),
	MATCH_APK(2),
	ERROR_URL_ERROR(3),
	ERROR_OTHER(4);
	private int type;
	private NodeType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
