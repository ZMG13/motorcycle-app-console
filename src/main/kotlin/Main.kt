
import controllers.MotorcycleAPI
import models.Motorcycle
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit
private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {
    runMenu()
}
private val motorcycleAPI = MotorcycleAPI()

fun mainMenu() :Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        MOTORCYCLE APP          |
         > ----------------------------------
         > | MOTORCYCLE MENU                |
         > |   1) Add a motorcycle          |
         > |   2) List all motorcycles      |
         > |   3) Update a motorcycle       |
         > |   4) Delete a motorcycle       |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))

}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addMotorcycle()
            2  -> listMotorcycles()
            3  -> updateMotorcycle()
            4  -> deleteMotorcycle()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addMotorcycle(){
   // logger.info { "addMotorcycle() function invoked" }
    val motorcycleBrand = readNextLine("Enter motorcycle brand: ")
    val motorcycleLicence = readNextInt("Enter a license number (1, 2, 3, 4): ")
    val motorcycleType = readNextLine("Enter motorcycle type: ")
    val isAdded = motorcycleAPI.add(Motorcycle(motorcycleBrand, motorcycleLicence, motorcycleType, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listMotorcycles(){
   // logger.info { "listMotorcycles() function invoked" }
    println(motorcycleAPI.listAllMotorcycles())
}

fun updateMotorcycle(){
    logger.info { "updateMotorcycle() function invoked" }
}

fun deleteMotorcycle(){
   // logger.info { "deleteMotorcycle() function invoked" }
    listMotorcycles()
    if (motorcycleAPI.numberOfMotorcycles() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the motorcycle to delete: ")
        //pass the index of the note to MotorcycleAPI for deleting and check for success.
        val motorcycleToDelete = motorcycleAPI.deleteMotorcycle(indexToDelete)
        if (motorcycleToDelete != null) {
            println("Delete Successful! Deleted Motorcycle: ${motorcycleToDelete.MotorcycleBrand}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun exitApp(){
    println("Exiting see ya!")
    exit(0)
}