package sutansums.problem.conversion.distance;

import sutansums.problem.conversion.common.IUnits;

public enum DistanceUnits implements IUnits {
	KM("km"),

	M("m"),

	CM("cm"),

	MM("mm"),

	;

	private final String unit;

	DistanceUnits(String unit) {
		this.unit = unit;
	}

	@Override
	public String getUnitString() {
		return unit;
	}

}
