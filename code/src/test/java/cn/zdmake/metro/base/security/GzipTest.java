package cn.zdmake.metro.base.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GzipTest {
	
	static class bytesConvert{
		public static String bytesToHexString(byte[] src) {
			StringBuilder stringBuilder = new StringBuilder("");
		    if (src == null || src.length <= 0) {   
		        return null;   
		    }   
		    for (int i = 0; i < src.length; i++) {   
		        int v = src[i] & 0xFF;   
		        String hv = Integer.toHexString(v);   
		        if (hv.length() < 2) {   
		            stringBuilder.append(0);   
		        }   
		        stringBuilder.append(hv);   
		    }   
		    return stringBuilder.toString();   
		}
		
		public static byte[] hexStringToBytes(String hexString) {
			if (hexString.length() % 2 != 0)
				hexString = "0" + hexString;
			int len = hexString.length() / 2;
			byte[] val = new byte[len];
			for (int i = 0; i < len; i++) {
				val[i] = (byte) (toInt(hexString.charAt(2 * i)) * 16 + toInt(hexString.charAt(2 * i + 1)));
			}
			return val;
		}
		
		private static int toInt(char a) {
			if (a >= '0' && a <= '9')
				return a - '0';
			if (a >= 'A' && a <= 'F')
				return a - 55;
			if (a >= 'a' && a <= 'f')
				return a - 87;
			return 0;
		}

	}

	@Test
	public void testGZIP() {
		String s = "abcdefg";
		byte[] b = GZIP.compressToByte(s);
		System.out.println(bytesConvert.bytesToHexString(b));
		assertTrue(GZIP.uncompressToString(b).equals(s));
	
		String o;
		o = GZIP.uncompressToString(bytesConvert.hexStringToBytes("1f8b080064b7715902ff4b4c4a4e494d4b0700a66a2a3107000000"));
		System.out.println(o);
	}
}
