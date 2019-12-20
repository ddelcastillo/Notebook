# UnionFinder

------------

### Use
Essentially, they are used to track **unions** between objects or sets of objects.

### Description
Abstractly, consider labeled boxes, each having some content of your choice. Now, lets say you merged the contents of some boxes. An UnionFinder would then help you keep track of *which* and *how many* boxes were merged. Furthermore, consider the action of merging two boxes as creating a single super-box that contains the two. Then, an UnionFinder would help you to efficiently check if two boxes *belong* to the same super-box.

------------

#### Differences between implementations
The following table displays a simple distinctions between the three different implementations.

| | BasicUnionFinder  | ExpandableBasicUnionFinder  | UnionFinder  |
|---|:-:|:-:|:-:|
| *Fixed size?*  | Yes | No | No |
| *Only numerical?* | Yes | Yes | No |

In greater detail:
- **BasicUnionFinder**: has *N* boxes labeled from 0 to *N*-1, for *N* a natural number.
- **ExpandableBasicUnionFinder**: has a variable number of numerically labeled boxes, for each label *L* being an integer.
- **UnionFinder**: has a variable number of *T* labeled boxes, for *T* any type.