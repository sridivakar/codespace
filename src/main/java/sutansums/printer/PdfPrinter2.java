package sutansums.printer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * courtesy :
 * https://memorynotfound.com/add-watermark-to-pdf-document-using-itext
 * -and-java/
 * 
 */
public class PdfPrinter2 {

	private static final int HALF_INCH = 36;

	private final Font font;

	private Document document;
	private PdfWriter writer;

	public PdfPrinter2(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom,
			Font font, String outputPdfFileName) throws DocumentException, FileNotFoundException, IOException {
		// margin units are in typographic point , where one typographic point = 1/72 of
		// an inch.
		this.font = font;

		this.document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
		this.writer = PdfWriter.getInstance(document, new FileOutputStream(outputPdfFileName));
		document.open();
	}

	public static PdfPrinter a4PdfPrinter() {
		return new PdfPrinter(PageSize.A4, HALF_INCH, HALF_INCH, HALF_INCH, HALF_INCH,
				new Font(FontFamily.COURIER, 16, Font.BOLD));
	}

	public static PdfPrinter a5PdfPrinter() {
		return new PdfPrinter(PageSize.A5, HALF_INCH, HALF_INCH, HALF_INCH, HALF_INCH,
				new Font(FontFamily.COURIER, 14, Font.BOLD));
	}

	public PdfPrinter2 withHeaderFooterLines(List<String> headerLines, List<String> footerLines) {
		writer.setPageEvent(new HeaderFooterPageEvent(headerLines, footerLines));
		return this;
	}

	public PdfPrinter2 withWaterMarkLines(List<String> waterMarkLines) {
		writer.setPageEvent(new WatermarkPageEvent(waterMarkLines));
		return this;
	}

	public PdfPrinter2 addLine(List<String> lines) {
		this.bodyLines.addAll(lines);
		return this;
	}

	public PdfPrinter2 close() {
		document.close();
		return this.
	}

	public void print(PageBuilder pageBuilder)
			throws DocumentException, FileNotFoundException, IOException {

		// write to document
		document.open();

		Paragraph paragraph = new Paragraph();
		paragraph.setFont(font);
		for (String line : pageBuilder.bodyLines) {
			paragraph.add(new Chunk(line));
			paragraph.add(Chunk.NEWLINE);
		}
		document.add(paragraph);

		document.newPage();

	}

	static class HeaderFooterPageEvent extends PdfPageEventHelper {
		private static final Font ffont = new Font(Font.FontFamily.UNDEFINED, 9, Font.ITALIC);
		private List<String> headerLines = new ArrayList<>();
		private List<String> footerLines = new ArrayList<>();

		HeaderFooterPageEvent(List<String> headerLines, List<String> footerLines) {
			this.headerLines.addAll(headerLines);
			this.footerLines.addAll(footerLines);
		}

		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte content = writer.getDirectContent();

			for (String line : headerLines) {
				Phrase header = new Phrase(line, ffont);
				ColumnText.showTextAligned(content, Element.ALIGN_CENTER, header,
						(document.right() - document.left()) / 2 + document.leftMargin(),
						document.top() + 10, 0);

			}

			for (String line : footerLines) {
				Phrase footer = new Phrase(line, ffont);
				ColumnText.showTextAligned(content, Element.ALIGN_CENTER, footer,
						(document.right() - document.left()) / 2 + document.leftMargin(),
						document.top() + 10, 0);

			}
		}
	}

	static class WatermarkPageEvent extends PdfPageEventHelper {
		private static final Font FONT = new Font(Font.FontFamily.COURIER, 24, Font.BOLD, new GrayColor(0.92f));
		private List<String> waterMarkLines = new ArrayList<>();

		public WatermarkPageEvent(List<String> waterMarkLines) {
			this.waterMarkLines.addAll(waterMarkLines);
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			final int angle = writer.getPageNumber() % 2 == 1 ? 45 : -45;

			for (String line : waterMarkLines) {
				Phrase waterMark = new Phrase(line, FONT);
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, waterMark, 200f, 300,
						angle);
			}
		}
	}

}
