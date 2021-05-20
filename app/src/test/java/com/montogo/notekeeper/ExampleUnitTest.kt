package com.montogo.notekeeper

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun kotlinDemoChapterTwo() {
        val p = Person("Joseph", 160.0)
        p.weightKilos = 75.0
        assertEquals("Joseph", p.name)
        assertEquals(75.0 * 2.2, p.weightLbs, 0.1)

        p.eatDessert()
        assertEquals(165.0 + 4.0, p.weightLbs, 0.1)
        assertEquals(169.0 - 20.0, p.calcGoalWeight(20.0), 0.1)
    }

    @Test
    fun nullableOperators() {
        val p: Person? = null
        val n = p?.name
        if(n != null) n else "default"
        assertEquals("default", n ?: "default")
        assertEquals("default", p?.name ?: "default")
    }
}

class Person(val name: String, var weightLbs: Double) {
    var weightKilos: Double
        get() = weightLbs / 2.2
        set(value) {
            weightLbs = value * 2.2
        }

    fun eatDessert(addedIceCream: Boolean = true) {
        weightLbs += if(addedIceCream) 4.0 else 2.0
    }

    fun calcGoalWeight(lbsToLose: Double) : Double {
        return weightLbs - lbsToLose
    }
}