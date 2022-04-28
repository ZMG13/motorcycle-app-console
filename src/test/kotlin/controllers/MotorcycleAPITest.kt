package controllers

import models.Motorcycle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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
        honda = Motorcycle("honda", 3, "cruiser", true)
        ktm = Motorcycle("ktm", 4, "sport", false)
        kawazaki = Motorcycle("kawazaki", 4, "dirt", false)
        suzuki = Motorcycle("suzuki", 1, "sport", true)

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
    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Motorcycle to a populated list adds to ArrayList`() {
            val newMotorcycle = Motorcycle("harley", 1, "Cruiser", false)
            assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
            assertTrue(populatedMotorcycles!!.add(newMotorcycle))
            assertEquals(6, populatedMotorcycles!!.numberOfMotorcycles())
            assertEquals(
                newMotorcycle,
                populatedMotorcycles!!.findMotorcycle(populatedMotorcycles!!.numberOfMotorcycles() - 1)
            )
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

    @Nested
    inner class ListNotes {

        @Test
        fun `listAllMotorcycles returns No Motorcycles Stored message when ArrayList is empty`() {
            assertEquals(0, emptyMotorcycles!!.numberOfMotorcycles())
            assertTrue(emptyMotorcycles!!.listAllMotorcycles().lowercase().contains("no Motorcycles"))
        }

        @Test
        fun `listAllMotorcycles returns Motorcycles when ArrayList has Motorcycles stored`() {
            assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
            val motorcycleString = populatedMotorcycles!!.listAllMotorcycles().lowercase()
            assertTrue(motorcycleString.contains("harley"))
            assertTrue(motorcycleString.contains("honda"))
            assertTrue(motorcycleString.contains("ktm"))
            assertTrue(motorcycleString.contains("kawazaki"))
            assertTrue(motorcycleString.contains("suzuki"))
        }
    }

    @Test
    fun `listActiveMotorcycles returns no active motorcycles stored when ArrayList is empty`() {
        assertEquals(0, emptyMotorcycles!!.numberOfActiveMotorcycles())
        assertTrue(
            emptyMotorcycles!!.listActiveMotorcycles().lowercase().contains("no active Motorcycles")
        )
    }

    @Test
    fun `listActiveMotorcycles returns active motorcycles when ArrayList has active motorcycles stored`() {
        assertEquals(3, populatedMotorcycles!!.numberOfActiveMotorcycles())
        val activeNotesString = populatedMotorcycles!!.listActiveMotorcycles().lowercase()
        assertTrue(activeNotesString.contains("harley"))
        assertFalse(activeNotesString.contains("honda"))
        assertTrue(activeNotesString.contains("ktm"))
        assertTrue(activeNotesString.contains("kawazaki"))
        assertFalse(activeNotesString.contains("suzuki"))
    }

    @Test
    fun `listArchivedMotorcycles returns no archived motorcycles when ArrayList is empty`() {
        assertEquals(0, emptyMotorcycles!!.numberOfArchivedMotorcycles())
        assertTrue(
            emptyMotorcycles!!.listArchivedMotorcycles().lowercase().contains("no archived motorcycles")
        )
    }

    @Test
    fun `listArchivedMotorcycles returns archived motorcycle when ArrayList has archived motorcycles stored`() {
        assertEquals(2, populatedMotorcycles!!.numberOfArchivedMotorcycles())
        val archivedNotesString = populatedMotorcycles!!.listArchivedMotorcycles().lowercase(Locale.getDefault())
        assertFalse(archivedNotesString.contains("harley"))
        assertTrue(archivedNotesString.contains("honda"))
        assertFalse(archivedNotesString.contains("ktm"))
        assertFalse(archivedNotesString.contains("kawazaki"))
        assertTrue(archivedNotesString.contains("suzuki"))
    }

    @Test
    fun `listMotorcyclesBySelectedLicense returns No Motorcycles when ArrayList is empty`() {
        assertEquals(0, emptyMotorcycles!!.numberOfMotorcycles())
        assertTrue(emptyMotorcycles!!.listMotorcyclesBySelectedLicense(1).lowercase().contains("no motorcycles")
        )
    }

    @Test
    fun `listMotorcyclesBySelectedLicense returns no Motorcycles when no Motorcycles of that license exist`() {
        //License 1 (1 Motorcycle), 2 (Motorcycle), 3 (1 Motorcycle). 4 (2 Motorcycle)
        assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
        val priority2String = populatedMotorcycles!!.listMotorcyclesBySelectedLicense(2).lowercase()
        assertTrue(priority2String.contains("no Motorcycles"))
        assertTrue(priority2String.contains("2"))
    }

    @Test
    fun `listMotorcyclesBySelectedLicense returns all Motorcycles that match that license when Motorcycles of that license exist`() {
        //Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
        assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
        val priority1String = populatedMotorcycles!!.listMotorcyclesBySelectedLicense(1).lowercase()
        assertTrue(priority1String.contains("1 note"))
        assertTrue(priority1String.contains("priority 1"))
        assertTrue(priority1String.contains("summer holiday"))
        assertFalse(priority1String.contains("swim"))
        assertFalse(priority1String.contains("learning kotlin"))
        assertFalse(priority1String.contains("code app"))
        assertFalse(priority1String.contains("test app"))


        val priority4String = populatedMotorcycles!!.listMotorcyclesBySelectedLicense(4).lowercase(Locale.getDefault())
        assertTrue(priority4String.contains("2 note"))
        assertTrue(priority4String.contains("priority 4"))
        assertFalse(priority4String.contains("swim"))
        assertTrue(priority4String.contains("code app"))
        assertTrue(priority4String.contains("test app"))
        assertFalse(priority4String.contains("learning kotlin"))
        assertFalse(priority4String.contains("summer holiday"))
    }
}

