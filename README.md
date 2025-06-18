# LibSLGen

A small research project, trying to generate tests based on the [LibSL](https://github.com/vpa-research/libsl-parser) language 
specifications.

There are two files with main functions in this project, both functions
accept a path to the `.lsl` file.

### `Main.kt` 

Just prints the abstract paths generated based on the provided specification file.

Abstract test paths are derived separately for each automaton by converting it into a simple finite state machine (FSM).
Basic paths are gained for each FSM by traversing it in the depth-first style,
stepping through each cycle only once. These basic paths are then appended with random walks
from the end point. 

You can control the probability of each step of these walks by adding a special `@Priority(value)`
function annotations, where `value` should be from `0` to `100`. The higher the priority, the most likely
it will be chosen as the next step of the random walk.

### `JUnit.kt` 

Creates `.java` test file for each automaton in the source `.lsl` file.

To add these generated tests to your java project, you need to provide functions of pattern:
`boolean is<state>() { ... }` where `<state>` refers to the name of the state in the finite automaton.

Also, test generation might fail if you automaton functions use types, values of which are unknown.
In this case, you can use the `@Values(<val1>, <val2>, ...)` annotation for each function parameter.
You can provide however many desired values into them, they will be picked at random when the value
for this parameter is needed.

