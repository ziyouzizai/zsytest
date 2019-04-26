package test.myutils;

public interface FileProcesser {
	default void init(String fileName) {};
	void process(String fileName,long lineNum,String line);
	default void end(String fileName,long totalNum) {};
}
