package sutansums.writer;

import sutansums.printer.PdfPrinter;


public class ShortBookProblemPrinter extends AbstractProblemPrinter {

    /**
     * <pre>
     * 
     * Notepad 
     *  Page Size : A5
     *  Page set up : Left = 0.5"; Right = 0.5"; Top = 0.5"; Bottom = 0.5"
     *  Font : Courier New + Bold, Size 14
     *  COLUMNS = 30, ROWS = 27
     * </pre>
     */
    private static final int COLUMNS = 41;

    public ShortBookProblemPrinter(boolean isMarkDown) {
        super(isMarkDown, PdfPrinter.shortPagePdfPrinter());
    }

    protected String[] getHeaderLines() {
        String[] header = new String[2];

        int i = 0;
        /* ............1234567890123456789012345678901234567890123456789012345678901234567890 */
        header[i++] = "Name :                    Date :         ";
        header[i++] = "Start Time :          End Time :         ";

        return header;
    }

    protected String[] getFooterLines() {
        String[] footerLines = new String[1];
        // ...............1234567890123456789012345678901234567890123456789012345678901234567890
        footerLines[0] = "Corrects:     Wrongs:     Sign:          ";

        return footerLines;
    }

    protected int getColumns() {
        return COLUMNS;
    }

}
