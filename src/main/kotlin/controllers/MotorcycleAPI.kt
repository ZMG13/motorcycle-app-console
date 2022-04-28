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

    fun listActiveMotorcycles(): String {
        return if (numberOfActiveMotorcycles() == 0) {
            "No active Motorcycles stored"
        } else {
            var listOfActiveMotorcycles = ""
            for (motorcycle in motorcycles) {
                if (!motorcycle.isMotorcycleArchived) {
                    listOfActiveMotorcycles += "${motorcycles.indexOf(motorcycle)}: $motorcycle \n"
                }
            }
            listOfActiveMotorcycles
        }
    }

    fun listArchivedMotorcycles(): String {
        return if (numberOfArchivedMotorcycles() == 0) {
            "No archived Motorcycle stored"
        } else {
            var listOfArchivedMotorcycles = ""
            for (motorcycle in motorcycles) {
                if (motorcycle.isMotorcycleArchived) {
                    listOfArchivedMotorcycles += "${motorcycles.indexOf(motorcycle)}: $motorcycle \n"
                }
            }
            listOfArchivedMotorcycles
        }
    }

    fun numberOfArchivedMotorcycles(): Int {
        var counter = 0
        for (motorcycle in motorcycles) {
            if (motorcycle.isMotorcycleArchived) {
                counter++
            }
        }
        return counter
    }

    fun numberOfActiveMotorcycles(): Int {
        var counter = 0
        for (motorcycle in motorcycles) {
            if (!motorcycle.isMotorcycleArchived) {
                counter++
            }
        }
        return counter
    }

    fun listMotorcyclesBySelectedLicense(license: Int): String {
        return if (motorcycles.isEmpty()) {
            "No motorcycles stored"
        } else {
            var listOfMotorcycles = ""
            for (i in motorcycles.indices) {
                if (motorcycles[i].MotorcycleLicence == license) {
                    listOfMotorcycles +=
                        """$i: ${motorcycles[i]}
                        """.trimIndent()
                }
            }
            if (listOfMotorcycles.equals("")) {
                "No motorcycle with license: $license"
            } else {
                "${numberOfMotorcyclesByLicense(license)} motorcycles with priority $license: $listOfMotorcycles"
            }
        }
    }

    fun numberOfMotorcyclesByLicense(license: Int): Int {
        var counter = 0
        for (motorcycle in motorcycles) {
            if (motorcycle.MotorcycleLicence == license) {
                counter++
            }
        }
        return counter
    }

    fun deleteMotorcycle(indexToDelete: Int): Motorcycle? {
        return if (isValidListIndex(indexToDelete, motorcycles)) {
            motorcycles.removeAt(indexToDelete)
        } else null
    }
}