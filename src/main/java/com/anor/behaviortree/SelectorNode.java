package com.anor.behaviortree;

import java.util.HashMap;
import java.util.Map;

public final class SelectorNode<Context extends Object> extends Node<Context> {

	private Map<Condition<Context>, Node<Context>> children = new HashMap<Condition<Context>, Node<Context>>();

	public final void addChild(Condition<Context> condition, Node<Context> node) {
		children.put(condition, node);
	}
	
	@Override
	public NodeStatus process(Context w) {
		for(Map.Entry<Condition<Context>, Node<Context>> entry : children.entrySet()) {
			if(entry.getKey().pass(w)) {
				return entry.getValue().process(w);
			}
		}
		return NodeStatus.FAILURE;
	}

}
