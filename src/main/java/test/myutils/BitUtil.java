package test.myutils;

public class BitUtil {
	public static String toBinaryString(byte b) {
		short[] masks = new short[] {0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80};
		StringBuffer sb = new StringBuffer();
		for(int i=Byte.SIZE -1;i >= 0;i--) {
			sb.append((b & masks[i]) > 0 ? "1" : "0");
		}
		return sb.toString();
	}
	
	public static void printBytes(byte[] bytes) {
		for(int i=0;i<bytes.length;i++) {
			System.out.println(toBinaryString(bytes[i]));
		}
	}
	
	public static byte[] double2Bytes(double d) {
		long value = Double.doubleToRawLongBits(d);
		return long2Bytes(value);
	}
	
	public static double bytes2Double(byte[] bytes) {
		return Double.longBitsToDouble(bytes2Long(bytes));
	}
	
	/**
	 * 转long型为字节数组
	 * 
	 * 字节数组中，低位在前，高位在后
	 * 如：9L对应的字节数组中，第一个元素是(0b00001001)
	 * @param value
	 * @return
	 */
	public static byte[] long2Bytes(long value) {
		byte[] byteRet = new byte[Byte.SIZE];
		for (int i = 0; i < Byte.SIZE; i++) {
			byteRet[i] = (byte) ((value >> Byte.SIZE * i) & 0xff);
		}		
		return byteRet;
	}
	
	/**
	 * 字节数组转化为long型
	 * 
	 * 字节数组中，低位在前，高位在后
	 * 如：9L对应的字节数组中，第一个元素是(0b00001001)
	 * @param bytes
	 * @return
	 */
	public static long bytes2Long(byte[] bytes) {
		long value = 0;
		for (int i = 0; i < Byte.SIZE; i++) {
			value |= ((long) (bytes[i] & 0xff)) << (Byte.SIZE * i);
		}
		return value;
	}
	
	public static void main(String[] args) {
		long n = 189763429L;
		byte[] bytes = long2Bytes(n);
		printBytes(bytes);
		System.out.println(bytes2Long(bytes));
		
		double d = 34567.980572;
		byte[] bs = double2Bytes(d);
		printBytes(bs);
		System.out.println(bytes2Double(bs));
	}
}
