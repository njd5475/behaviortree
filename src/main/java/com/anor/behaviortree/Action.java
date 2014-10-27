package com.anor.behaviortree;

public interface Action<Context extends Object> {

	public NodeStatus perform(Context w);
	
}
