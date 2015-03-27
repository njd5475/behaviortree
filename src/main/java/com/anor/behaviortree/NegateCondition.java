package com.anor.behaviortree;

public class NegateCondition<Context extends Object> implements Condition<Context> {

	private Condition<Context>	condition;

	public NegateCondition(Condition<Context> condition) {
		this.condition = condition;
	}

	@Override
	public boolean pass(Context w) {
		return !condition.pass(w);
	}

	public static <T> NegateCondition<T> negate(Condition<T> conditionToNegate) {
		return new NegateCondition<T>(conditionToNegate);
	}
}
