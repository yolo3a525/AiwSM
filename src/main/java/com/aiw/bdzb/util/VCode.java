package com.aiw.bdzb.util;

import java.util.Random;

public class VCode {

	
	public static String getCode() {
	         
		Random  d = new Random();

		String str = "";

		for(int i=0;i<4;i++){

		int num = d.nextInt(10);

		str += num+"";

		}
		return str;
	}
	
	public static void main(String[] args) {
		
		
		System.out.println(getCode());
	}
}
