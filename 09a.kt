fun main() {
    val n = 464
    val l = 70918

    var player = 0
    var cur = 0
    val a = arrayListOf(0)
    val score = IntArray(n) { 0 }
    for (turn in 1..l) {
        if (turn % 23 == 0) {
            score[player] += turn
            val i = (cur - 7 + a.size) % a.size
            score[player] += a[i]
            a.removeAt(i)
            cur = i
        } else {
            val i = (cur + 1) % a.size + 1
            a.add(i, turn)
            cur = i
        }
        // println(a.joinToString(" "))
        // println("current = $cur")
        player = (player + 1) % n
    }

    println(score.max())
}
