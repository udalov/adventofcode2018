fun addr(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] + it[b] }

fun addi(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] + b }

fun mulr(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] * it[b] }

fun muli(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] * b }

fun banr(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] and it[b] }

fun bani(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] and b }

fun borr(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] or it[b] }

fun bori(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] or b }

fun setr(r: List<Int>, a: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] }

fun seti(r: List<Int>, a: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = a }

fun gtir(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = if (a > it[b]) 1 else 0 }

fun gtri(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = if (it[a] > b) 1 else 0 }

fun gtrr(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = if (it[a] > it[b]) 1 else 0 }

fun eqir(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = if (a == it[b]) 1 else 0 }

fun eqri(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = if (it[a] == b) 1 else 0 }

fun eqrr(r: List<Int>, a: Int, b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = if (it[a] == it[b]) 1 else 0 }

fun main() {
    val lines = generateSequence { readLine() }.filterNot(String::isEmpty).toList()
    val beforeRegex = "Before:\\s*\\[(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\]".toRegex()
    val afterRegex = "After:\\s*\\[(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\]".toRegex()
    var ans = 0
    for (i in 0 until lines.size step 3) {
        val (a0s, b0s, c0s, d0s) = beforeRegex.matchEntire(lines[i])?.destructured ?: break
        val (_, a, b, c) = lines[i + 1].split(" ").map(String::toInt)
        val (a1s, b1s, c1s, d1s) = afterRegex.matchEntire(lines[i + 2])!!.destructured
        val r = listOf(a0s, b0s, c0s, d0s).map(String::toInt)
        val s = listOf(a1s, b1s, c1s, d1s).map(String::toInt)

        val z = BooleanArray(16)
        z[0] = addr(r, a, b, c) == s
        z[1] = addi(r, a, b, c) == s
        z[2] = mulr(r, a, b, c) == s
        z[3] = muli(r, a, b, c) == s
        z[4] = banr(r, a, b, c) == s
        z[5] = bani(r, a, b, c) == s
        z[6] = borr(r, a, b, c) == s
        z[7] = bori(r, a, b, c) == s
        z[8] = setr(r, a, c) == s
        z[9] = seti(r, a, c) == s
        z[10] = gtir(r, a, b, c) == s
        z[11] = gtri(r, a, b, c) == s
        z[12] = gtrr(r, a, b, c) == s
        z[13] = eqir(r, a, b, c) == s
        z[14] = eqri(r, a, b, c) == s
        z[15] = eqrr(r, a, b, c) == s
        if (z.count { it } >= 3) ans++
    }
    println(ans)
}
