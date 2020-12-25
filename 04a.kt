fun main() {
    val lines = generateSequence { readLine() }.toList().sorted()
    val timeRegex = "\\d+:(\\d+)]".toRegex()
    var current = -1
    var fell = -1
    val slept = hashMapOf<Int, IntArray>()
    for (line in lines) {
        val (_, time, action, id) = line.split(" ")
        val min = timeRegex.matchEntire(time)!!.destructured.component1().toInt()
        when (action) {
            "Guard" -> current = id.substring(1).toInt()
            "falls" -> fell = min
            "wakes" -> {
                if (current !in slept) {
                    slept[current] = IntArray(60)
                }
                for (x in fell until min) {
                    slept[current]!![x]++
                }
            }
        }
    }

    val maxId = slept.keys.maxBy { slept[it]!!.sum() }!!
    val minutes = slept[maxId]!!
    val maxMin = minutes.indices.maxBy { minutes[it] }!!
    println(maxId * maxMin)
}
