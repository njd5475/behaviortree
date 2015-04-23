BehaviorTree
============

Simple BehaviorTree implementation with a few different types of nodes.

Gradle
-
Add the following to your dependencies

`compile 'com.github.njd5475:behavior-trees:0.0.1'`

Maven
-
    <dependency>
      <groupId>com.github.njd5475</groupId>
      <artifactId>behavior-trees</artifactId>
      <version>0.0.1</version>
    </dependency>

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
