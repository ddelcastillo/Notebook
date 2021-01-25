# UndirectedUnweightedGraph

------------

### Use
Used to represent **objects** (vertices), and their **unions** (edges). These unions have no weight or direction.

### Description
An *undirected* graph models unions between objects without these having any particular orientation or precedence. Analogously, the undirected union of two objects may be thought of as a bridge, where it is possible to go interchangeably from one object to the other. An undirected and *unweighted* graph models these unions without any particular attribute or value assigned to them. Continuing the aforementioned analogy, this may be thought of as the bridges all being the same length, meaning their dimensions hold no relevancy to the union.

------------

#### Differences between implementations
*Basic* graphs are numerical and have a fixed size, and the others can be of any type and are expandable. Basic graphs have three implementations concerning it’s data structure: adjacency list (**AL**), adjacency matrix (**AM**), and a mixture of both (acronym omitted). Added to the data structure, a graph also tracks connected components if it contains the **CC** acronym.

In greater detail:
- **BasicUndirectedUnweightedALGraph**: has *N* vertices labeled from 0 to *N*-1, for *N* a natural number. Has an adjacency list implementation.
- **BasicUndirectedUnweightedALCCGraph**: extends from *BasicUndirectedUnweightedGraph*. Tracks connected components using an UnionFinder class.
- **BasicUndirectedUnweightedAMGraph**: has *N* vertices labeled from 0 to *N*-1, for *N* a natural number. Has an adjacency matrix implementation.
- **BasicUndirectedUnweightedAMCCGraph**: extends from *BasicUndirectedUnweightedAMGraph*. Tracks connected components using an UnionFinder.
- **BasicUndirectedUnweightedGraph**: has *N* vertices labeled from 0 to *N*-1, for *N* a natural number. Has both an adjacency list and an adjacency matrix implementation.
- **BasicUndirectedUnweightedCCGraph**: extends from *BasicUndirectedUnweightedGraph*. Tracks connected components using an UnionFinder.
- **UndirectedUnweightedGraph**: has a variable number of *T*-labeled vertices, for *T* any type. Has an adjacency list representation.
- **UndirectedUnweightedCCGraph**: extends from *UndirectedUnweightedGraph*. Tracks connected components by means of an expandable UnionFinder.

*Incoming features*: allow for a non-basic graph to generate a non-dynamic adjacency matrix based on the current state of the nodes, optimizing the `hasEdge` method. If a new edge is added between the existing nodes, it will be updated in the matrix. If a new vertex is added to the graph, it's edges will not be tracked in the adjacency matrix (only the previously existing vertex will). Given this situation, an `isUpdated` status will be added to track if the adjacency matrix is tracking all of the graph's vertex or if it requires updating.