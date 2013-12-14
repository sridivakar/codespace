package sutansums.generator;

import sutansums.problem.IProblem;

public interface IGenerator <P extends IProblem>{

	int getNumberOfOperands();
	
	int getNumberOfDigits();
	
	P getNext();
	
	char getSymbol();	
	
}
