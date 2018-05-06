package sutansums.problem.conversion.common;

public enum NullUnit implements IUnits {
	EMPTY(""),

	;

	private final String unit;

	NullUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String getUnitString() {
		return unit;
	}

}
