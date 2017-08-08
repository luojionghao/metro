package cn.zdmake.metro.model;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KeyValueTest {

	@Test
	public void testKey() {
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("testKey");
		keyValue.setValue("中1文2字3符4串");
		
		try {
			String testOut = new ObjectMapper().writeValueAsString(keyValue);
			System.out.println(testOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
