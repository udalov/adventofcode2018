fun main() {
    val lines = mutableListOf<String>()
    while (true) {
        val line = readLine() ?: break
        lines.add(line)
    }

    val s = mutableSetOf<Int>()
    var ans = 0
    while (true) {
        for (line in lines) {
            val x = line.substring(1).toInt()
            if (line[0] == '+') ans += x else ans -= x
            if (!s.add(ans)) {
                println(ans)
                return
            }
        }
    }
}
