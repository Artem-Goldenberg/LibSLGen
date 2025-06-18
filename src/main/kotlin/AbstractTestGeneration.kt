package org.example

import org.jetbrains.research.libsl.nodes.Shift
import org.jetbrains.research.libsl.nodes.State
import kotlin.random.Random

typealias Path = ConsList<TransitionGraph.Edge>
typealias Edge = TransitionGraph.Edge

fun TransitionGraph.randomWalkFrom(node: State, random: Random): ConsList<TransitionGraph.Edge> {
    val finishPriority = (Priority.MAX - Priority.MIN) / 10
    val edges = transitions[node]!!
    val commonPriority = edges.sumOf { it.priority.value }
    val totalPriority = commonPriority + commonPriority / Priority.FINISH_RATIO
    val randomPoint = random.nextDouble() * totalPriority
    var point = 0
    for (edge in edges) {
        point += edge.priority.value
        if (point >= randomPoint) {
            val path = randomWalkFrom(edge.to, random)
            return ConsList.Cons(edge, path)
        }
    }
    return ConsList.Nil
}

fun TransitionGraph.augmentWithRandomWalks(paths: List<Path>, radnom: Random): List<Path> = paths.map { path ->
    when (val node = path.getLast()) {
        null -> path
        else -> {
            val walk = this.randomWalkFrom(node.to, radnom)
            ConsList.concat(path, walk)
        }
    }
}

enum class StateMark {
    NEW, VISITING
}

fun TransitionGraph.dfs(marks: MutableMap<State, StateMark>, node: State): List<Path> {
    marks.computeIfAbsent(node) { StateMark.NEW }
    if (marks[node]!! == StateMark.VISITING) {
        return listOf(ConsList.Nil)
    }
    marks[node] = StateMark.VISITING;
    val result = mutableListOf<Path>()
    for (edge in transitions[node]!!) {
        val tail = this.dfs(marks,edge.to)
        result += tail.map { ConsList.Cons(edge, it) }
    }
    marks[node] = StateMark.NEW
    return result
}
