package test.net;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * url链接下载文件的辅助工具类
 * 
 * @author Administrator
 *
 */
public class FileUrlConvertUtils {

	private static Pattern fileNamePattern = Pattern.compile("https*://.*/(.*?)$");
    /**
     * 从url获取文件内容的字节数组
     * @param fileUrl
     * @return
     */
    public static byte[] loadFileByteFromURL(String fileUrl) {

        if (fileUrl.startsWith("http://")) {
            return httpConverBytes(fileUrl);
        } else if (fileUrl.startsWith("https://")) {
            return httpsConverBytes(fileUrl);
        } else {
            return null;
        }

    }

    /**
     * @MethodName httpConverBytes
     * @Description http路径文件内容获取
     *
     * @param path
     * @return
     */
    public static byte[] httpConverBytes(String fileUrl) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        URLConnection conn = null;

        try {
            URL url = new URL(fileUrl);
            conn = url.openConnection();
            String contentType = conn.getHeaderField("Content-Type");
            String server = conn.getHeaderField("server");
            System.out.println(contentType);
            System.out.println(server);
            in = new BufferedInputStream(conn.getInputStream());

            out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            byte[] content = out.toByteArray();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * @MethodName httpsConverBytes
     * @Description https路径文件内容获取
     *
     * @param url
     * @return
     */
    private static byte[] httpsConverBytes(String fileUrl) {
        BufferedInputStream inStream = null;
        ByteArrayOutputStream outStream = null;

        try {

            TrustManager[] tm = { new TrustAnyTrustManager() };
            SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
            sc.init(null, tm, new java.security.SecureRandom());
            URL console = new URL(fileUrl);

            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();

            String contentType = conn.getHeaderField("Content-Type");
            String server = conn.getHeaderField("server");
            System.out.println(contentType);
            System.out.println(server);
            
            inStream = new BufferedInputStream(conn.getInputStream());
            outStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }

            byte[] content = outStream.toByteArray();
            return content;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static long downFromUrl(String fileUrl,String savePath) {
    	BufferedInputStream inStream = null;
        FileOutputStream outStream = null;
        long b = 0;
        try {

            TrustManager[] tm = { new TrustAnyTrustManager() };
            SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
            sc.init(null, tm, new java.security.SecureRandom());
            URL console = new URL(fileUrl);

            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();

            inStream = new BufferedInputStream(conn.getInputStream());
            outStream = new FileOutputStream(savePath);

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
                b += len;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != outStream) {
                try {
                	outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return b;
    }
    
    /**
     * 信任证书的管理器
     * @author Administrator
     *
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf('.');
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    public static void download(String fileUrl,String savePath) throws IOException {
    	byte[] fileBytes = loadFileByteFromURL(fileUrl);
    	String name = parseNameFromUrl(fileUrl);
    	name = (name == null) ? "unname" + timestamps() : name;
    	File file = new File(savePath,name);
    	OutputStream output = new FileOutputStream(file);
    	org.apache.commons.io.IOUtils.write(fileBytes, output);
    	output.close();
    }
    
    public static void download2(String fileUrl,String filePathName) throws IOException {
    	downFromUrl(fileUrl,filePathName);
    }
    
    public static String timestamps() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
    	return sdf.format(new Date());
    }
    
    public static String parseNameFromUrl(String url) {
    	Matcher m = fileNamePattern.matcher(url);
    	return m.find() ? m.group(1) : null;
    }
}
