package test.others;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

public class IPLibTest {

	public static void main(String[] args) {
		String ip = "118.26.11.242";
		try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config,"/work/test/0109/ip2region.db");
            DataBlock dataBlock =  searcher.memorySearch(ip);
            System.out.println(dataBlock.toString());
            System.out.println(dataBlock.getRegion());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
