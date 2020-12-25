fun go(a: List<Int>, i: Int): Pair<Int, Int> {
    val c = a[i]
    val m = a[i + 1]
    var sum = 0
    var j = i + 2
    for (k in 0 until c) {
        val (inc, newJ) = go(a, j)
        sum += inc
        j = newJ
    }
    for (k in 0 until m) {
        sum += a[j + k]
    }
    return Pair(sum, j + m)
}

fun main() {
    val a = readLine()!!.split(" ").map(String::toInt)
    println(go(a, 0).first)
}
