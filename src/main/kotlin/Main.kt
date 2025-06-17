package org.example

import org.jetbrains.research.libsl.LibSL
import org.jetbrains.research.libsl.nodes.Automaton
import org.jetbrains.research.libsl.nodes.Shift
import org.jetbrains.research.libsl.nodes.State
import org.jetbrains.research.libsl.nodes.StateKind
import kotlin.collections.ArrayDeque

fun main() {
    val libSLDirectory = LibSL("src/test/resources")
    val libSLFile = libSLDirectory.loadFromFileName("Socket.lsl")

    val automatas = libSLFile.automata

    for (automata in automatas) {
        val graph = TransitionGraph(automata.states, automata.shifts)
        val initNode = graph.states.firstOrNull { it.kind == StateKind.INIT } ?: graph.states[0]
        val paths = graph.dfs(mutableMapOf(), initNode)
        val randomPaths = graph.augmentWithRandomWalks(paths)
        println("The ${automata.name} automaton paths:\n")
        println(quickString(randomPaths))
        println(" ============================================= ")
    }
}
