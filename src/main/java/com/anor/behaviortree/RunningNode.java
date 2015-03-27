package com.anor.behaviortree;

public class RunningNode<Context extends Object> extends Node<Context> {

	@Override
	public NodeStatus process(Context w) {
		return NodeStatus.RUNNING;
	}

}