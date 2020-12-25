fun main() {
    val l = generateSequence { readLine() }.toList()
    for (i in 0 until l.size) {
        for (j in 0 until i) {
            var d = 0
            for (k in 0 until l[i].length) {
                if (l[i][k] != l[j][k]) d++
            }
            if (d == 1) {
                var result = ""
                for (k in 0 until l[i].length) {
                    if (l[i][k] == l[j][k]) result += l[i][k]
                }
                println(result)
                return
            }
        }
    }
}
