package org.example

import org.jetbrains.research.libsl.nodes.Function
import org.jetbrains.research.libsl.nodes.IntegerLiteral
import org.jetbrains.research.libsl.nodes.State
import org.jetbrains.research.libsl.nodes.Shift
import kotlin.random.Random

data class TransitionGraph(
    val states: List<State>,
    val shifts: List<Shift>,
) {
    val random = Random(8586)
    data class Edge(val from: State, val to: State, val func: Function, val priority: Priority)

    val transitions: Map<State, List<Edge>>
    init {
        val map = mutableMapOf<State, MutableList<Edge>>()
        for (shift in shifts) {
            if (!states.contains(shift.from)) {
                println("Warning: shift ${quickString(shift)} goes from unknown state, skipping")
                continue
            }
            if (!states.contains(shift.to)) {
                println("Warning: shift ${quickString(shift)} leads to unknown state, skipping")
                continue
            }
            for (funcRef in shift.functions) {
                val func = funcRef.resolve()
                if (func == null) {
                    println("Warning: unresolved function: $funcRef, skipping")
                    continue
                }
                map.computeIfAbsent(shift.from) { mutableListOf() }
                map[shift.from]!!.add(
                    Edge(
                        shift.from,
                        shift.to,
                        func,
                        getPriorityOf(func)
                    )
                )
            }
        }
        this.transitions = map
    }

    private fun getPriorityOf(func: Function): Priority {
        val anno = func.annotationUsages.firstOrNull { it.annotationReference.name == "Priority" }
        if (anno == null) {
            return Priority.default
        }
        val priority = anno.arguments.firstOrNull()
        if (priority == null) {
            println("Warning: Priority annotation must have one argument, skipping")
            return Priority.default
        }
        val priorityValue: Int
        when (val expr = priority.value) {
            is IntegerLiteral -> priorityValue = expr.value.toInt()
            else -> {
                println("Warning: Priority annotation must have an integer literal as an argument, skipping")
                return Priority.default
            }
        }
        if (priorityValue < Priority.MIN || priorityValue > Priority.MAX) {
            println(
                "Warning: Priority $priorityValue is not in the range: ${Priority.MIN} ... ${Priority.MAX}, skipping"
            )
            return Priority.default
        }
        return Priority(priorityValue)
    }

//    fun toSwitchGraph(): Graph<Edge> {
//        val edges = mutableMapOf<Edge, MutableList<Edge>>()
//        for (state in states) {
//            for (shift in transitions[state]!!) {
//                for (nextShift in transitions[shift.to]!!) {
//                    edges.computeIfAbsent(shift) { mutableListOf() }
//                    edges[shift]!!.add(nextShift)
//                }
//            }
//        }
//        return graphOf(edges)
//    }

}



