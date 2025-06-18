package org.example

import org.jetbrains.research.libsl.nodes.*
import org.jetbrains.research.libsl.nodes.Function
import org.jetbrains.research.libsl.type.*
import java.util.Locale
import java.util.Locale.getDefault
import kotlin.random.Random
import kotlin.random.nextInt

class JUnitTestGenerator(val random: Random, var automaton: Automaton) {
     companion object {
         val header = """
        |import org.junit.jupiter.api.Test;
        |import static org.junit.jupiter.api.Assertions.*;
        """.trimMargin("|")
     }

    private val className = automaton.name.capitalize()
    private val varName = automaton.name.replaceFirstChar { it.lowercase(getDefault()) }

     fun String.capitalize(): String =
         replaceFirstChar {
             if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
         }

     fun junitTestClassFor(paths: List<Path>): String =
 """${header}

class ${className}Test {
    private $className $varName; 

    @BeforeEach
    void setUp() {
        $varName = new $className();
    }
${paths.mapIndexed { i, path -> junitTestFor(path, i + 1) }.joinToString("")}
}
"""

     fun junitTestFor(path: Path, num: Int): String {
         if (path.isEmpty()) return ""

         val calls = path.map { edge -> assertFor(edge) }
         val checkFunc = "is${path.first().from.name}"

         return """
    @Test
    void testPath${num}() {
        $checkFunc($varName);
${calls.joinToString("\n")}
    }
"""
     }

     fun assertFor(edge: Edge): String {
         val funcCall = random.callOf(edge.func, varName)
         val checkFunc = "is${edge.to.name}"
         return """
         |        $funcCall
         |        assertTrue($checkFunc($varName));
         """.trimMargin("|")
     }
 }

fun Random.callOf(func: Function, varName: String): String =
    func.args.map { arg ->
        val anno = arg.annotationUsages.firstOrNull {
            it.annotationReference.name == "Values"
        }
        if (anno != null) {
            require(anno.arguments.isNotEmpty()) {
                "@Values annotation must have at least one argument in $arg"
            }
            anno.arguments.map { it.value }.random(this).toString()
        } else {
            val type = arg.typeReference.resolve()
            require(type != null) { "Cannot resolve type for argument: $arg" }
            javaValueOf(type)
        }
    }.joinToString(", ", prefix = "$varName.${func.name}(", postfix = ");")

fun Random.javaValueOf(t: Type): String {
    return when (t) {
        is ArrayType -> {
            val type = t.generics.firstOrNull()?.resolve()
            require(type != null)
            val len = nextInt(0, 6)
            (0..<len).map {
                javaValueOf(type)
            }.joinToString(separator = ", ", prefix = "List.of(", postfix = ")")
        }
        is TypeAlias -> {
            val type = t.originalType.resolve()
            require(type != null)
            return javaValueOf(type)
        }
        is NullType -> "null"
        is PrimitiveType -> javaValueOf(t)
        else -> error("Cannot generate value of type: $t")
    }
}

fun Random.atomicValue(a: Atomic): String {
    require(a.value != null)
    return a.value.toString()
}

fun Random.javaValueOf(a: PrimitiveType): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    val value: Any = when (a) {
        is Int8Type -> this.nextInt(-10..<127)
        is Int16Type -> this.nextInt(-10..<1000)
        is Int32Type -> this.nextInt(-10..<1000)
        is Int64Type -> this.nextInt(-10..<1000)
        is UnsignedInt8Type -> this.nextInt(0..<255)
        is UnsignedInt16Type -> this.nextInt(0..<1000)
        is UnsignedInt32Type -> this.nextInt(0..<1000)
        is UnsignedInt64Type -> this.nextInt(0..<1000)
        is Float32Type -> this.nextFloat()
        is Float64Type -> this.nextDouble(-100.0, 100.0)
        is BoolType -> this.nextBoolean()
        is CharType -> allowedChars.random(this)
        is StringType -> (1..nextInt(10))
            .map { allowedChars.random(this) }
            .joinToString("")
        is AnyType -> "Anything"
        is LiteralType -> a.name
        else -> error("Unrecognized primitive type: $a")
    }
    return value.toString()
}
