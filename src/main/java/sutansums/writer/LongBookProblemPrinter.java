package sutansums.writer;

import sutansums.printer.PdfPrinter;


public class LongBookProblemPrinter extends AbstractProblemPrinter {

    /**
     * <pre>
     *  
     * Notepad 
     *   Page Size : Letter 
     *   Page set up : Left = 1"; Right = 1"; Top = 0.75"; Bottom = 0.75" 
     *   Font : Courier New + Bold, Size 16 
     *   COLUMNS = 46, ROWS = 42
     * </pre>
     */
    private static final int COLUMNS = 46;
    private static final int ROWS = 42;

    public LongBookProblemPrinter(boolean isMarkDown) {
        super(isMarkDown, PdfPrinter.longPagePdfPrinter());
    }

    protected String[] getHeaderLines() {
        String[] header = new String[2];

        int i = 0;
        /* ............1234567890123456789012345678901234567890123456789012345678901234567890 */
        header[i++] = "Name :                       Date :           ";
        header[i++] = "Start Time :             End Time :           ";

        return header;
    }

    protected String[] getFooterLines() {
        String[] footerLines = new String[1];

        // ...............1234567890123456789012345678901234567890123456789012345678901234567890
        footerLines[0] = "Corrects:        Wrongs:        Sign:         ";

        return footerLines;
    }

    protected int getColumns() {
        return COLUMNS;
    }

}
