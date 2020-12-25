data class Group(
    val desc: String,
    val id: Int,
    var units: Int,
    val hp: Int,
    val immuneTo: List<String>,
    val weakTo: List<String>,
    val attack: Int,
    val attackKind: String,
    val initiative: Int
) {
    val power: Int get() = units * attack

    override fun equals(other: Any?) = other is Group && other.id == id

    override fun hashCode() = id
}

data class T(val x: Int, val y: Int, val z: Int) : Comparable<T> {
    override fun compareTo(other: T): Int {
        if (x - other.x != 0) return x - other.x
        if (y - other.y != 0) return y - other.y
        if (z - other.z != 0) return z - other.z
        return 0
    }
}

fun damage(a: Group, b: Group): Int = a.power * when (a.attackKind) {
    in b.immuneTo -> 0
    in b.weakTo -> 2
    else -> 1
}

fun fight(a: List<Group>, b: List<Group>) {
    val target = hashMapOf<Group, Group>()
    val selected = hashSetOf<Group>()

    val g = mutableListOf<Pair<Group, List<Group>>>()
    for (ag in a) g.add(ag to b)
    for (bg in b) g.add(bg to a)

    for ((ag, bb) in g.sortedByDescending { (ag) -> T(ag.power, ag.initiative, 0) }) {
        val bg = bb.maxBy { bg ->
            if (bg in selected) T(0, 0, 0)
            else T(damage(ag, bg), bg.power, bg.initiative)
        }!!
        if (damage(ag, bg) == 0) continue
        if (!selected.add(bg)) continue
        target[ag] = bg
    }

    for (ag in (a + b).sortedByDescending(Group::initiative)) {
        if (ag.units <= 0) continue
        val bg = target[ag] ?: continue
        val d = damage(ag, bg)
        bg.units -= d / bg.hp;
        // println("${ag.desc} group ${ag.id} attacks defending group ${bg.id} ($d damage), killing ${d / bg.hp} units")
    }
}

fun cleanup(a: MutableList<Group>) {
    val it = a.iterator()
    while (it.hasNext()) {
        if (it.next().units <= 0) it.remove()
    }
}

fun main() {
    val immuneOrWeak = "\\(((immune|weak) to \\w+(, \\w+)*)?;? ?((immune|weak) to \\w+(, \\w+)*)?\\)"
    val regex = "(\\d+) units each with (\\d+) hit points( $immuneOrWeak)? with an attack that does (\\d+) (\\w+) damage at initiative (\\d+)".toRegex()

    val lines = generateSequence { readLine() }.toList()
    var a = mutableListOf<Group>()
    var b = mutableListOf<Group>()
    var infection = false
    var id = 1
    var gid = 1
    for (line in lines) {
        if (line.isEmpty()) continue
        if (line == "Infection:") {
            infection = true
            gid = 1
            continue
        }
        if (line == "Immune System:") continue

        val s = regex.matchEntire(line)!!.groupValues

        fun getImmuneWeak(prefix: String) =
            if (s[4].startsWith(prefix)) s[4].substringAfter(prefix).split(", ")
            else if (s[7].startsWith(prefix)) s[7].substringAfter(prefix).split(", ")
            else emptyList()

        val group = Group(
            "${if (infection) "Infection" else "Immune System"} group ${gid++}",
            id++,
            s[1].toInt(),
            s[2].toInt(),
            getImmuneWeak("immune to "),
            getImmuneWeak("weak to "),
            s[10].toInt(),
            s[11],
            s[12].toInt()
        )

        println(group)
        if (infection) b.add(group) else a.add(group)
    }

    println()
    println()
    println()

    while (a.isNotEmpty() && b.isNotEmpty()) {
        /*
        println("-----------------")
        println("Immune System:")
        for (ga in a) println("Group ${ga.id} contains ${ga.units} units")
        println()
        println("Infection:")
        for (gb in b) println("Group ${gb.id} contains ${gb.units} units")
        println()
        */

        fight(a, b)
        cleanup(a)
        cleanup(b)
    }

    println(a.sumBy { it.units } + b.sumBy { it.units })
}
