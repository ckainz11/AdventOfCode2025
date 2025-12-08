package util

/*------ pairwise ------*/

fun <T> List<T>.pairwise() = mapIndexed { index, first -> drop(index + 1).map { second -> first to second } }.flatten()
fun <T, R> List<T>.pairwise(transform: (T, T) -> R) =
    mapIndexed { index, first -> drop(index + 1).map { second -> transform(first, second) } }.flatten()

/*------ middle ------*/

fun <T> List<T>.middle(): T {
    if (size % 2 == 0) error("List must have an odd number of elements")
    return this[size / 2]
}

/*----- permutations -----*/

fun <T> List<T>.permutations(): List<List<T>> {
    return if (this.size == 1) listOf(this)
    else this.flatMap { i -> (this - i).permutations().map { listOf(i) + it } }
}

/*----- split -----*/

/**
 * Splits the list into sublists each time the predicate is true.
 * The element that matches the predicate is not included in the resulting sublists.
 * This may cause empty sublists in the result.
 */
fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val result = mutableListOf<MutableList<T>>()
    var group = mutableListOf<T>()
    for (item in this) {
        if (!predicate(item))
            group.add(item)
        else {
            result.add(group)
            group = mutableListOf()
        }
    }

    result.add(group)
    return result
}

/*----- subsets -----*/
fun <T> List<T>.subsetsOfSize(size: Int): List<List<T>> {
    if (size < 0) error("Size must be non-negative")
    if (size > this.size) return emptyList()
    if (size == 0) return listOf(emptyList())

    if (size == 1) return this.map { listOf(it) }

    val result = mutableListOf<List<T>>()
    for (i in this.indices) {
        val element = this[i]
        val remaining = this.drop(i + 1)
        val subsets = remaining.subsetsOfSize(size - 1)
        for (subset in subsets) {
            result.add(listOf(element) + subset)
        }
    }
    return result
}

/*----- String Joining -----*/

fun <T> Array<T>.join() = this.joinToString("")
fun <T> Array<T>.join(separator: String) = this.joinToString(separator)
fun CharArray.join() = this.joinToString("")
fun CharArray.join(separator: String) = this.joinToString(separator)
fun <T> Iterable<T>.join() = this.joinToString("")
fun <T> Iterable<T>.join(separator: String) = this.joinToString(separator)
