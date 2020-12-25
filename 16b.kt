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

fun setr(r: List<Int>, a: Int, @Suppress("UNUSED_PARAMETER") b: Int, c: Int): List<Int> =
    r.toMutableList().also { it[c] = it[a] }

fun seti(r: List<Int>, a: Int, @Suppress("UNUSED_PARAMETER") b: Int, c: Int): List<Int> =
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
    val o = Array(16) { BooleanArray(16) { true } }
    var li = 0
    for (i in 0 until lines.size step 3) {
        if (!lines[i].startsWith("B")) {
            li = i
            break
        }
        val (a0s, b0s, c0s, d0s) = beforeRegex.matchEntire(lines[i])!!.destructured
        val (opcode, a, b, c) = lines[i + 1].split(" ").map(String::toInt)
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
        z[8] = setr(r, a, b, c) == s
        z[9] = seti(r, a, c, c) == s
        z[10] = gtir(r, a, b, c) == s
        z[11] = gtri(r, a, b, c) == s
        z[12] = gtrr(r, a, b, c) == s
        z[13] = eqir(r, a, b, c) == s
        z[14] = eqri(r, a, b, c) == s
        z[15] = eqrr(r, a, b, c) == s
        for (j in 0 until 16) {
            o[opcode][j] = o[opcode][j] and z[j]
        }
    }

    val mr = IntArray(16) { -1 }
    val mc = IntArray(16) { -1 }
    outer@ while (true) {
        for (i in o.indices) if (mr[i] == -1) {
            for (j in o.indices) if (mc[j] == -1 && o[i][j]) {
                val okr = o.indices.all { k -> mc[k] != -1 || !o[i][k] || k == j }
                if (okr) {
                    mr[i] = j
                    mc[j] = i
                    continue@outer
                }
                
                val okc = o.indices.all { k -> mr[k] != -1 || !o[k][j] || k == i }
                if (okc) {
                    mr[i] = j
                    mc[j] = i
                    continue@outer
                }
            }
        }
        break
    }
    require(-1 !in mr)
    require(-1 !in mc)

    var r = listOf(0, 0, 0, 0)
    for (i in li until lines.size) {
        val (opcode, a, b, c) = lines[i].split(" ").map(String::toInt)
        val f = when (mr[opcode]) {
            0 -> ::addr
            1 -> ::addi
            2 -> ::mulr
            3 -> ::muli
            4 -> ::banr
            5 -> ::bani
            6 -> ::borr
            7 -> ::bori
            8 -> ::setr
            9 -> ::seti
            10 -> ::gtir
            11 -> ::gtri
            12 -> ::gtrr
            13 -> ::eqir
            14 -> ::eqri
            15 -> ::eqrr
            else -> throw AssertionError(opcode)
        }
        r = f(r, a, b, c)
    }

    println(r[0])
}
