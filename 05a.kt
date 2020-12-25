fun ok(x: Char, y: Char): Boolean {
    return x.toLowerCase() == y.toLowerCase() && x != y
}

fun main() {
    val a = readLine()!!
    val s = arrayListOf<Char>()
    for (c in a) {
        if (s.isNotEmpty() && ok(s.last(), c)) {
            s.removeAt(s.lastIndex)
        } else {
            s.add(c)
        }
    }
    println(s.size)
}
