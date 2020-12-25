fun addr(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] + r[b] }

fun addi(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] + b }

fun mulr(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] * r[b] }

fun muli(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] * b }

fun banr(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] and r[b] }

fun bani(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] and b }

fun borr(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] or r[b] }

fun bori(r: IntArray, a: Int, b: Int, c: Int) { r[c] = r[a] or b }

fun setr(r: IntArray, a: Int, @Suppress("UNUSED_PARAMETER") b: Int, c: Int) { r[c] = r[a] }

fun seti(r: IntArray, a: Int, @Suppress("UNUSED_PARAMETER") b: Int, c: Int) { r[c] = a }

fun gtir(r: IntArray, a: Int, b: Int, c: Int) { r[c] = if (a > r[b]) 1 else 0 }

fun gtri(r: IntArray, a: Int, b: Int, c: Int) { r[c] = if (r[a] > b) 1 else 0 }

fun gtrr(r: IntArray, a: Int, b: Int, c: Int) { r[c] = if (r[a] > r[b]) 1 else 0 }

fun eqir(r: IntArray, a: Int, b: Int, c: Int) { r[c] = if (a == r[b]) 1 else 0 }

fun eqri(r: IntArray, a: Int, b: Int, c: Int) { r[c] = if (r[a] == b) 1 else 0 }

fun eqrr(r: IntArray, a: Int, b: Int, c: Int) { r[c] = if (r[a] == r[b]) 1 else 0 }

fun main() {
    val ipr = readLine()!!.split(" ")[1].toInt()
    val lines = generateSequence { readLine() }.filterNot(String::isEmpty).toList()
    val n = lines.size
    val inst = Array<(IntArray, Int, Int, Int) -> Unit>(n) { { _, _, _, _ -> } }
    val az = IntArray(n)
    val bz = IntArray(n)
    val cz = IntArray(n)
    for (i in 0 until n) {
        val (ii, aa, bb, cc) = lines[i].split(" ")
        inst[i] = when (ii) {
            "addr" -> ::addr
            "addi" -> ::addi
            "mulr" -> ::mulr
            "muli" -> ::muli
            "banr" -> ::banr
            "bani" -> ::bani
            "borr" -> ::borr
            "bori" -> ::bori
            "setr" -> ::setr
            "seti" -> ::seti
            "gtir" -> ::gtir
            "gtri" -> ::gtri
            "gtrr" -> ::gtrr
            "eqir" -> ::eqir
            "eqri" -> ::eqri
            "eqrr" -> ::eqrr
            else -> throw AssertionError(inst)
        }
        az[i] = aa.toInt()
        bz[i] = bb.toInt()
        cz[i] = cc.toInt()
    }

    var ip = 0
    var r = intArrayOf(0, 0, 0, 0, 0, 0)
    while (true) {
        if (ip !in 0 until n) break
        r[ipr] = ip

        inst[ip](r, az[ip], bz[ip], cz[ip])

        ip = r[ipr] + 1
    }

    println(r[0])
}
