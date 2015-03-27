package com.anor.behaviortree;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;

import com.anor.behaviortree.Action;
import com.anor.behaviortree.Composite;
import com.anor.behaviortree.Condition;
import com.anor.behaviortree.FailNode;
import com.anor.behaviortree.InverterNode;
import com.anor.behaviortree.LeafNode;
import com.anor.behaviortree.Node;
import com.anor.behaviortree.NodeStatus;
import com.anor.behaviortree.SelectorNode;
import com.anor.behaviortree.SuccessNode;

public class BehaviorTreesTest {

	@Test
	public void testNegateCondition() {
		Condition mockCondition = mock(Condition.class);
		when(mockCondition.pass(any())).thenReturn(false, true);
		NegateCondition condition = NegateCondition.negate(mockCondition);
		assertTrue(condition.pass(new Object()));
		assertFalse(condition.pass(new Object()));
		
		
	}
	
	@Test
	public void testInOrderSequenceFailure() {
		Composite<Object> c = new Composite.InOrderSequence<Object>();
		
		SuccessNode<Object> mockNode = mock(SuccessNode.class);
		c.addChild(mockNode);
		c.addChild(new FailNode<Object>());
		c.addChild(mockNode);
		
		NodeStatus process = c.process(new Object());
		assertEquals(NodeStatus.FAILURE, process);
		verify(mockNode, times(1)).process(any());
	}
	
	@Test
	public void testInOrderSequenceSuccess() {
		Composite<Object> c = new Composite.InOrderSequence<Object>();
		
		c.addChild(new SuccessNode<Object>());
		c.addChild(new SuccessNode<Object>());
		c.addChild(new SuccessNode<Object>());
		
		NodeStatus process = c.process(new Object());
		assertEquals(NodeStatus.SUCCESS, process);
	}
	
	@Test
	public void testInOrderSequenceRunning() {
		Composite<Object> c = new Composite.InOrderSequence<Object>();
		
		SuccessNode<Object> mockNode = mock(SuccessNode.class);
		when(mockNode.process(any())).thenReturn(NodeStatus.SUCCESS);
		c.addChild(mockNode);
		c.addChild(new RunningNode<Object>());
		c.addChild(mockNode);
		
		NodeStatus process = c.process(new Object());
		verify(mockNode, times(1)).process(any());
		assertEquals(NodeStatus.RUNNING, process);
	}
	
	@Mock
	Node<Object> success;
	
	@Mock
	Node<Object> failure;
	
	@Test
	public void testRandomOrderSequence() {
		Object testObject = new Object();
		success = mock(SuccessNode.class);
		failure = mock(FailNode.class);
		Node<Object> running = mock(Node.class);
		when(success.process(testObject)).thenReturn(NodeStatus.SUCCESS);
		when(failure.process(testObject)).thenReturn(NodeStatus.FAILURE);
		when(running.process(testObject)).thenReturn(NodeStatus.RUNNING);
		
		Composite<Object> c = new Composite.RandomSequence<Object>();
		c.addChild(failure);
		c.addChild(success);
		c.addChild(running);
			
		NodeStatus process1 = c.process(testObject);
		boolean p = false;
		for(int i=100; i > 0; --i) {
			NodeStatus s = c.process(testObject);
			if(process1 != s) {
				p = true;
				break;
			}
		}
		assertTrue(p);
	}
	
	@Test
	public void testRandomOrderSequence2() {
		Object testObject = new Object();
		success = mock(SuccessNode.class);
		failure = mock(FailNode.class);
		Node<Object> running = mock(Node.class);
		when(success.process(testObject)).thenReturn(NodeStatus.SUCCESS);
		when(failure.process(testObject)).thenReturn(NodeStatus.SUCCESS);
		when(running.process(testObject)).thenReturn(NodeStatus.SUCCESS);
		
		Composite<Object> c = new Composite.RandomSequence<Object>();
		c.addChild(failure);
		c.addChild(success);
		c.addChild(running);
			
		NodeStatus process1 = c.process(testObject);
		boolean p = true;
		for(int i=100; i > 0; --i) {
			NodeStatus s = c.process(testObject);
			assertEquals(process1,s);
		}
		assertTrue(p);
	}

	@Test
	public void testCheckFunctions() {
		assertTrue(Node.failed(NodeStatus.FAILURE));
		assertFalse(Node.failed(NodeStatus.SUCCESS));
		assertFalse(Node.failed(NodeStatus.RUNNING));
		
		assertTrue(Node.success(NodeStatus.SUCCESS));
		assertFalse(Node.success(NodeStatus.FAILURE));
		assertFalse(Node.success(NodeStatus.RUNNING));
		
		assertTrue(Node.running(NodeStatus.RUNNING));
		assertFalse(Node.running(NodeStatus.SUCCESS));
		assertFalse(Node.running(NodeStatus.FAILURE));
	}
	
	@Test
	public void testInverter() {
		Object testObject = new Object();
		success = mock(SuccessNode.class);
		failure = mock(FailNode.class);
		Node<Object> running = mock(Node.class);
		when(running.process(testObject)).thenReturn(NodeStatus.RUNNING);
		when(success.process(testObject)).thenReturn(NodeStatus.SUCCESS);
		when(failure.process(testObject)).thenReturn(NodeStatus.FAILURE);
		
		InverterNode<Object> toTest = new InverterNode<Object>(success);
		assertEquals(success, toTest.getChild());
		assertTrue(Node.failed(toTest.process(testObject)));
		
		toTest = new InverterNode<Object>(failure);
		assertTrue(Node.success(toTest.process(testObject)));
		
		toTest = new InverterNode<Object>(running);
		assertTrue(Node.running(toTest.process(testObject)));
	}
	
	@Test
	public void testStatuses() {
		assertEquals(NodeStatus.SUCCESS, NodeStatus.valueOf("SUCCESS"));
		assertTrue(NodeStatus.SUCCESS.toString().equals("SUCCESS"));
		assertEquals(0, NodeStatus.SUCCESS.ordinal());
		assertTrue(NodeStatus.FAILURE.toString().equals("FAILURE"));
		assertEquals(1, NodeStatus.FAILURE.ordinal());
	}
	
	@Test
	public void testLeafNode() {
		Action<Object> mockAction = mock(Action.class);
		LeafNode<Object> leaf = new LeafNode(mockAction);
		Object Context = new Object();
		leaf.process(Context);
		verify(mockAction, times(1)).perform(Context);
	}
	
	@Test
	public void testSelector() {
		SelectorNode<Object> select = new SelectorNode<Object>();
		Condition<Object> first = mock(Condition.class);
		Condition<Object> second = mock(Condition.class);
		Condition<Object> third = mock(Condition.class);
		Node<Object> firstNode = mock(Node.class);
		Node<Object> secondNode = mock(Node.class);
		Node<Object> thirdNode = mock(Node.class);
		
		select.addChild(first, firstNode);
		select.addChild(second, secondNode);
		select.addChild(third, thirdNode);
		
		Object testContext = new Object();
		when(first.pass(testContext)).thenReturn(false);
		when(second.pass(testContext)).thenReturn(true);
		verify(third, never()).pass(testContext);
		
		select.process(testContext);
		
		when(first.pass(testContext)).thenReturn(false);
		when(second.pass(testContext)).thenReturn(false);
		when(third.pass(testContext)).thenReturn(false);
		
		assertEquals(NodeStatus.FAILURE, select.process(testContext));
		
	}
}