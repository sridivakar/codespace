package ponderthis.p_2013_01;

import java.util.ArrayList;
import java.util.List;

public class Solver {
	private final char[] inputChars;
	private final int subStringLength;
	
	public Solver(String inputString) {		
		this(inputString.toCharArray(), 3);
	}
	
	public Solver(String inputString, int _subStringLength) {		
		this(inputString.toCharArray(), _subStringLength);
	}
	
	
	public Solver(char[] _inputChars) {
		this(_inputChars, 3);
	}
	
	public Solver(char[] _inputChars, int _subStringLength) {
		this.inputChars = _inputChars;
		this.subStringLength = _subStringLength;
	}
	
	private void generateAllSubstrings(List<String> list){
		
		return ;		
	}
	
	public static void main(String[] args) {	
		Solver solver = new Solver("ABCEFHIJLMNPQTVXYZ", 3);
		List<String> list = new ArrayList<String>();
		solver.generateAllSubstrings(list);
	}
}
