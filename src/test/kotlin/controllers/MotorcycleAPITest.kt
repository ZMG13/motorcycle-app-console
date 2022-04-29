package controllers

import models.Motorcycle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class MotorcycleAPITest {
    private var harley: Motorcycle? = null
    private var honda: Motorcycle? = null
    private var ktm: Motorcycle? = null
    private var kawazaki: Motorcycle? = null
    private var suzuki: Motorcycle? = null
    private var populatedMotorcycles: MotorcycleAPI? = MotorcycleAPI(XMLSerializer(File("motorcycles.xml")))
    private var emptyMotorcycles: MotorcycleAPI? = MotorcycleAPI(XMLSerializer(File("motorcycles.xml")))

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
            assertTrue(emptyMotorcycles!!.listAllMotorcycles().lowercase().contains("no motorcycles"))
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
            emptyMotorcycles!!.listActiveMotorcycles().lowercase().contains("no active motorcycles")
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
        assertTrue(priority2String.contains("harley"))
        assertTrue(priority2String.contains("2"))
        assertFalse(priority2String.contains("honda"))
    }

    @Test
    fun `listMotorcyclesBySelectedLicense returns all Motorcycles that match that license when Motorcycles of that license exist`() {
        //Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
        assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
        val priority1String = populatedMotorcycles!!.listMotorcyclesBySelectedLicense(1).lowercase()
        assertTrue(priority1String.contains("sport"))
        assertTrue(priority1String.contains("suzuki"))
        assertFalse(priority1String.contains("honda"))
        assertFalse(priority1String.contains("cruiser"))
        assertFalse(priority1String.contains("harley"))
        assertFalse(priority1String.contains("ducati"))
        assertFalse(priority1String.contains("ford"))


        val priority4String = populatedMotorcycles!!.listMotorcyclesBySelectedLicense(4).lowercase(Locale.getDefault())
        assertTrue(priority4String.contains("ktm"))
        assertTrue(priority4String.contains("sport"))
        assertFalse(priority4String.contains("honda"))
        assertTrue(priority4String.contains("dirt"))
        assertFalse(priority4String.contains("suzuki"))
        assertFalse(priority4String.contains("cruiser"))
        assertFalse(priority4String.contains("harley"))
    }

    @Nested
    inner class DeleteMotorcycles {

        @Test
        fun `deleting a Motorcycle that does not exist, returns null`() {
            assertNull(emptyMotorcycles!!.deleteMotorcycle(0))
            assertNull(populatedMotorcycles!!.deleteMotorcycle(-1))
            assertNull(populatedMotorcycles!!.deleteMotorcycle(5))
        }

        @Test
        fun `deleting a motorcycle that exists delete and returns deleted object`() {
            assertEquals(5, populatedMotorcycles!!.numberOfMotorcycles())
            assertEquals(suzuki, populatedMotorcycles!!.deleteMotorcycle(4))
            assertEquals(4, populatedMotorcycles!!.numberOfMotorcycles())
            assertEquals(harley, populatedMotorcycles!!.deleteMotorcycle(0))
            assertEquals(3, populatedMotorcycles!!.numberOfMotorcycles())
        }
    }
    @Nested
    inner class UpdateMotorcycles {
        @Test
        fun `updating a Motorcycle that does not exist returns false`(){
            assertFalse(populatedMotorcycles!!.updateMotorcycle(6, Motorcycle("harley", 2, "cruiser", false)))
            assertFalse(populatedMotorcycles!!.updateMotorcycle(-1, Motorcycle("honda", 2, "sport", false)))
            assertFalse(emptyMotorcycles!!.updateMotorcycle(0, Motorcycle("ktm", 2, "dirt", false)))
        }

        @Test
        fun `updating a Motorcycle that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(suzuki, populatedMotorcycles!!.findMotorcycle(4))
            assertEquals("suzuki", populatedMotorcycles!!.findMotorcycle(4)!!.MotorcycleBrand)
            assertEquals(1, populatedMotorcycles!!.findMotorcycle(4)!!.MotorcycleLicence)
            assertEquals("sport", populatedMotorcycles!!.findMotorcycle(4)!!.MotorcycleType)

            //update Motorcycle 5 with new information and ensure contents updated successfully
            assertTrue(populatedMotorcycles!!.updateMotorcycle(4, Motorcycle("ktm", 2, "Cruiser", false)))
            assertEquals("ktm", populatedMotorcycles!!.findMotorcycle(4)!!.MotorcycleBrand)
            assertEquals(2, populatedMotorcycles!!.findMotorcycle(4)!!.MotorcycleLicence)
            assertEquals("Cruiser", populatedMotorcycles!!.findMotorcycle(4)!!.MotorcycleType)
        }
    }
    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty notes.XML file.
            val storingMotorcycles = MotorcycleAPI(XMLSerializer(File("motorcycles.xml")))
            storingMotorcycles.store()

            //Loading the empty Motorcycles.xml file into a new object
            val loadedMotorcycles = MotorcycleAPI(XMLSerializer(File("motorcycles.xml")))
            loadedMotorcycles.load()

            //Comparing the source of the Motorcycles (storingMotorcycles) with the XML loaded Motorcycles (loadedNotes)
            assertEquals(0, storingMotorcycles.numberOfMotorcycles())
            assertEquals(0, loadedMotorcycles.numberOfMotorcycles())
            assertEquals(storingMotorcycles.numberOfMotorcycles(), loadedMotorcycles.numberOfMotorcycles())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 Motorcycles to the Motorcycles.XML file.
            val storingMotorcycles = MotorcycleAPI(XMLSerializer(File("motorcycles.xml")))
            storingMotorcycles.add(honda!!)
            storingMotorcycles.add(suzuki!!)
            storingMotorcycles.add(harley!!)
            storingMotorcycles.store()

            //Loading Motorcycles.xml into a different collection
            val loadedMotorcycles = MotorcycleAPI(XMLSerializer(File("motorcycles.xml")))
            loadedMotorcycles.load()

            //Comparing the source of the Motorcycles (storingMotorcycles) with the XML loaded Motorcycles (loadedMotorcycles)
            assertEquals(3, storingMotorcycles.numberOfMotorcycles())
            assertEquals(3, loadedMotorcycles.numberOfMotorcycles())
            assertEquals(storingMotorcycles.numberOfMotorcycles(), loadedMotorcycles.numberOfMotorcycles())
            assertEquals(storingMotorcycles.findMotorcycle(0), loadedMotorcycles.findMotorcycle(0))
            assertEquals(storingMotorcycles.findMotorcycle(1), loadedMotorcycles.findMotorcycle(1))
            assertEquals(storingMotorcycles.findMotorcycle(2), loadedMotorcycles.findMotorcycle(2))
        }
    }

    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {
        // Saving an empty Motorcycles.json file.
        val storingMotorcycles = MotorcycleAPI(JSONSerializer(File("motorcycles.json")))
        storingMotorcycles.store()

        //Loading the empty notes.json file into a new object
        val loadedMotorcycles = MotorcycleAPI(JSONSerializer(File("motorcycles.json")))
        loadedMotorcycles.load()

        //Comparing the source of the Motorcycles (storingMotorcycles) with the json loaded Motorcycles (loadedMotorcycles)
        assertEquals(0, storingMotorcycles.numberOfMotorcycles())
        assertEquals(0, loadedMotorcycles.numberOfMotorcycles())
        assertEquals(storingMotorcycles.numberOfMotorcycles(), loadedMotorcycles.numberOfMotorcycles())
    }

    @Test
    fun `saving and loading an loaded collection in JSON doesn't loose data`() {
        // Storing 3 Motorcycles to the Motorcycles.json file.
        val storingMotorcycles = MotorcycleAPI(JSONSerializer(File("motorcycles.json")))
        storingMotorcycles.add(honda!!)
        storingMotorcycles.add(suzuki!!)
        storingMotorcycles.add(harley!!)
        storingMotorcycles.store()

        //Loading Motorcycles.json into a different collection
        val loadedMotorcycles = MotorcycleAPI(JSONSerializer(File("motorcycles.json")))
        loadedMotorcycles.load()

        //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        assertEquals(3, storingMotorcycles.numberOfMotorcycles())
        assertEquals(3, loadedMotorcycles.numberOfMotorcycles())
        assertEquals(storingMotorcycles.numberOfMotorcycles(), loadedMotorcycles.numberOfMotorcycles())
        assertEquals(storingMotorcycles.findMotorcycle(0), loadedMotorcycles.findMotorcycle(0))
        assertEquals(storingMotorcycles.findMotorcycle(1), loadedMotorcycles.findMotorcycle(1))
        assertEquals(storingMotorcycles.findMotorcycle(2), loadedMotorcycles.findMotorcycle(2))
    }
    @Nested
    inner class ArchiveMotorcycles {
        @Test
        fun `archiving a motorcycle that does not exist returns false`(){
            assertFalse(populatedMotorcycles!!.archiveMotorcycle(6))
            assertFalse(populatedMotorcycles!!.archiveMotorcycle(-1))
            assertFalse(emptyMotorcycles!!.archiveMotorcycle(0))
        }

        @Test
        fun `archiving an already archived motorcycle returns false`(){
            assertTrue(populatedMotorcycles!!.findMotorcycle(1)!!.isMotorcycleArchived)
            assertFalse(populatedMotorcycles!!.archiveMotorcycle(1))
        }

        @Test
        fun `archiving an active motorcycle that exists returns true and archives`() {
            assertFalse(populatedMotorcycles!!.findMotorcycle(3)!!.isMotorcycleArchived)
            assertTrue(populatedMotorcycles!!.archiveMotorcycle(3))
            assertTrue(populatedMotorcycles!!.findMotorcycle(3)!!.isMotorcycleArchived)
        }
    }
}

