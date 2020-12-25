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

    repeat(20) {
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
    }

    var ans = 0
    for (i in b.indices) if (b[i]) ans += (i - n/2)

    println(ans)
}
