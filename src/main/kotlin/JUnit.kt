package org.example

import org.jetbrains.research.libsl.LibSL
import org.jetbrains.research.libsl.nodes.StateKind
import java.io.File
import kotlin.random.Random

fun main(args: Array<String>) {
    require(args.size == 1) { "Expecting a single argument: LibSL filepath" }
    val libsl = LibSL("")
    val spec = libsl.loadFromPath(args[0])

    val random = Random(342023)

    for (automaton in spec.automata) {
        val graph = TransitionGraph(automaton.states, automaton.shifts)
        val initNode = graph.states.firstOrNull { it.kind == StateKind.INIT } ?: graph.states[0]
        val paths = graph.dfs(mutableMapOf(), initNode)
        val randomPaths = graph.augmentWithRandomWalks(paths, random)
        println("The ${automaton.name} automaton paths:\n")
        println(quickString(randomPaths))
        println(" ---------------------------------------------- ")
        val testGen = JUnitTestGenerator(random, automaton)
        val testClass = testGen.junitTestClassFor(randomPaths)
        val filename = "${automaton.name}Test.java"
        File(filename).writeText(testClass)
        println("Written to $filename")
        println(" ============================================= ")
    }
}