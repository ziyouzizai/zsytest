package test.others;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.zip.CRC32;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test1 {
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    public static JSONObject getVideoUrl(String videoId) {
        String r = String.valueOf(Math.random());
        System.out.println("r = " + r);
        r = r.substring(2, r.length());
        System.out.println("r = " + r);
        String r1 = "/video/urls/v/1/toutiao/mp4/" + videoId + "?r=" + r;
        System.out.println("r = " + r1);
        CRC32 crc32 = new CRC32();
        crc32.update(r1.getBytes());
        long l = crc32.getValue();
        long l1;
        if (l >= 0) {
            l1 = l;
        } else {
            l1 = l + 0x100000000L;
        }
        System.out.println(" l= " + l);
        System.out.println(" l1= " + l1);
        String url = "http://ib.365yg.com" + r1 + "&s=" + l1;
        System.out.println(" url= " + url);
        String res = null;
        try {
            URL rurl = new URL(url);
            URLConnection conn = rurl.openConnection();
            conn.setRequestProperty("User-Agent'", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36'");
            InputStream is = conn.getInputStream();
            res = convertStreamToString(is);
            System.out.println(" res= " + res);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return JSONObject.parseObject(res);
    }

    public static JSONObject getSortJson(String json){
        JSONObject jsonObj = JSON.parseObject(json);
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        return null;
    }

    public static void main(String[] args) {
//    	220802dd53a6ad6455886406df07a050bab11560fe4000081d9f6da0a64
        JSONObject js = getVideoUrl("1a559a691e6a43589798be2dd5bebbd1");
        String video_list = JSONObject.parseObject(js.getString("data")).getString("video_list");
        getSortJson(video_list);
    }

}