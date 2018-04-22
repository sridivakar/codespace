package sutansums.printer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * courtesy : https://memorynotfound.com/add-watermark-to-pdf-document-using-itext -and-java/
 * 
 */
public class PdfPrinter {

	private static final int HALF_INCH = 36;

	private final Rectangle pageSize;
	private final float marginLeft;
	private final float marginRight;
	private final float marginTop;
	private final float marginBottom;

	public PdfPrinter(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom) {
		// margin units are in typographic point , where one typographic point = 1/72 of an inch.
		this.pageSize = pageSize;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
	}

	public static PdfPrinter longPagePdfPrinter() {
		return new PdfPrinter(PageSize.A4, HALF_INCH, HALF_INCH, HALF_INCH, HALF_INCH);
	}

	public static PdfPrinter shortPagePdfPrinter() {
		return new PdfPrinter(PageSize.A5, HALF_INCH, HALF_INCH, HALF_INCH, HALF_INCH);
	}

	public void print(String inputTxtFileName, String outputPdfFileName) {
		try (BufferedReader input = new BufferedReader(new FileReader(inputTxtFileName))) {
			Document document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
			Font font = new Font(FontFamily.COURIER, 14, Font.BOLD);

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPdfFileName));

			// watermark
			writer.setPageEvent(new WatermarkPageEvent());

			// write to document
			document.open();

			String line = input.readLine();
			Paragraph paragraph = new Paragraph();
			paragraph.setFont(font);
			while (line != null) {
				paragraph.add(new Chunk(line));
				paragraph.add(Chunk.NEWLINE);
				// document.newPage();
				// document.add(new Paragraph("Testing.", font));
				// document.newPage();
				line = input.readLine();
			}
			document.add(paragraph);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	static class WatermarkPageEvent extends PdfPageEventHelper {

		Font FONT = new Font(Font.FontFamily.COURIER, 24, Font.BOLD, new GrayColor(0.95f));

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			Phrase phrase = new Phrase("Stone House India Publications", FONT);
			int angle = writer.getPageNumber() % 2 == 1 ? 45 : -45;

			ColumnText
					.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, phrase, 200f, 300, angle);
		}
	}
}
