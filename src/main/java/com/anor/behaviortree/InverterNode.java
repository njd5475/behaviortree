package com.anor.behaviortree;

public class InverterNode<Context extends Object> extends SingleChild<Context> {

	public InverterNode(Node<Context> child) {
		super(child);
	}
	
	@Override
	public NodeStatus process(Context w) {
		NodeStatus ret = child.process(w);
		if(failed(ret)) {
			return NodeStatus.SUCCESS;
		}else if(success(ret)) {
			return NodeStatus.FAILURE;
		}
		
		return ret;
	}

	
}
