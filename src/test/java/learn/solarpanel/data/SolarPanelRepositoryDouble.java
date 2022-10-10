package learn.solarpanel.data;

import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SolarPanelRepositoryDouble implements SolarPanelRepository{
    private final ArrayList<SolarPanel> solarPanels = new ArrayList<>();
public SolarPanelRepositoryDouble (){
SolarPanel solarPanel = new SolarPanel();
solarPanel.setId(30);
solarPanel.setSection(Sections.MAIN);
solarPanel.setColumn(5);
solarPanel.setRow(21);
solarPanel.setYearInstalled(2022);
solarPanel.setTracking(true);
solarPanel.setMaterial(SolarPanelMaterial.MONOCRYSTALLINE_SILICON);
solarPanels.add(solarPanel);
solarPanels.add(new SolarPanel(31,Sections.MAIN,5,21,2022,true,SolarPanelMaterial.AMORPHUS_SILICON));
}
    @Override
    public List<SolarPanel> findAll() {
        return new ArrayList<>(solarPanels);
    }

    @Override
    public List<SolarPanel> findBySection(Sections Section) {
        return null;
    }

    @Override
    public SolarPanel findById(int Id) {
        return null;
    }

    @Override
    public List<SolarPanel> findByMaterial(SolarPanelMaterial material) {
        return null;
    }

    @Override
    public SolarPanel add(SolarPanel solarPanel) {
    solarPanels.add(solarPanel);
        return solarPanel;
    }

    @Override
    public boolean update(SolarPanel solarPanel) {
        return false;
    }

    @Override
    public boolean deleteById(int Id) {
        return false;
    }
}
