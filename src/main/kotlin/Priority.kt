package org.example

data class Priority(val value: Int) {
    companion object {
        val default = Priority(60)
        const val MIN = 0
        const val MAX = 100
        const val FINISH_RATIO = 10
    }
}
