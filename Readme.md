I. File list
------------
node_info.java				Interface
weighted_grpah.java			Interface
WGraph_DS.java				weighted_graph implementation + node_info as an internal class
weighted_graph_algorithms	Interface
WGraph_Algo.java			graph_algorithms implementation
WGraph_DSTest.java			Test
WGraph_AlgoTest.java		Test
Readme.md					This file

In this file, I will explain:
- Why I chose the data structures I used in this project.
- How I implemented the interfaces.
- Which algorithms I used.

II. Data structures
-------------------

A. HashMap
	I used it in order to search, find, add, connect and remove (Basically I used it almost in every function).
	In addition, I used Hashmap inside an Hashmap in order to map between nodes and their edges.
	This data structure helped me to do all those functions with the best time complexity and it made the work much cleaner and easier.
	Also, after a long search this is almost the only (if not the only) data structure that can get time complexity O(1) in part of the functions.


III. Implementations
--------------------

A. WGraph_DS
	This class implements two interfaces:
	NodeInfo - This class defines the node and the values it has. Each node will receive a unique key and its values.
	WGraph_DS - defines the graph.
	It has hashmap in order to search, add, remove nodes.
	It has Hashmap inside an Hashmap in order to map between nodes and connect edges between them.
	
	
B. WGraph_Algo
	This class defines all the algorithms used in the graph.
	It has hashmap in order to go through all nodes in the graph and to set the parent for every node.
	In this class, there is a function for saving and loading the graph.
	Also, I implemented Dijkstra algorithm and I will explain about it below.

IV. Algorithms
--------------

A. Dijkstra
	Dijkstra algorithm is an algorithm for finding the shortest paths between nodes in a graph.
	For each node, it will be marked whether it has been visited or not and what is its distance from the source - which we will keep in "tag".
	At first all the vertices are marked as not visited, and their distance is defined as infinity.
	In the algorithm loop - as long as there are vertices that were not visited:
		- Marking the node as visited (X).
		- each neighbor of that node (Y) (that has not been visited yet), will be updated to the minimal value of (dist(y) + edge(X, Y)).
	After all that, picking the node which has the shortest distance.
	
V. Tests
----------

A. I used junit 5 for the tests, there are two tests in this project, one for each implementation.

VI. Expected Bottlenecks
-----------------------

As expected, most of the difficulties were in Graph_Algo.

A. Dijkstra
	There are a lot of Pseudocodes out there but implementing it in this graph was a challenge.


VII. Real Bottlenecks
--------------------

A. Dijkstra
	As expected, it was difficult to implement and not get lost in the way. After finding the right data structures it was easier.
	The priority queue makes the algorithm much faster.