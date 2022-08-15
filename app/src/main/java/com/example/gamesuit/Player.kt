package com.example.gamesuit

open class Player {
    open var pilihan: SuitType? = null
    open var name: String? = null
}

class MainPlayer(override var name: String?) : Player()

class Bot : Player() {
    override var name: String? = "CPU"

    fun randomPilihan(): SuitType {
        return SuitType.values().random()
    }
}



