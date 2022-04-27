package controllers

import models.Motorcycle

private var motorcycles = ArrayList<Motorcycle>()
class MotorcycleAPI {
    fun add(motorcycle: Motorcycle): Boolean {
        return motorcycles.add(motorcycle)
    }
    fun listAllMotorcycles(): String {
        return if (motorcycles.isEmpty()) {
            "No motorcycles stored"
        } else {
            var listOfMotorcycle = ""
            for (i in motorcycles.indices) {
                listOfMotorcycle += "${i}: ${motorcycles[i]} \n"
            }
            listOfMotorcycle
        }
    }
}