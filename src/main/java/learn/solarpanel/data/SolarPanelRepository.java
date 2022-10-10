package learn.solarpanel.data;

import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;

import java.io.FileNotFoundException;
import java.util.List;

public interface SolarPanelRepository {
    List<SolarPanel> findAll() throws FileNotFoundException, DataAccessException;

    List<SolarPanel> findBySection(Sections section) throws FileNotFoundException, DataAccessException;

    SolarPanel findById(int Id) throws FileNotFoundException, DataAccessException;

    List<SolarPanel> findByMaterial(SolarPanelMaterial material) throws FileNotFoundException, DataAccessException;

    SolarPanel add(SolarPanel solarPanel) throws FileNotFoundException, DataAccessException;

    boolean update(SolarPanel solarPanel) throws DataAccessException, FileNotFoundException;

    boolean deleteById(int Id) throws FileNotFoundException, DataAccessException;
}
