package com.anor.behaviortree;

public abstract class SingleChild<Context extends Object> extends Node<Context> {

	protected Node<Context>	child;

	public SingleChild(Node<Context> child) {
		this.child = child;
	}
	
	public final Node<Context> getChild() {
		return child;
	}
	
}
