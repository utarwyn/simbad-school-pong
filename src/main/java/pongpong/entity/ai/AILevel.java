package pongpong.entity.ai;

/**
 * Inspired from Code inComplete website.
 */
public enum AILevel {

	LOSING_8 ( .2, .004),
	LOSING_7 ( .3, .005),
	LOSING_6 ( .4, .006),
	LOSING_5 ( .5, .007),
	LOSING_4 ( .6, .008),
	LOSING_3 ( .7, .009),
	LOSING_2 ( .8, .010),
	LOSING_1 ( .9, .011),
	TIE      (1.0, .012),
	WINNING_1(1.1, .013),
	WINNING_2(1.2, .014),
	WINNING_3(1.3, .015),
	WINNING_4(1.4, .016),
	WINNING_5(1.5, .017),
	WINNING_6(1.6, .018),
	WINNING_7(1.7, .019),
	WINNING_8(1.8, .020);


	private double reaction;

	private double error;

	AILevel(double reaction, double error) {
		this.reaction = reaction;
		this.error = error;
	}

	public double getReaction() {
		return this.reaction;
	}

	public double getError() {
		return this.error;
	}

	public AILevel getPrev() {
		int idx = this.getIndex();

		if (idx - 1 >= 0)
			return AILevel.values()[idx - 1];

		return this;
	}

	public AILevel getNext() {
		int idx = this.getIndex();

		if (AILevel.values().length > idx + 1)
			return AILevel.values()[idx + 1];

		return this;
	}

	private int getIndex() {
		int idx = 0;

		for (AILevel level : AILevel.values()) {
			if (this.equals(level))
				break;

			idx++;
		}

		return idx;
	}

	public static AILevel getDefault() {
		return AILevel.TIE;
	}

}
