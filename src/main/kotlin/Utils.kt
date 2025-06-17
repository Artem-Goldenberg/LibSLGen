package org.example

import org.jetbrains.research.libsl.nodes.Shift
import org.jetbrains.research.libsl.nodes.State
import org.jetbrains.research.libsl.type.*

fun getValueFor(t: Type) {
    val some = when (t) {
        is ArrayType -> TODO("some")
        is EnumType -> TODO()
        is GenericType -> TODO()
        is EnumLikeSemanticType -> TODO()
        is SimpleType -> TODO()
        is TypeAlias -> TODO()
        is ListType -> TODO()
        is MapType -> TODO()
        is NullType -> TODO()
        is PrimitiveType -> TODO()
        is RealType -> TODO()
        is StructuredType -> TODO()
    }
}

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
