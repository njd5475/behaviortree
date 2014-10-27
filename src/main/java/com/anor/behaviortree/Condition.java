package com.anor.behaviortree;

public interface Condition<Context extends Object> {

	public boolean pass(Context w);
	
}
