package util

/**
 * Returns true if this range overlaps with the other range.
 * @param other The other range to check.
 * @return True if the ranges overlap, false otherwise.
 */
infix fun <T: Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>): Boolean =
    this.start <= other.endInclusive && other.start <= this.endInclusive

/**
 * Returns true if this range fully contains the other range.
 * @param other The other range to check.
 * @return True if this range contains the other range, false otherwise.
 */
infix fun <T: Comparable<T>> ClosedRange<T>.containsRange(other: ClosedRange<T>): Boolean = this.start <= other.start && this.endInclusive >= other.endInclusive

/**
 * Returns true if this range is adjacent to the other range.
 * @param other The other range to check.
 * @return True if the ranges are adjacent, false otherwise.
 */
infix fun IntRange.adjoint(other: IntRange): Boolean = this.last + 1 == other.first || other.last + 1 == this.first

/**
 * Returns true if this range is adjacent to the other range.
 * @param other The other range to check.
 * @return True if the ranges are adjacent, false otherwise.
 */
infix fun LongRange.adjoint(other: LongRange): Boolean = this.last + 1 == other.first || other.last + 1 == this.first

/**
 * Merges a list of LongRanges into a single LongRange that spans from the minimum start to the maximum end.
 * @return A LongRange that covers all ranges in the list.
 */
fun Collection<LongRange>.merge(): LongRange = this.minOf { r -> r.first }..this.maxOf { r -> r.last }
