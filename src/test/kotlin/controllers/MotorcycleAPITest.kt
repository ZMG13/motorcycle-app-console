package controllers

import models.Motorcycle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MotorcycleAPITest {
    private var harley: Motorcycle? = null
    private var honda: Motorcycle? = null
    private var ktm: Motorcycle? = null
    private var kawazaki: Motorcycle? = null
    private var suzuki: Motorcycle? = null
    private var populatedMotorcycles: MotorcycleAPI? = MotorcycleAPI()
    private var emptyMotorcycles: MotorcycleAPI? = MotorcycleAPI()

    @BeforeEach
    fun setup() {
        harley = Motorcycle("harley", 2, "cruiser", false)
        honda = Motorcycle("honda", 3, "cruiser", false)
        ktm = Motorcycle("ktm", 4, "sport", false)
        kawazaki = Motorcycle("kawazaki", 4, "dirt", false)
        suzuki = Motorcycle("suzuki", 1, "sport", false)

        //adding 5 Note to the notes api
        populatedMotorcycles!!.add(harley!!)
        populatedMotorcycles!!.add(honda!!)
        populatedMotorcycles!!.add(ktm!!)
        populatedMotorcycles!!.add(kawazaki!!)
        populatedMotorcycles!!.add(suzuki!!)
    }

    @AfterEach
    fun tearDown() {
        harley = null
        honda = null
        ktm = null
        kawazaki = null
        suzuki = null
        populatedMotorcycles = null
        emptyMotorcycles = null
    }

    /*  @Test
    fun `adding a Note to a populated list adds to ArrayList`(){
        val newMotorcycle = Motorcycle("indian", 2, "Cruiser", false)
        assertTrue(populatedMotorcycles!!.add(newMotorcycle))
    }

    @Test
    fun `adding a Note to an empty list adds to ArrayList`(){
        val newMotorcycle = Motorcycle("indian", 2, "Cruiser", false)
        assertTrue(emptyMotorcycles!!.add(newMotorcycle))
    } */

        @Test
        fun `adding a Motorcycle to a populated list adds to ArrayList`() {
            val newMotorcycle = Motorcycle("harley", 1, "Cruiser", false)
            assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
            assertTrue(populatedMotorcycles!!.add(newMotorcycle))
            assertEquals(6, populatedMotorcycles!!.numberOfMotorcycles())
            assertEquals(
                newMotorcycle,
                populatedMotorcycles!!.findMotorcycle(populatedMotorcycles!!.numberOfMotorcycles() - 1))
        }

        @Test
        fun `adding a Motorcycle to an empty list adds to ArrayList`() {
            val newMotorcycle = Motorcycle("harley", 1, "Cruiser", false)
            assertEquals(0, emptyMotorcycles!!.numberOfMotorcycles())
            assertTrue(emptyMotorcycles!!.add(newMotorcycle))
            assertEquals(1, emptyMotorcycles!!.numberOfMotorcycles())
            assertEquals(newMotorcycle, emptyMotorcycles!!.findMotorcycle(emptyMotorcycles!!.numberOfMotorcycles() - 1))
        }
    }

