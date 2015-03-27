BehaviorTree
============

Simple BehaviorTree implementation with a few different types of nodes.

Nodes
-
* Composite.InOrderSequence
* Composite.RandomSequence
* SelectorNode
* LeafNode
* Inverter
* RunningNode
* SuccessNode
* FailNode

Conditions
-
* Condition
* NegateCondition 

Context Support
-
A context class is passed through all nodes when a `performAction()` method is called