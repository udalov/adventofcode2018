fun main() {
    var ans = 0
    while (true) {
        val line = readLine() ?: break
        val x = line.substring(1).toInt()
        if (line[0] == '+') ans += x else ans -= x
    }
    println(ans)
}
