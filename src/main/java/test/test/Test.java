package test.test;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.collect.Maps;
import com.reyun.dmp.entity.CampInfo;
import com.reyun.dmp.entity.FecInfo;

import gui.ava.html.image.generator.HtmlImageGenerator;
import sun.misc.BASE64Encoder;
import test.myutils.CollectionUtil;
import test.myutils.FWriter;
import test.myutils.FileProcesser;
import test.myutils.FileUtil;
import test.net.HttpUtil;
import test.others.Cat;

/**
 * Hello world!
 *
 */
public class Test {
	
	
	public static void main(String[] args) throws Exception {
		test70();
	}
	
	public static void test72() throws Exception {
		//curl -X POST 'http://idfa.trackingio.com/tkioIdfa' -d 'appid=1329345451&idfa=8D3F0E56-3ED2-4A1B-AB4B-9E86E234405B'
		//h("http://idfa.trackingio.com/tkioIdfa","");
		String resp = "{\"code\":0,\"data\":\"fa253f5de6354bdd7f7318ec765e7840a6c1548853a27ebfa2e07c597c1b462a34c7b1323b21c381a765a8a4dd5a6f4fcbe5143db72bdf976c19922790df72a007d4e3593610dbe522bc85c5dbdc2716d6d706185e0a8c33be07d8ea2092409b344cf5f311ae188159140ae992a65b9f538299ae4c7ed68308f8aa6a92566d2e8d09c5cf492f76ea0a5041bf4680807e846e2cabffcc31c0348439388f76c3ef930c08d9205df3d350806e7816a0b7cc40b7b4508efe2c972e4019fb86e4d98e1f4f574279fe6b227119798d5c205300fd80d24c9455786a\",\"request_id\":\"abf06a040a6d7d81\"}";
//		net.sf.json.JSONObject result = net.sf.json.JSONObject.fromObject(resp);
//		net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(result.get("data"));
//		System.out.println(data);
		String data = "fa253f5de6354bdd7f7318ec765e7840a6c1548853a27ebfa2e07c597c1b462a34c7b1323b21c381a765a8a4dd5a6f4fcbe5143db72bdf976c19922790df72a007d4e3593610dbe522bc85c5dbdc2716d6d706185e0a8c33be07d8ea2092409b344cf5f311ae188159140ae992a65b9f538299ae4c7ed68308f8aa6a92566d2e8d09c5cf492f76ea0a5041bf4680807e846e2cabffcc31c0348439388f76c3ef930c08d9205df3d350806e7816a0b7cc40b7b4508efe2c972e4019fb86e4d98e1f4f574279fe6b227119798d5c205300fd80d24c9455786a";
		System.out.println(parse(data));
	}
	
	public static String parse(String data) throws Exception {
		String strKey = "tourhb";
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Cipher cipher = Cipher.getInstance("DES");
		Key key = d(strKey.getBytes());
		cipher.init(2, key);
		return new String(cipher.doFinal(j(data)));
	}
	
	public static void test71() throws JsonProcessingException {
		FecInfo fecinfo = new FecInfo();
		fecinfo.setMedia_id("fec-test");
		fecinfo.setFec_id("90");
		fecinfo.setFec_ver("0.1.2@83c14b4f682b39e0");
		//fecinfo.setFec_url("");//TODO 
		ObjectMapper om = new ObjectMapper();
		String s = om.writeValueAsString(fecinfo);
		System.out.println(s);
	}
	
