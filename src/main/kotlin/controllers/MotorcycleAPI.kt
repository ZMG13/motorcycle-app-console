package controllers

import models.Motorcycle
import persistence.Serializer
import persistence.XMLSerializer
import java.io.File


class MotorcycleAPI(serializerType: Serializer) {

    private var motorcycles = ArrayList<Motorcycle>()
    private var serializer: Serializer = serializerType

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
            "No archived Motorcycles stored"
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

    fun updateMotorcycle(indexToUpdate: Int, note: Motorcycle?): Boolean {
        //find the Motorcycle object by the index number
        val foundMotorcycle = findMotorcycle(indexToUpdate)

        //if the Motorcycle exists, use the Motorcycle details passed as parameters to update the found Motorcycle in the ArrayList.
        if ((foundMotorcycle != null) && (note != null)) {
            foundMotorcycle.MotorcycleBrand = note.MotorcycleBrand
            foundMotorcycle.MotorcycleLicence = note.MotorcycleLicence
            foundMotorcycle.MotorcycleType = note.MotorcycleType
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, motorcycles);
    }
    @Throws(Exception::class)
    fun load() {
        motorcycles = serializer.read() as ArrayList<Motorcycle>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(motorcycles)
    }
}

