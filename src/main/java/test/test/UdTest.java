package test.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import test.myutils.FileUtil;
import test.myutils.StringUtil;

public class UdTest {
	@SuppressWarnings("rawtypes")
	private static Map<String, BloomFilter> bfMap = new HashMap<>();;
	private static List<String> camps = Arrays.asList(new String[] {"903a5d67d20d9bbe","93e560696e2082e6"});
	private static Map<String,JSONObject> campInfos = new HashMap<>();

	static {
		System.out.println("系统初始化...");
		System.out.println("活动信息加载中...");
		for(String camp : camps) {
			try {
				campInfos.put(camp,getCampJson(camp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("活动信息加载完成");
		
		System.out.println("过滤器信息加载中...");
		try {
			loadBf(camps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("过滤器信息加载完成");
		System.out.println("系统初始化完成");
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("please input:");
			String line = scanner.nextLine();
			if("exit".equals(line)) {
				scanner.close();
				return;
			}else {
				System.out.println("idfa=" + line);
				List<String> result = convertIdfa(line);
				System.out.println(StringUtil.join(result,","));
			}
		}
	}
	
	public static void test() throws Exception{
		// String signData =
		// "fa253f5de6354bdd7f7318ec765e7840a6c1548853a27ebfa2e07c597c1b462a34c7b1323b21c381a765a8a4dd5a6f4fcbe5143db72bdf976c19922790df72a007d4e3593610dbe522bc85c5dbdc2716d6d706185e0a8c33be07d8ea2092409b344cf5f311ae188159140ae992a65b9f538299ae4c7ed68308f8aa6a92566d2e8d09c5cf492f76ea0a5041bf4680807e846e2cabffcc31c0348439388f76c3ef930c08d9205df3d350806e7816a0b7cc40b7b4508efe2c972e4019fb86e4d98e1f4f574279fe6b227119798d5c205300fd80d24c9455786a";
		// String signData =
		// "fa253f5de6354bdd7f7318ec765e7840a6c1548853a27ebfa2e07c597c1b462a34c7b1323b21c381a765a8a4dd5a6f4fcbe5143db72bdf976c19922790df72a007d4e3593610dbe522bc85c5dbdc2716d6d706185e0a8c33be07d8ea2092409b344cf5f311ae188159140ae992a65b9f538299ae4c7ed68308f8aa6a92566d2e8d09c5cf492f76ea0a5041bf4680807e846e2cabffcc31c0348439388f76c3ef930c08d9205df3d350806e7816a0b7cc40b7b4508efe2c972e4019fb86e4d98ea8b770ebc725f88bae7bd8590cc5aca4c2d4da6fde98e7f5";
		// System.out.println(parse(signData));

		// System.out.println(parse("ed6cd3a74d908ed0"));

		
		String camp = "3967ef6cf4ada9e207a3b939e2cc602d59d27dc4ac1923a234d61ce3bba6e7d8a991073da3c37b6eadb5c9f8dc4521753985d7562ff040a38141008a2a7d7b4ef4e2d2aea7388e7d2b7532e3a09dd2083fd228c5e7909d3a43ee36347e9f7b46fe96aecf078f081e24f3bd2f3549ae989b565d751f525790e1fa83491aa77da62c931650a210827f41070023d674a9ba6406fade822d250aff04626a5c7ae8122f96a8e8c017d4de0d4b9d5411ed803dbbd53219ebec01a7ef5526979e39d6e388606df33e75f9456bc776d24914bb329f6eb723ecc69c0f863b3e1391a1a36077b5b7e8af1d5d96";
		String campjson = parse(camp);
		System.out.println(campjson);
		
		JSONObject json = JSONObject.parseObject(campjson);
		JSONObject audience = json.getJSONArray("audiences").getJSONObject(0);
		String key = audience.getInteger("id") + "_" + audience.getInteger("deviceid_type") + "_" + audience.getString("ver");
		System.out.println(converDataKey(key));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> convertIdfa(String idfa) throws Exception {
		List<String> result = new ArrayList<>();
		String code = m(idfa);
		//String af = parse("signdata[fec_salt]");
		String af = parse("ed6cd3a74d908ed0");//就是"@"
		//String key = "camp[audiences[id]] + \"_\" + camp[audiences[deviceid_type]] + \"_\" + camp[audiences[ver]]";
		
		for(String camp : camps) {
			String key = getKey(camp);
			code = m(code + af + key);
			BloomFilter bf = bfMap.get(camp);
			if(bf.mightContain(code)) {
				result.add(camp);
			}
		}
		return result;
	}

	public static String getKey(String camp) {
		JSONObject json = campInfos.get(camp);
		JSONObject audience = json.getJSONArray("audiences").getJSONObject(0);
		return audience.getInteger("id") + "_" + audience.getInteger("deviceid_type") + "_" + audience.getString("ver");
	}
	
	public static JSONObject getCampJson(String camp) throws Exception {
		String baseDir = "/work/test/common/fec/info/camp/";
		String filePath = baseDir + camp + ".cam";
		String data = FileUtil.file2Str(filePath);
		String json = parse(data);
		return JSONObject.parseObject(json);
	}

	// 加密key(bf文件的文件名) key="camp[audiences[id]] + \"_\" +
	// camp[audiences[deviceid_type]] + \"_\" + camp[audiences[ver]]";
	@SuppressWarnings("restriction")
	public static String converDataKey(String key) throws Exception {
		String strKey = "tourhb";
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Cipher cipher = Cipher.getInstance("DES");
		Key k = d(strKey.getBytes());
		cipher.init(1, k);
		byte[] bsr = cipher.doFinal(key.getBytes());
		return a(bsr);
	}

	// c().l()方法
	@SuppressWarnings("restriction")
	public static String parse(String data) throws Exception {
		String strKey = "tourhb";
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Cipher cipher = Cipher.getInstance("DES");
		Key key = d(strKey.getBytes());
		cipher.init(2, key);
		return new String(cipher.doFinal(j(data)));
	}

	public static String a(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0)
				intTmp += 256;
			if (intTmp < 16)
				sb.append("0");
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static Key d(byte[] arrBTmp) throws Exception {
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

	public static String m(String content) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] md5Bytes = md5.digest(content.getBytes("UTF-8"));
		StringBuffer hexValue = new StringBuffer();
		for (byte i : md5Bytes) {
			int val = i & 0xFF;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	@SuppressWarnings("rawtypes")
	public static void loadBf(List<String> cmaps) throws Exception {
		String baseDir = "/work/test/common/fec/data/prepared/";
		for (String camp : camps) {
			String key = getKey(camp);
			String bfFileName = converDataKey(key);
			File file = new File(baseDir + bfFileName);
			FileInputStream fin;
			try {
				System.out.println("bf camp:" + camp + " loading...");
				fin = new FileInputStream(file);
				BloomFilter bf = BloomFilter.readFrom(fin, Funnels.stringFunnel(Charset.forName("utf-8")));
				bfMap.put(camp, bf);
				System.out.println("bf camp:" + camp + " loaded");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
