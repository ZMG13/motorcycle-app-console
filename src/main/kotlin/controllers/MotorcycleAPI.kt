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

    fun listAllMotorcycles(): String =
        if  (motorcycles.isEmpty()) "No motorcycles stored"
        else formatListString(motorcycles)

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

    fun listActiveMotorcycles(): String =
        if  (numberOfActiveMotorcycles() == 0)  "No active motorcycles stored"
        else formatListString(motorcycles.filter { motorcycle -> !motorcycle.isMotorcycleArchived})

    fun listArchivedMotorcycles(): String =
        if  (numberOfArchivedMotorcycles() == 0) "No archived motorcycles stored"
        else formatListString(motorcycles.filter { motorcycle -> motorcycle.isMotorcycleArchived})

    fun numberOfArchivedMotorcycles(): Int = motorcycles.count { motorcycle: Motorcycle -> motorcycle.isMotorcycleArchived }

        fun numberOfActiveMotorcycles(): Int = motorcycles.count {motorcycle : Motorcycle -> !motorcycle.isMotorcycleArchived}

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

    fun numberOfMotorcyclesByLicense(license: Int): Int = motorcycles.count { motorcycle: Motorcycle -> motorcycle.MotorcycleLicence == license }

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
    fun archiveMotorcycle(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val motorcycleToArchive = motorcycles[indexToArchive]
            if (!motorcycleToArchive.isMotorcycleArchived) {
                motorcycleToArchive.isMotorcycleArchived = true
                return true
            }
        }
        return false
    }
    fun searchByBrand (searchString : String) =
        formatListString(
            motorcycles.filter { motorcycle -> motorcycle.MotorcycleBrand.contains(searchString, ignoreCase = true) })
    fun formatListString(notesToFormat : List<Motorcycle>) : String =
        notesToFormat
            .joinToString (separator = "\n") { motorcycle ->
                motorcycles.indexOf(motorcycle).toString() + ": " + motorcycle.toString() }
}

