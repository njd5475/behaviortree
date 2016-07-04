package com.anor.behaviortree;

public class FailNode<Context extends Object> extends Node<Context> {

	@Override
	public NodeStatus process(Context w) {
		return NodeStatus.FAILURE;
	}

}
