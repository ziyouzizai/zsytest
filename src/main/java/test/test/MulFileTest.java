package test.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
* 类说明 
*
* @author zhangsy
* @date 2018年7月21日
*/
public class MulFileTest {
	private String logPath = "";
}

class Runner implements Runnable{
	private String logPath = null;
	private String name = null;
	public Runner(String logPath,String name) {this.logPath = logPath;this.name = name;}
	public void run() {
		File txtFile = new File(logPath);
        if (!txtFile.exists()) {
            try {
                txtFile.createNewFile();// 创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedWriter bw = null;
            try {
				bw = new BufferedWriter(new FileWriter(txtFile));
				for(int i=0;i<100;i++) {
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
        }
        
	}
}