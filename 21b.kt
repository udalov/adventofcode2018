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

    for (i in 0 until n) {
        val str = when (inst[i]) {
            ::addr -> {
                if (cz[i] == ipr && bz[i] == ipr) "goto ${i + 1}+r${az[i]}"
                else "r${cz[i]} = r${az[i]} + r${bz[i]}"
            }
            ::addi -> {
                if (cz[i] == ipr && az[i] == ipr) "goto ${i + 1 + bz[i]}"
                else "r${cz[i]} = r${az[i]} + ${bz[i]}"
            }
            ::mulr -> "r${cz[i]} = r${az[i]} * r${bz[i]}"
            ::muli -> "r${cz[i]} = r${az[i]} * ${bz[i]}"
            ::banr -> "r${cz[i]} = r${az[i]} & r${bz[i]}"
            ::bani -> "r${cz[i]} = r${az[i]} & ${bz[i]}"
            ::borr -> "r${cz[i]} = r${az[i]} | r${bz[i]}"
            ::bori -> "r${cz[i]} = r${az[i]} | ${bz[i]}"
            ::setr -> "r${cz[i]} = r${az[i]}"
            ::seti -> {
                if (cz[i] == ipr) "goto ${az[i] + 1}"
                else "r${cz[i]} = ${az[i]}"
            }
            ::gtir -> "r${cz[i]} = ${az[i]} > r${bz[i]} ? 1 : 0"
            ::gtri -> "r${cz[i]} = r${az[i]} > ${bz[i]} ? 1 : 0"
            ::gtrr -> "r${cz[i]} = r${az[i]} > r${bz[i]} ? 1 : 0"
            ::eqir -> "r${cz[i]} = ${az[i]} == r${bz[i]} ? 1 : 0"
            ::eqri -> "r${cz[i]} = r${az[i]} == ${bz[i]} ? 1 : 0"
            ::eqrr -> "r${cz[i]} = r${az[i]} == r${bz[i]} ? 1 : 0"
            else -> "o_O"
        }
        println("%02d: $str".format(i))
    }
    println()

    /*
        r1 = 0
    06: r2 = r1 | 65536
        r1 = 8725355
    08: r1 = (((r1 + (r2 & 255)) & 16777215) * 65899) & 16777215
        if (r2 < 256) goto 28
        r5 = 0
    18: if (r2 < 256 * (r5 + 1)) goto 26
        r5++
        goto 18
    26: r2 = r5
        goto 08
    28: if (r1 != r0) goto 06
    */    

    var ip = 0
    var r = intArrayOf(0, 0, 0, 0, 0, 0)
    var iter = 0L
    val s = mutableMapOf<Int, Long>()
    while (true) {
        if (ip !in 0 until n) break

        ++iter
        r[ipr] = ip
        inst[ip](r, az[ip], bz[ip], cz[ip])
        ip = r[ipr] + 1

        if (ip == 28) {
            if (r[1] in s) break
            s[r[1]] = iter
            if (s.size % 10000 == 0) println(s.size)
        }
    }

    val ans = s.keys.maxBy { s[it]!! }
    println("$ans -> ${s[ans]}")
}
