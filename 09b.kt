fun main() {
    val n = 464
    val l = 7091800

    var player = 0
    val a = java.util.LinkedList<Int>()
    a.add(0)
    var cur = a.listIterator()
    val score = LongArray(n) { 0L }
    for (turn in 1..l) {
        if (turn % 1000000 == 0) println(turn)
        if (turn % 23 == 0) {
            score[player] += turn.toLong()
            repeat(7) {
                if (cur.hasPrevious()) cur.previous()
                else cur = a.listIterator(a.size - 1)
            }
            score[player] += cur.next().toLong()
            cur.remove()
        } else {
            cur.next()
            if (!cur.hasNext()) cur = a.listIterator()
            cur.next()
            cur.add(turn)
            cur.previous()
        }
        // println(a.joinToString(" "))
        // println("current = ${cur.nextIndex()}")
        player = (player + 1) % n
    }

    println(score.max())
}
