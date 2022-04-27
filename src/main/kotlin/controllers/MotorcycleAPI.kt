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
    fun numberOfMotorcycles(): Int {
        return motorcycles.size
    }

    fun findMotorcycle(index: Int): Motorcycle? {
        return if (isValidListIndex(index, motorcycles)) {
            motorcycles[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

}