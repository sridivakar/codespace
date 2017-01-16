package ponderthis.p_2013_01;

import java.util.ArrayList;
import java.util.List;

public class PermuataionsAndCombinations {
	
	private final String origString;
	private final int r;
	private final List<String> result = new ArrayList<String>();
	
	public PermuataionsAndCombinations(String string, int r) {
		this.origString = string;
		this.r = r;
	}
	
	public  List<String> getNcrValues() {
		
		char[] input = new char[r];
		ncr(input, 0, 0);
		return result;
	}

	public  List<String> getNprValues() {
		
		char[] input = new char[r];
		npr(input, -1, 0);
		return result;
	}

	
	private void ncr(char[] charArray, int origStringIndex, int indexToFill) {
		for (int i = origStringIndex; i < origString.length(); i++) {
			charArray[indexToFill] = origString.charAt(i);
			if (indexToFill + 1 < charArray.length) {
				ncr(charArray, i + 1, indexToFill + 1);
			} else {
				result.add(String.valueOf(charArray));
			}
		}
	}
	
	
	private void npr(char[] charArray, int origStringIndex, int indexToFill) {
		
		for (int i = 0; i < origString.length(); i++) {
			if (i == origStringIndex) {
				continue;
			}
			charArray[indexToFill] = origString.charAt(i);
			if (indexToFill + 1 < charArray.length) {
				npr(charArray, i, indexToFill + 1);
			} else {
				result.add(String.valueOf(charArray));
			}
		}
	}
	
	public static void main(String[] s) {
		PermuataionsAndCombinations ncr = new PermuataionsAndCombinations("ABCDE", 2);
		List<String> list = ncr.getNcrValues();
		
		System.out.println(list);
	}

}
