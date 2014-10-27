package com.anor.behaviortree;

public class LeafNode<Context extends Object> extends Node<Context> {

	private Action<Context>	action;

	public LeafNode(Action<Context> action) {
		this.action = action;
	}

	@Override
	public NodeStatus process(Context w) {
		return action.perform(w);
	}

}
