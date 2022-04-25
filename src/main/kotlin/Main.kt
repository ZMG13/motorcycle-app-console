
import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit
private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {
    runMenu()
}


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
    logger.info { "addMotorcycle() function invoked" }
}

fun listMotorcycles(){
    logger.info { "listMotorcycles() function invoked" }
}

fun updateMotorcycle(){
    logger.info { "updateMotorcycle() function invoked" }
}

fun deleteMotorcycle(){
    logger.info { "deleteMotorcycle() function invoked" }
}

fun exitApp(){
    println("Exiting see ya!")
    exit(0)
}