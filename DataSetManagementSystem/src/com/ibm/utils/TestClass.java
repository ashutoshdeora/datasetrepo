package com.ibm.utils;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "A & B";
		String temp = null;
		temp = a;
		temp = a.replaceAll("(?=[]\\[+&|!(){}^\"~*?:\\\\-])", "\\\\");
		System.out.println(temp);

	}

}