	public static String h(String apiUrl, String json) {
		RequestConfig.Builder configBuilder = RequestConfig.custom();
	    configBuilder.setConnectTimeout(70000);
	    configBuilder.setSocketTimeout(70000);
	    configBuilder.setConnectionRequestTimeout(70000);
	    configBuilder.setStaleConnectionCheckEnabled(true);
	    RequestConfig bb = configBuilder.build();
	    
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(bb);
			StringEntity stringEntity = new StringEntity(json, "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return httpStr;
	}
	
	/**
	 * 反序列化campinfo
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static void test70() throws IllegalBlockSizeException, BadPaddingException, Exception {
		String strKey = "tourhb";
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String data = "3967ef6cf4ada9e207a3b939e2cc602d59d27dc4ac1923a234d61ce3bba6e7d8a991073da3c37b6eadb5c9f8dc4521753985d7562ff040a38141008a2a7d7b4ef4e2d2aea7388e7d2b7532e3a09dd2083fd228c5e7909d3a43ee36347e9f7b46fe96aecf078f081e24f3bd2f3549ae989b565d751f525790e1fa83491aa77da62c931650a210827f41070023d674a9ba6406fade822d250aff04626a5c7ae8122f96a8e8c017d4de0d4b9d5411ed803dbbd53219ebec01a7ef5526979e39d6e388606df33e75f9456bc776d24914bb329f6eb723ecc69c0f863b3e1391a1a36077b5b7e8af1d5d96";
		Cipher cipher = Cipher.getInstance("DES");
		Key key = d(strKey.getBytes());
		cipher.init(2, key);
		String s = new String(cipher.doFinal(j(data)));
		ObjectMapper om = new ObjectMapper();
		CampInfo info = om.readValue(s,CampInfo.class);
		System.out.println(JSON.toJSONString(info));
		
		
	}
	
	private static Key d(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];
		for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++)
			arrB[i] = arrBTmp[i];
		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}
	
	public static byte[] j(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		int i = 0;
		while (i < iLen) {
			String strTmp = new String(arrB, i, 2);
			arrOut[(i / 2)] = (byte) Integer.parseInt(strTmp, 16);
			i += 2;
		}
		return arrOut;
	}

	
	public static void test69() throws IOException {
		String str =  FileUtil.file2Str("/work/test/2019/0418/bf_result.dat");
		String zipStr = FileUtil.compress(str);
		FileUtil.str2File(zipStr,"/work/test/2019/0418/bf_result_zip.dat");
		String unzipStr = FileUtil.uncompress(zipStr);
		System.out.println(str.equals(unzipStr));
		FileUtil.str2File(unzipStr,"/work/test/2019/0418/bf_result_zip_unzip.dat");
	}
	
	public static void test68() throws UnsupportedEncodingException {
		String str = "测试";
		String result = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
		System.out.println(result);
		
		byte[] bs = Base64.getDecoder().decode(result);
		String s = new String(bs,"utf-8");
		System.out.println(s);
	}
	
	public static void test67() throws Exception {
//		String url = "https://file.wikileaks.org/file/1000-us-marines-in-georgia-2008.zip";
//		HttpUtil.getWithJudge(url);
//		
//		HttpUtil.getWithJudge("https://file.wikileaks.org/file/aryan-nation-2009");
		
		File file = new File("/work/test/2019/0416/wikileaks/fs/test.dat");
		System.out.println(file.getPath());
		System.out.println(file.getParent());
	}
	
	public static void test66() {
		System.out.println(createEncryptPSW("123456"));
	}
	
	public static void test65() {
		FileUtil.scanFile("/work/test/2019/0409/feicui_0411",new FileProcesser() {
			private FWriter fw = null;
			private Pattern pattern = Pattern.compile(":([01])}");
			private long t = 0;
			private long f = 0;
			
			@Override
			public void init(String fileName) {
				fw = new FWriter("/work/test/2019/0409/feicui_0411_result.csv");
				fw.init();
			}

			@Override
			public void process(String fileName, long lineNum, String line) {
				if(lineNum < Long.MAX_VALUE) {
					//curl -X POST 'http://idfa.trackingio.com/tkioIdfa' -d 'appid=1450586372&idfa=427D1CC4-2A1F-42C6-A919-2944DF522624'
					String url = "http://idfa.trackingio.com/tkioIdfa";
					String params = String.format("appid=1380567884&idfa=%s",line);
					try {
						String result = HttpUtil.post(url, params);
						Matcher m = pattern.matcher(result);
						if(m.find()) {
//							System.out.println(line + "," + m.group(1));
							int flag = Integer.parseInt(m.group(1));
							t += (flag == 1 ? 1 : 0);
							f += (flag == 1 ? 0 : 1);
							fw.writeLine(line + "," + m.group(1));
						}else {
							fw.writeLine(line + ",-1");
							f += 1;
						}
						if(lineNum % 100 == 0) {
							System.out.println("===" + lineNum);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}

			@Override
			public void end(String fileName, long totalNum) {
				System.out.println("成功：" + t);
				System.out.println("失败：" + f);
			}
		});
	}
	
	private static String createEncryptPSW(String psw) {
		MessageDigest messagedigest;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(psw.getBytes("UTF8"));
			byte abyte0[] = messagedigest.digest();
			return (new BASE64Encoder()).encode(abyte0);
		} catch (Exception e) {
//			throw new EncryptException("加密出现错误" + e.getMessage());
		}
		return "";
	}
	
	public static void test64() {
		File file = new File("/tmp/test");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(file.getAbsolutePath());
	}
	
	public static void test63() {
		File dir = new File("/work/ws/merge_temp/cmiic4");
		processDir(dir);
	}
	
	private static void processDir(File dir) {
		File[] files = dir.listFiles();
		for(File file : files) {
			if(file.isDirectory()) {
				if(file.getName().equals(".svn")) {
					boolean flag = file.delete();
					System.out.println(file.getAbsolutePath() + "\t\t\t" + flag);
				}else {
					processDir(file);
				}
			}
		}
	}
	
	
	public static void test62() {
		String idfa = UUID.randomUUID().toString().toUpperCase();
		System.out.println(idfa);
	}
	
	public static void genUrl(String idfa) {
		String idfv = UUID.randomUUID().toString().toUpperCase();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = sdf.format(d);
		long ts = d.getTime();
		
		String checkUrl = String.format("curl -X POST 'http://idfa.trackingio.com/tkioIdfa' -d 'appid=1380567884&idfa=%s'",idfa);
		
		String install = String.format("curl -X POST 'https://log.reyun.com/receive/tkio/install' -H 'Content-Type: application/json;charset=UTF-8' -d '{\"appid\":\"6064e1008ce01581a8e25a14dad05d7a\",\"when\":\"%s\",\"what\":\"install\",\"context\":{\"modify\":\"0\",\"_carrier\":\"中国电信\",\"_lats\":\"1\",\"iad-attribution\":\"false\",\"_idfa\":\"%s\",\"_jbk\":\"1\",\"_lib_version\":\"1.3.0\",\"_app_version\":\"1.0.003\",\"_manufacturer\":\"苹果\",\"_timestamp\":\"%d\",\"_create_timestamp\":\"%d\",\"_rydevicetype\":\"iPhone\",\"_deviceid\":\"%s\",\"_sads\":\"1\",\"_tz\":\"+8\",\"_resolution\":\"667*375\",\"_pkgname\":\"com.tc.mhld\",\"_istf\":\"0\",\"_network_type\":\"WIFI\",\"_mac\":\"02:00:00:00:00:00\",\"_idfv\":\"%s\",\"_device_gps\":\"unknown\",\"frequency\":\"2.333\",\"_ryosversion\":\"11.0.3\",\"_campaignid\":\"test\",\"_model\":\"iPhone 6 (A1549\\/A1586)\",\"_ryos\":\"ios\"},\"who\":\"unknown\"}'",date,idfa,ts,ts,idfa,idfv);

		System.out.println(idfa);
		System.out.println();
		System.out.println(checkUrl);
		System.out.println();
		System.out.println(install);
	}
	
	
	
	public static void test61() {
		String target = "a,b,c,e";
		String[] arr = target.split(",");
		List<String> list = Arrays.asList(arr);
		list.add("f");
		System.out.println(list);
	}
	
	public static void test60() throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Map<String,Object> map = new HashMap<>();
		map.put("age",Integer.MAX_VALUE + "");
		map.put("name","lu");
		map.put("color_num","blue");
		map.put("leg_num_size","5");
		System.out.println(convertMap(map));
		Cat cat = mapToObject(map,Cat.class);
		System.out.println(cat.toString());
	}
	
	public static <T> T mapToObject(Map<String,? extends Object> map,Class<T> clz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		if(map == null)
			return null;
		T bean = clz.newInstance();
		BeanUtils.populate(bean,map);
		return bean;
	}
	
	public static Map<String, ? extends Object> convertMap(Map<String, ? extends Object> map) {
		if (map == null)
			return null;
		Map<String, Object> result = new HashMap<>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String newKey = key;
			Matcher m = null;
			if (key != null && key.contains("_")) {
				m = Pattern.compile("_([a-zA-Z])").matcher(key);
				while (m.find()) {
					String o = m.group();
					String n = m.group(1).toUpperCase();
					newKey = newKey.replace(o, n);
				}
				result.put(newKey, map.get(key));
			} else {
				result.put(key, map.get(key));
			}
		}
		return result;
	}
	
	public static void test59() {
		FileUtil.scanFile("/work/dbs/result0322_2.csv",new FileProcesser() {
			private Map<String,FWriter> map = new HashMap<>();
			@Override
			public void process(String fileName, long lineNum, String line) {
				String key = line;
				String[] arr = line.split(",");
				String intname = arr[0];
				if(map.get(intname) == null) {
					FWriter fw = new FWriter("/work/test/2019/0322/idfa_" + intname + ".csv");
					fw.init();
					map.put(intname, fw);
				}
				map.get(intname).writeLine(line);
			}
			@Override
			public void end(String fileName, long totalNum) {
				List<String> list = CollectionUtil.convertSetToList(map.keySet());
				for(String item : list) {
					map.get(item).close();
				}
			}
		});
	}
	
	public static void test58() {
		FWriter fw = new FWriter("/work/test/2019/0322/force.dat");
		fw.init();
		for(int i=0;i<10000;i++) {
			fw.writeLine(UUID.randomUUID().toString().toUpperCase());
		}
		fw.close();
	}
	
	public static void test57() {
		List<String> target = new ArrayList<>();
		target.add("ee");
		target.add("hh");
		target.add("rewrewr");
		
		List<String> list1 = new ArrayList<>();
		list1.add("aa");
		list1.add("bb");
		list1.add("cc");
		list1.add("dd");
		list1.add("ee");
		list1.add("ff");
		
		System.out.println(CollectionUtil.getIntersection(target,list1));
		
		System.out.println(target);
		
		System.out.println(list1);
	}
	
	public static void test56() {
		System.out.println(UUID.randomUUID().toString().toUpperCase());
	}
	
	public static List<Integer> gen(int size){
		List<Integer> result = new ArrayList<>();
		Random rand = new SecureRandom();
		int start = rand.nextInt(size);
		int step = rand.nextInt(size/2);
		return null;
	}

	public static void test55() {
		String key = UUID.randomUUID().toString().replaceAll("-","");
		System.out.println(key);
		System.out.println(key.length());
		
		String s = "bf2c4a5b866543d7a526ccf8a2b6b066";
		System.out.println(s.length());
	}
	
	public static void test54() throws IOException {
		File file = new File("/work/test/0109/bl_deviceid_rs.txt");
		File out = new File("/work/test/0109/bl_deviceid_rs_out.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));
		String line = null;
		while((line = br.readLine()) != null) {
			int ind = line.indexOf(1);
			line = ind > -1 ? line.substring(0,ind) : line;
			if(line.length() > 30) {
				bw.write(line);
				bw.newLine();
				bw.flush();
			}
		}
		br.close();
		bw.close();
	}
	
	public static void test53() {
		int a = -1000;
		int b = 1000;
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(a >> 2));
		System.out.println(Integer.toBinaryString(a >>> 2));
		System.out.println(Integer.toBinaryString(0));
		System.out.println(Integer.toBinaryString(-1));
		System.out.println(Integer.toBinaryString(-2));
		System.out.println(Integer.toBinaryString(-3));
		
		System.out.println(a >> 2);
		System.out.println(a >>> 2 );
	}
	
	public static void test52() {
		List<String> list1 = new ArrayList<String>();  
	    list1.add("A");  
	    list1.add("B");  
	  
	    List<String> list2 = new ArrayList<String>();  
	    list2.add("C");  
	    list2.add("B");  
	  
	    // 2个集合的交集  
	    list1.retainAll(list2);  
	    System.out.println("交集:" + list1);  
	}
	
	public static void test51() {
		String ss = "\"\\\"1994\\\",\\\"OPPO R11s\\\",\\\"218.98.32.136\\\",\\\"unknown\\\",\\\"null\\\",\\\"2018-12-11 21:39:19\\\",\\\"OPPO R11s\\\",\\\"7.1.1\\\",\\\"com.huanshou.taojj\\\"\"";
		System.out.println(ss);		
		ss = ss.replaceAll("^\"\\\\\"","").replaceAll("\\\\\"\"$","");
		System.out.println(ss);
		String[] arr = ss.split("\\\\\",\\\\\"");
		System.out.println(Arrays.toString(arr));
		System.out.println(arr.length);
	}
	
	public static void test50() {
		long cur = System.currentTimeMillis();
		for(int i=6;i<30;i++) {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(cur - i * 24 * 60 * 60 * 1000L)));
		}
	}
	
	public static void test49() {
		System.out.println(new Date().getTime());
		System.out.println(System.currentTimeMillis());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)));
		
		System.out.println(Long.MAX_VALUE);
	}
	
	public static void test48() {
//		SecureRandom secureRandom = new SecureRandom();
//		System.out.println(secureRandom.nextInt(100));
		System.out.println(System.getProperty("java.security"));
		
		SecureRandom rand = new SecureRandom();
		System.out.println(rand.getAlgorithm());
		for (int k = 0; k < 20; k++) {
			int small = 0;
			int large = 0;
			rand = new SecureRandom();
			for (int i = 0; i < 1000000; i++) {
				// rand = new Random(System.currentTimeMillis());
				int item = rand.nextInt(1000000);
				if (item > 850000)
					large++;
				else
					small++;
			}
			System.out.println(String.format("k=%d -- small=%d,large=%d", k, small, large));
		}
	}
	
	private static void saveRandom(Random rand) {
		try {
			ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(new File("/work/test/1121/object")));
			ois.writeObject(rand);
			ois.flush();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Random readRandom() {
		Random random = null;
		try {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(new File("/work/test/1121/object")));
			random = (Random) (oin.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return random;
	}

	public static void test47() {
		long seed = 12345L;
		Random random = new Random(seed);
		saveRandom(random);

		for (int k = 0; k < 10; k++) {
			Random rand = readRandom();
			for (int i = 0; i < 10; i++) {
				System.out.print(rand.nextInt(10) + " ");
			}
			System.out.println();
			saveRandom(random);
		}
	}

	public static void test46() {
		Long rank = System.currentTimeMillis();
		Random rand = new Random(rank);
		for (int k = 0; k < 20; k++) {
			int small = 0;
			int large = 0;
			rand = new Random(rank);
			for (int i = 0; i < 1000000; i++) {
				// rand = new Random(System.currentTimeMillis());
				int item = rand.nextInt(1000000);
				if (item > 850000)
					large++;
				else
					small++;
			}
			System.out.println(String.format("k=%d -- small=%d,large=%d", k, small, large));
		}
	}

	public static void test45() {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();

	}

	public static void test44() {
		System.out.println(Long.MAX_VALUE);

		int oldCapacity = 15;
		for (int i = 0; i < 20; i++) {
			oldCapacity = oldCapacity + ((oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1));
			System.out.println(oldCapacity);
		}

	}

	public static void test43() {
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
				+ "<plist version=\"1.0\">\n" + "<dict>\n" + "  <key>CHALLENGE</key>\n"
				+ "  <string>optional challenge</string>\n" + "  <key>IMEI</key>\n"
				+ "  <string>35 698906 479619 0</string>\n" + "  <key>PRODUCT</key>\n"
				+ "  <string>iPhone7,2</string>\n" + "  <key>UDID</key>\n"
				+ "  <string>b6843202e3e62afe921477917d47582d7a675193</string>\n" + "  <key>VERSION</key>\n"
				+ "  <string>14B100</string>\n" + "</dict>\n" + "</plist>";
		String regex = ".*?<!DOCTYPE plist PUBLIC \\\"-//Apple//DTD PLIST 1.0//EN\\\" \\\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\\\">\\n<plist\\s+version=\\\".*?\\\">\\n<dict>(.*?)</dict>\n</plist>";
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

		String reg2 = "<key>(.*?)</key>.*?<(\\w+)>(.*?)</\\2>";
		Pattern pat2 = Pattern.compile(reg2, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(s);
		if (m.find()) {
			System.out.println(m.group(1));
			String target = m.group(1);

			Matcher m2 = pat2.matcher(target);
			while (m2.find()) {
				System.out.println(m2.group(1) + "||" + m2.group(3));
			}
		}
	}

	public static void test42() {
		String pastDate = "";
		String today = "";

	}

	public static void test41() {
		File inputPath = new File("/work/ws/git/report/src/main/resources/tpl/trackingio/");
		File[] files = inputPath.listFiles();
		String regex = "<en>(.*?[\\u4e00-\\u9fa5].*?)</en>";
		Pattern pattern = Pattern.compile(regex);
		File file = new File(inputPath, "new_campaigninfo_bydatecampaign.xml");
		String content = file2Str(file);
		Matcher m = pattern.matcher(content);
		if (m.find()) {
			System.out.println(m.group(1));
		}
	}

	public static String file2Str(File file) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static void test40() {
		String input = "aabbccddaa112233aa$1BBC$CD$Daauiouup";
		Pattern pattern = Pattern.compile("(a)a");
		Matcher m = pattern.matcher(input);
		int count = 1;
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String stufix = "_" + (count++);
			m.appendReplacement(sb, "$1" + stufix);
		}
		m.appendTail(sb);
		System.out.println(sb.toString());
	}

	public static void test39() {
		String regex = "(<.*?column\\sname=\".+?\".*?>.*?<zh>(.*?)</zh>)(\\s*?</column>)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		String input = "<column name=\"number\">\n\t\t\t<zh>推广活动总数</zh>\n\t\t</column>";
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			System.out.println("1====" + m.group(1) + "====");
			System.out.println("2====" + m.group(2) + "====");
			System.out.println("3====" + m.group(3) + "====");
		}
		String s = input.replaceAll(regex, "11");

		s = pattern.matcher(input).replaceAll("$1\n\t\t\t<en>helloworld</en>\n$3");
		System.out.println(s);
	}

	public static void test38() {
		String s = "/work/ws/git/report/src/main/resources/tpl/trackingio/campaigninfo_byds.xml";
		Matcher m = Pattern.compile(".*?\\/([^\\/]*?)\\.xml").matcher(s);
		if (m.find()) {
			System.out.println(m.group(1));
		}

		String ss = s.replace(".*?\\/([^\\/]*?)\\.xml", "$1");
		System.out.println(ss);
	}

	public static void test37() {
		System.out.println(LOCALE.EN.getCode());
		System.out.println(LOCALE.std("testtt"));
	}

	public static void test36() {
		String pathName = "/work/ws/git/trackingio/src/main/websrc/template/active/product.html";
		String regex = ">\\s*(.*?[\\u4e00-\\u9fa5]+.*?)\\s*<";
		Pattern pattern = Pattern.compile(regex);
		File file = new File(pathName);
		String line = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				Matcher m = pattern.matcher(line);
				if (m.find()) {
					System.out.println(m.group(1));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void test35() {
		String s = null;
		assert s != null ? true : false;
		assert false;
		System.out.println("end");
	}

	public static void test34() {
		List<Dog> dogs = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			dogs.add(new Dog("name" + i));
		}
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.gc();
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// System.gc();
		// try {
		// Thread.sleep(1000L);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
//		System.out.println(dogs.get(0));
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("jvm shutdown");
			}
		}));
	}

	public static void test33() {
		int[] arr = new int[] { 1, 3, -1, 0, 2, 1, -4, 2, 0, 1, -7, 0, -9, 5, 13, -1, 4, 6 };

		int k = 0, w = arr.length - 1;
		while (k < w) {
			if (arr[k] <= 0 && arr[w] > 0) {// swap
				System.out.println(String.format("swap:<%d,%d> [%d,%d]", k, w, arr[k], arr[w]));
				arr[k] += arr[w];
				arr[w] = arr[k] - arr[w];
				arr[k] = arr[k] - arr[w];
				k++;
				w--;
			} else if (arr[k] <= 0 && arr[w] <= 0) {
				w--;
				System.out.println("w--");
			} else if (arr[k] > 0) {
				k++;
				System.out.println("k++");
			}
		}

		System.out.println(Arrays.toString(arr));
	}

	public static void test32() {
		BigDecimal PI = new BigDecimal(0).setScale(100, BigDecimal.ROUND_HALF_UP);
		BigDecimal flag = new BigDecimal(1).setScale(100, BigDecimal.ROUND_HALF_UP);

		BigDecimal sub = new BigDecimal(4);
		for (long w = 1; w <= 1000L; w++) {
			PI = PI.add(sub.multiply(flag)
					.multiply(new BigDecimal(1).divide(new BigDecimal(2 * w - 1), 100, BigDecimal.ROUND_HALF_UP)));
			sub = sub.multiply(new BigDecimal(-1));
		}
		System.out.println(PI.stripTrailingZeros().toPlainString());
	}

	public static void test31() {
		// computer PI
		double PI = 0;
		double flag = 1;
		for (long w = 1; w <= 1000000000L; w++) {
			PI += 4.0 * flag * (1.0 / (2 * w - 1));
			flag *= -1;
		}
		System.out.println(PI);
	}

	public static void test30() {
		System.out.println(System.currentTimeMillis() / 1000);
	}

	public static void test29() {
		final Lock lock = new ReentrantLock();

		List<Thread> tlist = new ArrayList<Thread>();
		for (int i = 0; i < 3; i++) {
			tlist.add(new Thread() {
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						if (lock.tryLock()) {
							System.out.println(Thread.currentThread().getName() + "|| getlock");
							try {
								Thread.sleep(2000L);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							} finally {
								lock.unlock();
								System.out.println(Thread.currentThread().getName() + "|| unlock");
								try {
									Thread.sleep(1000L);
								} catch (InterruptedException e) {
									Thread.currentThread().interrupt();
								}
							}
						} else {
							System.out.println(Thread.currentThread().getName() + "|| not get lock");
							try {
								Thread.sleep(1000L);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
						}
					}
				}
			});
		}
		for (Thread t : tlist) {
			t.start();
		}

		try {
			Thread.sleep(10 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Thread t : tlist) {
			t.interrupt();
		}

		System.out.println("over");
	}

	public static void test28() {
		String url = "";
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();

		String cb = "RW70o3BlNn1nfKTkHPmasSfzm6SKR0_R5AMeIcnOFutYiryCrWwg11bfIpvrzGiq";
		mEntityBuilder.addBinaryBody("callback", cb.getBytes());
		mEntityBuilder.addTextBody("accountUserId", "90909");
		mEntityBuilder.addTextBody("target", "3");
		mEntityBuilder.addTextBody("dataDetail", "{purchase_amount=13412, purchase_time=1535594573000}");
		// byte[] postBody
		// mEntityBuilder.addBinaryBody(postName, postBody);
		httppost.setEntity(mEntityBuilder.build());

		// 不写成接口：可以直接写在一起
		BufferedReader in = null;
		try {
			HttpResponse response = httpClient.execute(httppost);

			int code = response.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				in = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				System.out.println(sb.toString());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test27() {
		// https://ad.e.kuaishou.com/rest/dmp/log?accountUserId=90909&target=3&dataDetail={purchase_amount=13412,
		// purchase_time=1535594573000}
		String url = "https://ad.e.kuaishou.com/rest/dmp/log";
		HttpPost hp = new HttpPost(url);

		CloseableHttpClient client = HttpClientBuilder.create().build();

		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("accountUserId", "90909"));
		nvps.add(new BasicNameValuePair("target", "3"));
		nvps.add(new BasicNameValuePair("dataDetail", "{purchase_amount=13412, purchase_time=1535594573000}"));
		// nvps.add(new BasicNameValuePair("",""));
		// nvps.add(new BasicNameValuePair("",""));
		// nvps.add(new BasicNameValuePair("",""));

		hp.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
		BufferedReader in = null;
		try {
			HttpResponse response = client.execute(hp);

			int code = response.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				in = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				System.out.println(sb.toString());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test25() {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");

		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String v = it.next();
			if (v.equals("bbb") || v.equals("ccc")) {
				it.remove();
			}
		}
		System.out.println(list);

		String url = "http://www.googleadservices.com/pagead/conversion/app/1.0?dev_token=s_6JzifStn1cmnCxgOW8Uw&link_id=58C1B1E3F41475D332AABD35E9C63E43&app_event_type=in_app_purchase&rdid=0F7AB11F-DA50-498E-B225-21AC1977A85D&id_type=idfa&lat=0&app_version=1.2.4&os_version=9.3.2&sdk_version=1.9.5r6&timestamp=1432681913.123456&value=1.99&currency_code=USD";
	}

	public static void test24() {
		int N = 44354;

		int i = 0;
		int mask = ~(1 << 6);
		System.out.println(Integer.toHexString(mask).toUpperCase());
		System.out.println(Integer.toBinaryString(mask));

		int rn = (mask + (i << 6)) & N;
		System.out.println(Integer.toHexString(N).toUpperCase());

		System.out.println(Integer.toHexString(rn).toUpperCase());

		System.out.println(Integer.toBinaryString(N));
		System.out.println(Integer.toBinaryString(rn));

		System.out.println(setFrontGenSurl(true));
		System.out.println(setFrontGenSurl(false));

		System.out.println(setBit(3, true, 1));
		System.out.println(setBit(3, false, 1));

		System.out.println(setBit(4, true, 1));
		System.out.println(setBit(4, false, 1));

		for (int w = 1; w < 5; w++) {
			System.out.println(getBit(5, w));
		}
	}

	private static int setBit(int N, boolean value, int offset) {
		if (value) {
			int mask = 1 << (offset - 1);
			return N | mask;
		} else {
			int mask = ~(1 << (offset - 1));
			return N & mask;
		}
	}

	public static boolean getBit(int target, int offset) {
		int mask = 1 << (offset - 1);
		return (target & mask) >> (offset - 1) == 1;
	}

	private static int setFrontGenSurl(boolean frontGenSurl) {
		int mark = 0;
		int mask = 0x7FFFFFFE + (frontGenSurl ? 1 : 0);
		return mark & mask;
	}

	public static void test23() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("s", "s");

		Collection<String> list = map.values();
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String next = it.next();
			System.out.println(next);
		}
	}

	public static void test22() {
		List<Dog> list = Arrays
				.asList(new Dog[] { new Dog(1, "1", "dog1"), new Dog(2, "1", "dog1"), new Dog(3, "3", "dog3") });
		Map<String, Dog> map = Maps.uniqueIndex(list, new Function<Dog, String>() {
			public String apply(Dog input) {
				return input.getCode();
			}
		});
		System.out.println(map);
	}

	public static void test21() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, -1);
		System.out.println(sdf.format(c.getTime()));

		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);

		Object[] olist = new Object[] { 1, 2, 3, 4 };
		Integer[] arr = Arrays.copyOf(olist, olist.length, Integer[].class);
		System.out.println(arr);
	}

	public static void test20() {
		String sql = "SELECT\n" + "m.id,\n" + "m.media_id,\n" + "ME.name as media_name,\n" + "m.product_id,\n"
				+ "m.company_id,\n" + "m.op_company_id,\n" + "m.ad_style,\n" + "m.creative,\n" + "m.ua,\n"
				+ "m.material_type AS materialtype,\n"
				+ "case when m.material_type = 2 then concat('http://img.adinsights.cn/',replace(mr.videos,',',',http://img.adinsights.cn/')) else concat('http://img.adinsights.cn/',replace(mr.images,',',',http://img.adinsights.cn/')) end as materialurl,\n"
				+ "m.lp,\n" + "m.add_time,\n" + "m.last_time,\n" + "m.total,\n" + "m.duration,\n"
				+ "p. NAME AS product_name,\n" + "c. NAME AS company,\n" + "co. NAME AS op_company,\n"
				+ "m.audit_time,\n" + "p.type_id,\n" + "p.logo,\n" + "m.cat_id,\n" + "m.tag_id\n" + "FROM\n"
				+ "material m FORCE INDEX (\n" + "ix_material_last_time,\n" + "ix_material_total,\n"
				+ "ix_material_duration,\n" + "ind_mediaid,\n" + "ind_pid,\n" + "ix_status\n" + ")\n"
				+ "LEFT JOIN product p ON m.product_id = p.id\n" + "LEFT JOIN company c ON m.company_id = c.id\n"
				+ "LEFT JOIN media ME ON m.media_id=ME.id\n" + "LEFT JOIN company co ON m.op_company_id = co.id\n"
				+ "LEFT JOIN material_resources mr on m.res_id=mr.id\n" + "WHERE m.id='";
		System.out.println(sql);
	}

	public static int[] quickSort(int[] list) {

		return null;
	}

	public static void test19() {
		int a = 3;
		int b = 91;
		b = a ^ b;
		a = b ^ a;
		b = b ^ a;
		System.out.println(a);
		System.out.println(b);
	}

	public static void test18() {
		String url = "https://hdd.youyuegame.com/h5/201805/20180528/18098/index.html";
		// String result = HttpUtil.doGet(url);
		// System.out.println(result);
	}

	public static void test17() {
		int a1 = 1;
		int a2 = 2;
		int a3 = 3;
		int a5 = 5;

		int a = 0;
		for (int i = 1; i <= 5; i++) {
			a ^= i;
		}
		a ^= a1;
		a ^= a2;
		a ^= a3;
		a ^= a5;

		System.out.println(a);
	}

	public static void test16() {
		String regex = "^.*\\.apk$";
		String lp = "http://static.centanet.com/app/app-jrtt_sz-release.apk";
		System.out.println(Pattern.compile(regex).matcher(lp).find());
	}

	public static void test15() {
		HtmlImageGenerator hig = new HtmlImageGenerator();
		hig.loadUrl("http://td01.iruifu.com.cn/tracking/promote/datang/fWvSTz4vmOTz.html");
		try {
			Thread.sleep(10000);
			hig.setSize(new Dimension(200, 500));
			hig.getBufferedImage();
			Thread.sleep(10000);
			hig.saveAsImage("/work/test/0709/save.jpg");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test14() {
		final Lock lock = new ReentrantLock();
		final Condition con_pro = lock.newCondition();
		final Condition con_cus = lock.newCondition();

		final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(5);

		Thread pro = new Thread(new Runnable() {
			public void run() {
				while (true) {
					lock.lock();
					int N = new Random(System.currentTimeMillis()).nextInt(10);
					for (int i = 0; i < N; i++) {
						try {
							Thread.sleep(1000L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String product = "product_" + i;
						boolean flag = queue.offer(product);
						if (!flag) {
							try {
								con_pro.await();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else {
							System.out.println("pro " + product);
							con_cus.signal();
						}
					}
					lock.unlock();

				}
			}
		});

		Thread cus = new Thread(new Runnable() {
			public void run() {
				while (true) {
					lock.lock();
					String val = queue.poll();
					if (val == null) {
						con_pro.signal();
						try {
							con_cus.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("cus " + val);
					}
					lock.unlock();
				}
			}
		});

		pro.start();
		cus.start();
	}

	public static void test13() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("loop " + i);
				}
			}
		});
		System.out.println("start");
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}

	public static void test12() {
		String target = null;
		String key = "3";
		try {
			if (key.equals("3")) {
				System.out.println("exit");
				return;
			}
			target = "haha";
		} finally {
			System.out.println(target.trim());
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test11() {
		String regex = "http:\\/\\/clickc\\.admaster\\.com\\.cn\\/c\\/a110391,b2638231,c3078,i0,m101,8a2,8b3,h";
		String input = "http://clickc.admaster.com.cn/c/a110391,b2638231,c3078,i0,m101,8a2,8b3,h";
		boolean flag = Pattern.compile(regex).matcher(input).find();
		System.out.println(flag);

		HashMap<String, String> map = new HashMap<String, String>();

	}

	public static void test10() {
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("bbc");
		list.add("cbc");
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String str = it.next();
			System.out.println(str);
			if (str.equals("abc")) {
				it.remove();
			}
		}
	}

	public static void test9() {
		List<String> list = Arrays.asList(new String[] { "111", "", "", "222" });
		list = new ArrayList<String>(list);
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String item = it.next();
			if ("111".equals(item)) {
				it.remove();
			}
		}
		System.out.println(list);
	}

	public static void test8() {
		Map<String, String> target = new HashMap<String, String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			String realPath = getPath(Test.class) + "test/xmls/productinsight.xml";
			Document doc = db.parse(realPath);
			Element root = doc.getDocumentElement();
			NodeList nodelist = root.getElementsByTagName("sql");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);
				System.out.println(
						node.getAttributes().getNamedItem("id").getNodeValue() + "=" + node.getTextContent().trim());
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getPath(Class name) {
		String strResult = null;
		if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
			strResult = name.getResource("/").toString().replace("file:/", "").replace("%20", " ");
		} else {
			strResult = name.getResource("/").toString().replace("file:", "").replace("%20", " ");
		}
		return strResult;
	}

	public static void test4() {
		String s = "videos\\m_v_0805572d64dd42e4acb5f9efd0f5dc90.mp4";
		System.out.println(s.replace("videos\\", ""));
	}

	public static void test7() {
		String sql = "AAA#{BBB}\n#{CCC}\n#{DDD}\nEEE";
		List<Object> params = Arrays.asList(new Object[] { "", null, "222" });
		System.out.println(processSql(sql, params));
	}

	public static String processSql(String sql, List<Object> params) {
		String returnSql = new String(sql);
		Pattern p = Pattern.compile("#\\{.*?\\}");
		for (Object item : params) {
			if (item == null || (item instanceof String && "".equals(item))) {
				returnSql = p.matcher(returnSql).replaceFirst(" ");
			} else {
				returnSql = returnSql.replaceFirst("\\{", "").replaceFirst("\\}", "");
			}
		}
		return returnSql;
	}

	public static void test5() {
		String s1 = "灵谕-唯美仙侠开启修仙情缘";
		String s2 = "灵谕-唯美仙侠开启修仙情缘";
		System.out.println(s1.equals(s2));
	}

	public static void test3() {
		Interner<String> pool = Interners.newWeakInterner();

		pool.intern("");
		System.out.println(new String("hello") == new String("hello"));

		System.out.println(pool.intern(new String("hello")) == pool.intern(new String("hello")));
		System.out.println(new String("hello").intern() == pool.intern(new String("hello")));
	}

	public static void test1() {
		String s = "abc";
		String s1 = new String(s);
		String s2 = new String(s);
		System.out.println(s1 == s2);
		System.out.println(s1.toCharArray() == s2.toCharArray());
		System.out.println(s1.getBytes() == s2.getBytes());
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());

		System.out.println(s1.intern() == s2.intern());
	}

	public static void target(String t) {
		for (int i = 0; i < 5; i++) {
			synchronized (t.intern()) {
				// try {
				System.out.println(String.format("%s--%s", Thread.currentThread().getName(), t));
				if ("thread_1".equals(Thread.currentThread().getName())) {
					int b = 4 / 0;
					System.out.println(b);
				}
				// }catch(Throwable e) {
				// break;
				// }
			}
		}
	}

	public static void test2() {
		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(new Runnable() {

				public void run() {
					Test.target("hello");
				}

			}, "thread_" + i);
			list.add(thread);
		}
		for (Thread thread : list) {
			thread.start();
		}
	}
}

class Dog implements Cloneable {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private int id;
	private String code;
	private String name;

	public Dog() {
	}

	public Dog(int id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Dog(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("dog " + this.name + " finalize");
	}

}

enum LOCALE {
	ZH("zh"), EN("en");

	private String code;

	LOCALE(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static String std(String locale) {
		String result = ZH.getCode();
		for (LOCALE e : LOCALE.values()) {
			if (e.getCode().equals(locale)) {
				result = locale;
			}
		}
		return result;
	}
}