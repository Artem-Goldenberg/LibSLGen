package org.example

import org.jetbrains.research.libsl.nodes.Shift
import org.jetbrains.research.libsl.nodes.State
import org.jetbrains.research.libsl.type.*

fun quickString(shift: Shift): String {
    return "${shift.from.name} -> ${shift.to.name}"
}

fun quickString(edge: TransitionGraph.Edge): String {
    return "${edge.from.name} --${edge.func.name}> ${edge.to.name}"
}

fun quickString(state: State): String {
    return state.name
}

fun quickString(path: ConsList<TransitionGraph.Edge>): String {
    return path.joinToString { quickString(it) }
}

fun quickString(paths: List<ConsList<TransitionGraph.Edge>>): String {
    return paths.joinToString("\n") { quickString(it) }
}
