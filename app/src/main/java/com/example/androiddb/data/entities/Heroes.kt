package com.example.androiddb.data.entities

import kotlin.random.Random

data class Heroes(
    val id: String,
    val photo: String,
    val name: String,
    val life:Int = 100
){

        fun isAlive():Boolean {
            if (life > 0) {
                return true
            } else {
                return false
            }
        }

        fun healHero(): Heroes {
            val currentLife = if (life < 80 && life != 0) {
                life + 20
            } else {
                100
            }
            return this.copy(life = currentLife)
        }
        fun hitHero():Heroes {
            val damage = Random.nextInt(10,60)
            val currentLife = (life-damage).coerceAtLeast(0)
            return this.copy(life = currentLife)
        }

}
