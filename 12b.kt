fun main() {
    // val initial = "#..#.#..##......###...###"
    val initial = "#.#..#..###.###.#..###.#####...########.#...#####...##.#....#.####.#.#..#..#.#..###...#..#.#....##."
    val d = generateSequence { readLine() }.toList().map { line ->
        val (x, y) = line.split(" => ")
        x to y
    }.toMap()

    val n = 2000
    var b = BooleanArray(n)
    var bt = BooleanArray(n)
    for (i in initial.indices) {
        if (initial[i] == '#') b[i + n / 2] = true
    }

    var cur = 0
    for (iter in 1..600) {
        for (i in 2 until (n - 2)) {
            var s = ""
            for (j in -2..2) {
                s += if (b[i + j]) '#' else '.'
            }
            bt[i] = d[s] == "#"
        }
        var tmp = bt
        bt = b
        b = tmp

        var new = b.indices.sumBy { i -> if (b[i]) i - n/2 else 0 }
        println("$iter $new (d=${new-cur})")
        cur = new
    }

    val ans = cur + (50000000000L - 600) * 73

    println(ans)
}
