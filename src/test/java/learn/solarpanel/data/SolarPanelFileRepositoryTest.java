package learn.solarpanel.data;

import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelFileRepositoryTest {
    private static final String SEED_PATH = "./data/solarpanels-seed.csv";
    private static final String TEST_PATH = "./data/solarpanels-test.csv";


    private final SolarPanelFileRepository repository = new SolarPanelFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAllSolarPanels() throws FileNotFoundException, DataAccessException {
        List<SolarPanel> actual = repository.findAll();
        assertNotNull(actual);
        assertEquals(28, actual.size());
    }

    @Test
    void shouldFindExistingSections() throws FileNotFoundException, DataAccessException {
        List<SolarPanel> main = repository.findBySection(Sections.MAIN);
        assertNotNull(main);
        assertEquals(12, main.size());
        List<SolarPanel> upperhill = repository.findBySection(Sections.UPPERHILL);
        assertNotNull(upperhill);
        assertEquals(8, upperhill.size());
        List<SolarPanel> lowerhill = repository.findBySection(Sections.LOWERHILL);
        assertNotNull(lowerhill);
    }

    @Test
    void shouldFindExistingId() throws FileNotFoundException, DataAccessException {
        SolarPanel main = repository.findById(20);
        assertNotNull(main);

        assertEquals(Sections.UPPERHILL, main.getSection());
        assertEquals(2, main.getRow());
        assertEquals(4, main.getColumn());
    }
    @Test
    void shouldNotFindExistingId() throws FileNotFoundException, DataAccessException {
        SolarPanel nope = repository.findById(187501);
        assertNull(nope);
    }

    @Test
    void shouldFindOneOfEachMaterial() throws FileNotFoundException, DataAccessException {
        List<SolarPanel> mono = repository.findByMaterial(SolarPanelMaterial.MONOCRYSTALLINE_SILICON);
        assertNotNull(mono);
        assertEquals(5, mono.size());
        List<SolarPanel> amorphus = repository.findByMaterial(SolarPanelMaterial.AMORPHUS_SILICON);
        assertNotNull(amorphus);
        assertEquals(5, amorphus.size());
        List<SolarPanel> cadmium = repository.findByMaterial(SolarPanelMaterial.CADMIUM_TELLURIDE);
        assertNotNull(cadmium);
        assertEquals(5, cadmium.size());
        List<SolarPanel> copper = repository.findByMaterial(SolarPanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE);
        assertNotNull(copper);
        assertEquals(7, copper.size());
        List<SolarPanel> multi= repository.findByMaterial(SolarPanelMaterial.MULTICRYSTALLINE_SILICON);
        assertNotNull(multi);
        assertEquals(6, multi.size());

    }

    @Test
    void shouldAddSolarPanel() throws FileNotFoundException, DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setMaterial(SolarPanelMaterial.AMORPHUS_SILICON);
        solarPanel.setRow(1);
        solarPanel.setColumn(5);
        solarPanel.setYearInstalled(2022);
        solarPanel.setSection(Sections.MAIN);
        solarPanel.setTracking(true);
        SolarPanel actual = repository.add(solarPanel);
        assertNotNull(actual);
        assertEquals(29, actual.getId());

    }

    @Test
    void shouldUpdateExisting() throws FileNotFoundException, DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setId(6);
        solarPanel.setSection(Sections.LOWERHILL);
        solarPanel.setTracking(false);
        solarPanel.setRow(5);
        solarPanel.setColumn(7);
        solarPanel.setYearInstalled(2022);
        solarPanel.setMaterial(SolarPanelMaterial.CADMIUM_TELLURIDE);

        boolean success = repository.update(solarPanel);
        assertTrue(success);

        SolarPanel actual = repository.findById(6);
        assertNotNull(actual);
        assertEquals(Sections.LOWERHILL, actual.getSection());
        assertEquals(2022, actual.getYearInstalled());

    }

    @Test
    void shouldNotUpdateExisting() throws FileNotFoundException, DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setId(187501);

        boolean actual = repository.update(solarPanel);
        assertFalse(actual);
    }

    @Test
    void shouldDeleteExisting() throws FileNotFoundException, DataAccessException {
        boolean actual = repository.deleteById(10);
        assertTrue(actual);

        SolarPanel o = repository.findById(10);
        assertNull(o);
    }

    @Test
    void shouldNotDeleteMissing() throws FileNotFoundException, DataAccessException {
        boolean actual = repository.deleteById(187501);
        assertFalse(actual);
    }
}
