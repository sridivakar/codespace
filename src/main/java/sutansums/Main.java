package sutansums;

import sutansums.generator.AdditionGenerator;
import sutansums.generator.DivisionGenerator;
import sutansums.generator.MultiplicationGenerator;
import sutansums.generator.SubstractionGenerator;
import sutansums.writer.ProblemPrinter;

public class Main {

	public static void main(String[] args) {
		AdditionGenerator additionGenerator_2X1_ = new AdditionGenerator(2/*operands*/, 1 /*digits*/, false);
		AdditionGenerator additionGenerator_3X2_ = new AdditionGenerator(3/*operands*/, 1 /*digits*/, false);
		AdditionGenerator additionGenerator_2X2_ = new AdditionGenerator(2/*operands*/, 2 /*digits*/, false);
		AdditionGenerator additionGenerator_3X3_ = new AdditionGenerator(3/*operands*/, 3 /*digits*/, false);
		AdditionGenerator additionGenerator_4X3_ = new AdditionGenerator(4/*operands*/, 3 /*digits*/, false);
		
		SubstractionGenerator substractionGenerator_1_ = new SubstractionGenerator(1 /*digits*/, false);
		SubstractionGenerator substractionGenerator_2_ = new SubstractionGenerator(2 /*digits*/, false);
		SubstractionGenerator substractionGenerator_3_ = new SubstractionGenerator(3 /*digits*/, false);
		
		MultiplicationGenerator multiplicationGenerator_2 = new MultiplicationGenerator(2 /*digits*/, 1, true);
		MultiplicationGenerator multiplicationGenerator_3 = new MultiplicationGenerator(3 /*digits*/, 1, true);
		
		DivisionGenerator divisionGenerator_2 = new DivisionGenerator(2 /*digits*/, 1, true);
		DivisionGenerator divisionGenerator_3 = new DivisionGenerator(3 /*digits*/, 1, true);
		DivisionGenerator divisionGenerator_4 = new DivisionGenerator(4 /*digits*/, 1, true);
		
		ProblemPrinter printer = new ProblemPrinter();
		
/*		// All 2x1 additions
		for (int i = 0; i< 20; i++) {	
			printer.writeHeader();
			printer.writeBody(additionGenerator_2X1_, 12problemAlongX, 5problemAlongY);		
			
			printer.writeFooter();
		}*/

		// All 2x1 additions
        for (int i = 0; i< 100; i++) {   
            printer.writeHeader();
            printer.writeBody(additionGenerator_2X1_, 12/*problemAlongX*/, 4/*problemAlongY*/);     
            printer.writeBody(substractionGenerator_1_ , 12/*problemAlongX*/, 1/*problemAlongY*/);  
            printer.writeFooter();
        }
        
//		for (int i = 0; i< 10; i++) {	
//			printer.writeHeader();
//			printer.writeBody(additionGenerator_2X1_, 12/*problemAlongX*/, 3/*problemAlongY*/);		
//			printer.writeBody(additionGenerator_3X2_, 12/*problemAlongX*/, 2/*problemAlongY*/);
//			
//			printer.writeFooter();
//		}
		
//		for (int i = 0; i< 5; i++) {    
//		    printer.writeHeader();
//		    printer.writeBody(additionGenerator_2X1_, 12/*problemAlongX*/, 3/*problemAlongY*/);             
//		    printer.writeBody(additionGenerator_3X2_, 12/*problemAlongX*/, 1/*problemAlongY*/);
//		    printer.writeBody(substractionGenerator_1_, 12/*problemAlongX*/, 2/*problemAlongY*/);
//		    printer.writeFooter();
//		}
		
//		for (int i = 0; i< 5; i++) {
//			printer.writeHeader();
//			printer.writeBody(additionGenerator_2X2_, 15/*problemAlongX*/, 5/*problemAlongY*/);		
//			printer.writeBody(substractionGenerator_2_, 15/*problemAlongX*/, 4/*problemAlongY*/);
//			printer.writeFooter();
//		}
		
		for (int i = 0; i< 5; i++) {			
//			printer.write(additionGenerator_4X4_, 1/*pages*/, 8/*problemAlongX*/, 1/*problemAlongY*/);		
//			printer.write(additionGenerator_5X4_, 1/*pages*/, 8/*problemAlongX*/, 2/*problemAlongY*/);
//
//			printer.write(substractionGenerator_4_, 1/*pages*/, 7/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.write(substractionGenerator_5_, 1/*pages*/, 6/*problemAlongX*/, 2/*problemAlongY*/);
//
//			printer.write(multiplicationGenerator, 1/*pages*/, 7/*problemAlongX*/, 3/*problemAlongY*/);
//			
		}
			
//		
//		for (int i = 0; i< 20; i++) {
//			printer.writeHeader();
//			printer.writeBody(substractionGenerator_5_, 5/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeBody(multiplicationGenerator, 6/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeHorizantalBody(divisionGenerator, 5/*problemAlongX*/, 2/*problemAlongY*/);
//			printer.writeFooter();
//		}
		
		
//		for (int i = 0; i< 30; i++) {
//			printer.writeHeader();
//						
//			printer.writeBody(additionGenerator_3X3_, 8/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeBody(substractionGenerator_2_, 9/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeBody(multiplicationGenerator_2, 9/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeBody(additionGenerator_3X3_, 8/*problemAlongX*/, 1/*problemAlongY*/);
//			
//						
//			printer.writeBody(substractionGenerator_3_, 8/*problemAlongX*/, 1/*problemAlongY*/);			
//			printer.writeBody(multiplicationGenerator_3, 8/*problemAlongX*/, 1/*problemAlongY*/);
//			
//			printer.writeBody(additionGenerator_4X3_, 8/*problemAlongX*/, 1/*problemAlongY*/);
//			
//			printer.writeHorizantalBody(divisionGenerator_2, 6/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeHorizantalBody(divisionGenerator_3, 5/*problemAlongX*/, 1/*problemAlongY*/);
//			printer.writeHorizantalBody(divisionGenerator_4, 5/*problemAlongX*/, 1/*problemAlongY*/);
//			
//			printer.writeFooter();
//		}
		
		printer.close();
	}
}
