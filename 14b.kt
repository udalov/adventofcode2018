fun main() {
    val a = mutableListOf(3, 7)
    var x = 0
    var y = 1
    val b = listOf(4, 4, 0, 2, 3, 1)
    for (iter in 1..Int.MAX_VALUE) {
        val z = a[x] + a[y]
        if (z >= 10) {
            a.add(z / 10)
            if (a.size >= b.size && a.subList(a.size - b.size, a.size) == b) {
                println(a.size - b.size)
                return
            }
            a.add(z % 10)
        } else {
            a.add(z)
        }
        if (a.size >= b.size && a.subList(a.size - b.size, a.size) == b) {
            println(a.size - b.size)
            return
        }

        x = (x + a[x] + 1) % a.size
        y = (y + a[y] + 1) % a.size
    }
}
