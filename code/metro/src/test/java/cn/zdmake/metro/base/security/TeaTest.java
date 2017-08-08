package cn.zdmake.metro.base.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TeaTest {

	/*
	@Test
	public void testTea() {
		try {
			String str = "12345678";
			String encrypted = Tea.encryptByBase64Tea(str);
			//System.out.println(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	@Test 
	public void testEncryptByBase64Tea() {
		try {
		String str = "{\"request\":[\"GZ8_9L.A0001.F_CV\",\"GZ8_9L.A0002.F_CV\",\"GZ8_9L.A0003.F_CV\"]}";
		String encrypted = Tea.encryptByBase64Tea(str);
		System.out.println("length:" + encrypted.length());
		System.err.println(encrypted);
		assertTrue(Tea.decryptByBase64Tea(encrypted).equals(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
