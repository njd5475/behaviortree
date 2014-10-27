package com.anor.behaviortree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public abstract class Composite<Context extends Object> extends Node<Context> {

	private List<Node<Context>> children;
	
	public Composite() {
		children = new LinkedList<Node<Context>>();
	}
	
	public final void addChild(Node node) {
		children.add(node);
	}
	
	protected final List<Node<Context>> getChildren() {
		return children;
	}
	
	public static class InOrderSequence<Context extends Object> extends Composite<Context> {

		@Override
		public final NodeStatus process(Context Context) {
			NodeStatus toRet = NodeStatus.FAILURE;
			for(Node<Context> child : getChildren()) {
				toRet = child.process(Context);
				if(failed(toRet)) {
					return toRet;
				}
			}
			return toRet;
		}
	}
	
	public static class RandomSequence<Context extends Object> extends Composite<Context> {
		
		@Override
		public NodeStatus process(Context Context) {
			// maintain the original order
			List<Node<Context>> children = new ArrayList<Node<Context>>(getChildren());
			
			Collections.shuffle(getChildren());
			
			NodeStatus toRet = NodeStatus.FAILURE;
			for(Node<Context> child : getChildren()) {
				toRet = child.process(Context);
				if(failed(toRet) || running(toRet)) {
					return toRet;
				}
			}
			return toRet;
		}
		
	}
}


