package org.example

sealed class ConsList<out T>: Iterable<T> {
    object Nil : ConsList<Nothing>() {
        override fun iterator() = object : Iterator<Nothing> {
            override fun hasNext() = false
            override fun next() = throw NoSuchElementException()
        }
    }

    data class Cons<out T>(val element: T, val rest: ConsList<T>): ConsList<T>() {
        override fun iterator() = iterator {
            yield(element)
            yieldAll(rest)
        }
    }

    companion object {
        fun <T> concat(a: ConsList<T>, b: ConsList<T>): ConsList<T> {
            return when (a) {
                is Nil -> b
                is Cons -> Cons(a.element, concat(a.rest, b))
            }
        }
    }

    fun getLast(): T? {
        return when (this) {
            is Cons -> {
                if (this.rest is Nil) this.element
                else this.rest.getLast()
            }
            is Nil -> null
        }
    }
}