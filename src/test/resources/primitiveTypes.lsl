libsl "1.0.0";
library simple;

typealias Int = int32;
typealias Float = float32;
typealias Double = float64;
typealias V = *void;
typealias Char = char;

automaton A : Int {
    var i: V;
    var c: Char = 'x';

    fun f(param: Int): V {
    }

    fun sum(): Float {
        result = 0.1f + 0.2f;
    }

    fun sumAgain(): Double {
        result = 0.1 + 0.2;
    }

    fun newSum(): Float {
        result = 0.0f + 0.1f;
    }

    fun compareInt(): Int {
        result = 1073;
    }
}
