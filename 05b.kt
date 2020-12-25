fun ok(x: Char, y: Char): Boolean {
    return x.toLowerCase() == y.toLowerCase() && x != y
}

fun go(a: String): Int {
    val s = arrayListOf<Char>()
    for (c in a) {
        if (s.isNotEmpty() && ok(s.last(), c)) {
            s.removeAt(s.lastIndex)
        } else {
            s.add(c)
        }
    }
    return s.size
}

fun main() {
    val a = readLine()!!
    var ans = Int.MAX_VALUE
    for (c in 'a'..'z') {
        ans = minOf(ans, go(a.filterNot { it.toLowerCase() == c }))
    }
    println(ans)
}
