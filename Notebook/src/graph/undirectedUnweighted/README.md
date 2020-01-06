# UndirectedUnweightedGraph

------------

### Use
Used to represent **objects** (vertices) and their **unions** (edges). These unions have no weight or direction.

### Description
An *undirected* graph models unions between objects without these having any particular orientation or precedence. Analogously, the undirected union of two objects may be thought of as a bridge, where it is possible to go interchangeably from one object to the other. An undirected and *unweighted* graph models these unions without any particular attribute or value assigned to them. Continuing the aforementioned analogy, this may be thought of as the bridges all being the same length, meaning their dimensions hold no relevancy to the union.

------------

#### Differences between implementations
*Basic* graphs are numerical and have a fixed size. The others can be of any type and are expandable. 

In greater detail:
- **BasicUndirectedUnweightedGraph**: has *N* vertices labeled from 0 to *N*-1, for *N* a natural number. Has an adjacency list representation.
- **BasicUndirectedUnweightedCCGraph**: extends from *BasicUndirectedUnweightedGraph*. Tracks connected components by means of an UnionFinder.
- **BasicUndirectedUnweightedAMGraph**: has *N* vertices labeled from 0 to *N*-1, for *N* a natural number. Has an adjacency matrix representation.
- **BasicUndirectedUnweightedAMCCGraph**: extends from *BasicUndirectedUnweightedAMGraph*. Tracks connected components by means of an UnionFinder.
- **UndirectedUnweightedGraph**: has a variable number of *T* labeled vertices, for *T* any type. Has an adjacency list representation.
- **UndirectedUnweightedCCGraph**: extends from *UndirectedUnweightedGraph*. Tracks connected components by means of an UnionFinder.