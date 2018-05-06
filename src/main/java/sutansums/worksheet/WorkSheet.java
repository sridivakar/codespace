package sutansums.worksheet;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import java.util.Collections;

public class WorkSheet {

	private final List<String> header = new ArrayList<>();
	private final List<String> body = new ArrayList<>();
	private final List<String> footer = new ArrayList<>();

	private final String waterMark;
	private final int columns;
	private final int rows;

	@Generated("SparkTools")
	private WorkSheet(Builder builder) {
		this.header.addAll(builder.header);
		this.body.addAll(builder.body);
		this.footer.addAll(builder.footer);
		this.waterMark = builder.waterMark;
		this.columns = builder.columns;
		this.rows = builder.rows;
	}

	public List<String> getHeader() {
		return header;
	}

	public List<String> getBody() {
		return body;
	}

	public List<String> getFooter() {
		return footer;
	}

	public String getWaterMark() {
		return waterMark;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private List<String> header = Collections.emptyList();
		private List<String> body = Collections.emptyList();
		private List<String> footer = Collections.emptyList();
		private String waterMark = "Stone House India Publications";
		private int columns;
		private int rows;

		private Builder() {
		}

		public Builder withHeader(List<String> header) {
			this.header = header;
			return this;
		}

		public Builder withBody(List<String> body) {
			this.body = body;
			return this;
		}

		public Builder withFooter(List<String> footer) {
			this.footer = footer;
			return this;
		}

		public Builder withWaterMark(String waterMark) {
			this.waterMark = waterMark;
			return this;
		}

		public Builder withColumns(int columns) {
			this.columns = columns;
			return this;
		}

		public Builder withRows(int rows) {
			this.rows = rows;
			return this;
		}

		public WorkSheet build() {
			return new WorkSheet(this);
		}
	}
}
