fun main() {
    val lines = generateSequence { readLine() }.toList()
    val g = mutableMapOf<Char, MutableList<Char>>()
    for (c in 'A'..'Z') g[c] = arrayListOf()
    val was = sortedSetOf<Char>()
    for (line in lines) {
        val ss = line.split(" ")
        val from = ss[1][0]
        val to = ss[7][0]
        was.add(from)
        was.add(to)
        g[from]!!.add(to)
    }

    while (true) {
        val x = ('A'..'Z').firstOrNull { i ->
            g[i]!!.isNotEmpty() && ('A'..'Z').none { j -> i in g[j]!! }
        } ?: break
        print(x)
        was.remove(x)
        g[x] = mutableListOf()
    }
    for (c in was) print(c)
    println()
}
