package test.others;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;

import com.mysql.jdbc.StringUtils;

public class Statistics {
	public static void main(String[] args) throws Exception {
//		statisticsVender();
//		statisticsVersion();
//		statisticsModel();
//		statisticsRepeat();
		statisticsRegion();
	}
	
	public static void statisticsRegion() throws DbMakerConfigException, IOException {
		final Map<String,Map<String,Long>> map = new HashMap<>();
		DbConfig config = new DbConfig();
        final DbSearcher searcher = new DbSearcher(config,"/work/test/0109/ip2region.db");
        
		Processer processer = new Processer() {
			@Override
			public void process(String filePathName, String[] lines, long linenum, BufferedWriter bw) throws IOException {
				String tmp = filePathName.replace("/work/test/1218/result/","");
				String integral = tmp.substring(0,tmp.indexOf("/"));
				String ip = lines[2];
				DataBlock datablock = searcher.binarySearch(ip);
				String region = datablock.getRegion();
				if(map.get(integral) == null) {
					map.put(integral,new HashMap<String,Long>());
				}
				if(map.get(integral).get(region) == null) {
					map.get(integral).put(region,0L);
				}
				map.get(integral).put(region,map.get(integral).get(region) + 1L);
			}
		};
		process(processer);
		
		BufferedWriter bbw = new BufferedWriter(new FileWriter(new File("/work/test/0109/region.dat")));
		
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			bbw.newLine();
			bbw.write(key + "\n");
			Map<String,Long> iv = map.get(key);
			Iterator<String> itt = iv.keySet().iterator();
			while(itt.hasNext()) {
				String k = itt.next();
				bbw.write(k + "," + iv.get(k) + "\n");
			}
			bbw.flush();
		}
	}
	
	public static void statisticsRepeat() throws ClassNotFoundException, IOException {
		final Map<String,List<String>> map = new HashMap<>();
		Processer processer = new Processer() {
			@Override
			public void process(String filePathName, String[] lines, long linenum, BufferedWriter bw) {
				String tmp = filePathName.replace("/work/test/1218/result/","");
				String integral = tmp.substring(0,tmp.indexOf("/"));
				String idfa = lines[4];
				if(map.get(integral) == null) {
					map.put(integral,new ArrayList<String>());
				}
				map.get(integral).add(idfa);
			}
		};
		process(processer);
		
		List<String> list = new ArrayList<>(map.keySet());
		for(int k=0;k<list.size();k++) {
			for(int w=k+1;w<list.size();w++) {
				System.out.println(list.get(k) + "<-->" + list.get(w) + ":");
				List<String> list1 = map.get(list.get(k));
				List<String> list2 = map.get(list.get(w));
				System.out.println(receiveCollectionList(list1,list2).size());
				System.out.println();
			}
		}
	}
	
	public static void statisticsModel() {
		final Map<String,Map<String,Long>> map = new HashMap<>();
		Processer processer = new Processer() {
			@Override
			public void process(String filePathName, String[] lines, long linenum, BufferedWriter bw) {
				String tmp = filePathName.replace("/work/test/1218/result/","");
				String integral = tmp.substring(0,tmp.indexOf("/"));
				String vendor = lines[1];
				if(map.get(integral) == null) {
					map.put(integral,new HashMap<String,Long>());
				}
				if(map.get(integral).get(vendor) == null) {
					map.get(integral).put(vendor,0L);
				}
				map.get(integral).put(vendor,map.get(integral).get(vendor) + 1L);
			}
		};
		process(processer);
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println();
			System.out.println(key);
			Map<String,Long> iv = map.get(key);
			Iterator<String> itt = iv.keySet().iterator();
			while(itt.hasNext()) {
				String k = itt.next();
				System.out.println(k + "," + iv.get(k));
			}
		}
	}
	
	public static void statisticsVersion() {
		final Map<String,Map<String,Long>> map = new HashMap<>();
		Processer processer = new Processer() {
			@Override
			public void process(String filePathName, String[] lines, long linenum, BufferedWriter bw) {
				String tmp = filePathName.replace("/work/test/1218/result/","");
				String integral = tmp.substring(0,tmp.indexOf("/"));
				String vendor = lines[7];
				if(map.get(integral) == null) {
					map.put(integral,new HashMap<String,Long>());
				}
				if(map.get(integral).get(vendor) == null) {
					map.get(integral).put(vendor,0L);
				}
				map.get(integral).put(vendor,map.get(integral).get(vendor) + 1L);
			}
		};
		process(processer);
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println();
			System.out.println(key);
			Map<String,Long> iv = map.get(key);
			Iterator<String> itt = iv.keySet().iterator();
			while(itt.hasNext()) {
				String k = itt.next();
				System.out.println(k + "," + iv.get(k));
			}
		}
	}
	
	public static void statisticsVender() {
		final Map<String,Map<String,Long>> map = new HashMap<>();
		Processer processer = new Processer() {
			@Override
			public void process(String filePathName, String[] lines, long linenum, BufferedWriter bw) {
				String tmp = filePathName.replace("/work/test/1218/result/","");
				String integral = tmp.substring(0,tmp.indexOf("/"));
				String vendor = lines[3];
				if(map.get(integral) == null) {
					map.put(integral,new HashMap<String,Long>());
				}
				if(map.get(integral).get(vendor) == null) {
					map.get(integral).put(vendor,0L);
				}
				map.get(integral).put(vendor,map.get(integral).get(vendor) + 1L);
			}
		};
		process(processer);
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println();
			System.out.println(key);
			Map<String,Long> iv = map.get(key);
			Iterator<String> itt = iv.keySet().iterator();
			while(itt.hasNext()) {
				String k = itt.next();
				System.out.println(k + "," + iv.get(k));
			}
		}
	}
	
	public static void process(Processer processer) {
		File rootdir = new File("/work/test/1218/result");
		File[] subdirs = rootdir.listFiles();
		for(File sdir : subdirs) {
			File[] files = sdir.listFiles();
			for(File file : files) {
				processCSV(file.getAbsolutePath(),"",",",processer);
			}
		}
	}
	
	public static List<String[]> getCSVContent(String filePath){
		List<String[]> result = null;
		
		return result;
	}
	
	public static void processCSV(String srcFilePath,String distFilePath,String separator,Processer processer) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(new File(srcFilePath)));
			if(!StringUtils.isEmptyOrWhitespaceOnly(distFilePath)) {
				bw = new BufferedWriter(new FileWriter(new File(distFilePath)));
			}
			
			String line = null;
			long linenum = 0L;
			while((line = br.readLine()) != null) {
				linenum++;
				line = line.replaceAll("^\"\\\\\"","").replaceAll("\\\\\"\"$","");
				
				String[] arr = line.split("\\\\\"" + separator + "\\\\\"");
				
				processer.process(srcFilePath,arr, linenum, bw);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
     * @方法描述：获取两个ArrayList的交集
     * @param firstArrayList 第一个ArrayList
     * @param secondArrayList 第二个ArrayList
     * @return resultList 交集ArrayList
     */
    public static List<String> receiveCollectionList(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);// 大集合用linkedlist  
        HashSet<String> othHash = new HashSet<String>(secondArrayList);// 小集合用hashset  
        Iterator<String> iter = result.iterator();// 采用Iterator迭代器进行数据的操作  
        while(iter.hasNext()) {
            if(!othHash.contains(iter.next())) {  
                iter.remove();            
            }     
        }
        resultList = new ArrayList<String>(result);
        return resultList;
    }
	
	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();    
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);    
	    out.writeObject(src);    
	    
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());    
	    ObjectInputStream in = new ObjectInputStream(byteIn);    
	    @SuppressWarnings("unchecked")    
	    List<T> dest = (List<T>) in.readObject();  
	    return dest;    
	}  
}

interface Processer{
	void process(String filePathName,String[] lines,long linenum,BufferedWriter bw) throws IOException;
}
