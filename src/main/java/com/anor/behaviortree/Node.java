package com.anor.behaviortree;

public abstract class Node<Context extends Object> {

	public abstract NodeStatus process(Context w);
	
	public static boolean failed(NodeStatus s) {
		return s == NodeStatus.FAILURE;
	}
	
	public static boolean success(NodeStatus s) {
		return s == NodeStatus.SUCCESS;
	}
	
	public static boolean running(NodeStatus s) {
		return s == NodeStatus.RUNNING;
	}
}
