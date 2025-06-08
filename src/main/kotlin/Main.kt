package org.example

import org.jetbrains.research.libsl.LibSL

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name = "Kotlin"

    val libSLDirectory = LibSL("src/test/resources")
    val libSLFile = libSLDirectory.loadFromFileName("simple.lsl")

    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println(libSLFile)

}