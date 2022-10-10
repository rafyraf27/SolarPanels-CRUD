package learn.solarpanels.domain;

import learn.solarpanel.data.DataAccessException;
import learn.solarpanel.data.SolarPanelRepositoryDouble;
import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SolarPanelServiceTest {
    SolarPanelService service = new SolarPanelService(new SolarPanelRepositoryDouble());
    @Test
void shouldAddSolarPanel() {
    }
    @Test
    void shouldNotAddNullPanel() throws FileNotFoundException, DataAccessException {
        SolarPanelResult result = service.add(null);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotAddPanelWhenNoRoom() throws FileNotFoundException, DataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(6, Sections.MAIN,251,251,2021,false, SolarPanelMaterial.AMORPHUS_SILICON));
        assertFalse(result.isSuccess());
    }
}