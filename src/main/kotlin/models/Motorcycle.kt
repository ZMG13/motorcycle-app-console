package models

import persistence.Serializer

data class Motorcycle(var MotorcycleBrand: String,
                var MotorcycleLicence: Int,
                var MotorcycleType: String,
                var isMotorcycleArchived :Boolean)

