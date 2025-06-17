libsl "1.0.0";
library socket version "1.0.0" language "C";

include socket.h;

typealias Void = void;

annotation Take(times: int);

automaton Socket : int {
    initstate Alloced;
    state Bound, Listening, Connected;

    shift Alloced -> Bound by  bind;
    shift Bound -> Listening by listen(int);
    shift Listening -> Connected by conn;
    shift Connected -> Connected by [snd, rcv];

    shift Connected -> Bound by end;
    shift (Listening, Bound) -> Alloced by reset;

    @Take(10)
    fun bind(): void {

    }

    fun listen(param: int): Void {

    }

    fun conn() {}
    fun snd();
    fun rcv();
    fun end();
    fun reset();
}

fun bind(): void {

}

fun listen(param: int): Void {

}
