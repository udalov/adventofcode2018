fun main() {
    val lines = generateSequence { readLine() }.toList()

    val regex = "position=<([\\d-]+),([\\d-]+)>velocity=<([\\d-]+),([\\d-]+)>".toRegex()
    val a = arrayListOf<Pair<Int, Int>>()
    val d = arrayListOf<Pair<Int, Int>>()
    for (line in lines) {
        val l = line.replace(" ", "").replace("\t", "")
        val (x, y, dx, dy) = regex.matchEntire(l)!!.destructured
        a.add(x.toInt() to y.toInt())
        d.add(dx.toInt() to dy.toInt())
    }

    val mx = 0
    val my = 0

    val c = arrayListOf<MutableList<Char>>()
    for (i in 0 until 300) {
        val l = arrayListOf<Char>()
        repeat(300) { l.add('.') }
        c.add(l)
    }

    for (iter in 1 until 30000) {
        for ((x, y) in a) {
            if (x + mx in c.indices && y + my in c[x + mx].indices) {
                c[x + mx][y + my] = '#'
            }
        }

        if (iter >= 10272 && iter <= 10276) {
            for (i in 0 until 300) {
                for (j in 0 until 300) print(c[j][i])
                println()
            }
            println()
            println()
            println()
        }

        for ((x, y) in a) {
            if (x + mx in c.indices && y + my in c[x + mx].indices) {
                c[x + mx][y + my] = '.'
            }
        }

        for (i in a.indices) {
            val (x, y) = a[i]
            val (dx, dy) = d[i]
            a[i] = Pair(x + dx, y + dy)
        }
        
        /*
        val xs = a.map { it.first }
        val ys = a.map { it.second }
        val mdx = xs.max()!! - xs.min()!!
        val mdy = ys.max()!! - ys.min()!!
        println("$iter $mdx $mdy")
        */
    }
}
