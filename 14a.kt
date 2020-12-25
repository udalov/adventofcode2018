fun main() {
    val a = mutableListOf(3, 7)
    var x = 0
    var y = 1
    val limit = 440231
    for (iter in 1..Int.MAX_VALUE) {
        if (a.size >= limit + 10) {
            println(a.subList(limit, limit + 10).joinToString(""))
            return
        }

        val z = a[x] + a[y]
        if (z >= 10) {
            a.add(z / 10)
            a.add(z % 10)
        } else {
            a.add(z)
        }

        x = (x + a[x] + 1) % a.size
        y = (y + a[y] + 1) % a.size
    }
}
