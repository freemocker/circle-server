package com.ubatis.circleserver.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

	/**
	 * 随机编码
	 * @param cout 随机编码的位数
	 * @return
	 */
	public static String getRandomCharCode(int cout){
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',     
	            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',     
	            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cout; i++) {
			sb.append(codeSequence[new Random().nextInt(codeSequence.length-1)]);
		}
		return sb.toString();
	}

	/**
	 * 随机数字
	 * @param cout
	 * @return
	 */
	public static String getRandomNumberCode(int cout){
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cout; i++) {
			sb.append(codeSequence[new Random().nextInt(codeSequence.length-1)]);
		}
		return sb.toString();
	}

	public static String UUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

}
