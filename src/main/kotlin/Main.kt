
import utils.ScannerInput
import java.lang.System.exit
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
    println("You chose Add Note")
}

fun listMotorcycles(){
    println("You chose List Notes")
}

fun updateMotorcycle(){
    println("You chose Update Note")
}

fun deleteMotorcycle(){
    println("You chose Delete Note")
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}