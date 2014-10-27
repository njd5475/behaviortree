package com.anor.behaviortree;

public class SuccessNode<Context extends Object> extends Node<Context> {

	@Override
	public NodeStatus process(Context w) {
		return NodeStatus.SUCCESS;
	}

}
