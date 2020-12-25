fun go(a: List<Int>, i: Int): Pair<Int, Int> {
    val c = a[i]
    val m = a[i + 1]
    var sum = 0
    var j = i + 2
    val l = arrayListOf<Int>()
    for (k in 0 until c) {
        val (v, newJ) = go(a, j)
        l.add(v)
        j = newJ
    }
    for (k in 0 until m) {
        val index = a[j + k] - 1
        if (index in l.indices) {
            sum += l[index]
        } else if (c == 0) {
            sum += a[j + k]
        }
    }
    return Pair(sum, j + m)
}

fun main() {
    val a = readLine()!!.split(" ").map(String::toInt)
    println(go(a, 0).first)
}
