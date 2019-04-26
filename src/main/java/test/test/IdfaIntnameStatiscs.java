package test.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.myutils.CollectionUtil;
import test.myutils.DateUtil;
import test.myutils.FWriter;
import test.myutils.FileProcesser;
import test.myutils.FileUtil;

public class IdfaIntnameStatiscs {
	public static void main(String[] args) {
		String sourceFilePath = "/work/test/2019/0220/idfa_intname.dat";
		long start = System.currentTimeMillis();
		FileUtil.scanFile(sourceFilePath,new FileProcesser() {
			private Map<String,Map<String,Set<String>>> datMap = new HashMap<>();
			
			public void process(String fileName, long lineNum, String line) {
				String[] arr = line.split(",");
				String intname = arr[0];
				String date = arr[1];
				String idfa = arr[2];
				
				if(datMap.get(intname) == null) {
					datMap.put(intname,new HashMap<String,Set<String>>());
				}
				if(datMap.get(intname).get(date) == null) {
					datMap.get(intname).put(date,new HashSet<String>());
				}
				datMap.get(intname).get(date).add(idfa);
				
				if(lineNum % 1000 == 0) {
					System.out.println(lineNum);
				}
			}
			public void end(String fileName, long totalNum) {
				String outputDir = "/work/test/2019/0220/idfadat/";
				Iterator<String> it = datMap.keySet().iterator();
				while(it.hasNext()) {
					String intname = it.next();
					FWriter fw = new FWriter(outputDir + String.format("result_%s.csv",intname));
					fw.init();
					
					Map<String,Set<String>> dateMap = datMap.get(intname);
					List<String> dateList = CollectionUtil.convertSetToSortList(dateMap.keySet());
					for(int i=0;i<dateList.size()-1;i++) {
						int j = i+1;
						String d1 = dateList.get(i);
						String d2 = dateList.get(j);
						
						if(DateUtil.getDvalue(d2,d1) != 1L)
							continue;
						float baseNum = (float)(dateMap.get(dateList.get(i)).size());
						int intersectionNum = CollectionUtil.getIntersection(CollectionUtil.convertSetToList(dateMap.get(dateList.get(i))), CollectionUtil.convertSetToList(dateMap.get(dateList.get(j)))).size();
						
						if(intersectionNum/baseNum > 1) {
							System.out.println("here");
						}
						String line = String.format("%s,%s,%d,%d,%d,%f",
								d1,
								d2,
								dateMap.get(dateList.get(i)).size(),
								dateMap.get(dateList.get(j)).size(),
								intersectionNum,
								intersectionNum/baseNum);
						fw.writeLine(line);
						System.out.println(line);
					}
					
					fw.close();
					
					
				}
			}
		});
		
		long end = System.currentTimeMillis();
		System.out.println(String.format("total cost %d ms",end - start));
	}
}
