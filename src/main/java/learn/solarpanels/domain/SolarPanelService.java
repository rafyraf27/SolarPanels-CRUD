package learn.solarpanels.domain;

import learn.solarpanel.data.DataAccessException;
import learn.solarpanel.data.SolarPanelRepository;
import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolarPanelService {
    private final SolarPanelRepository repository;

    public SolarPanelService(SolarPanelRepository repository) {
        this.repository = repository;
    }

    public List<SolarPanel> findByType(Sections sections) throws FileNotFoundException, DataAccessException {
        return repository.findBySection(sections);
    }

    public SolarPanelResult add(SolarPanel solarPanel) throws FileNotFoundException, DataAccessException {
        SolarPanelResult result = validateInputs(solarPanel);
        if (!result.isSuccess()) {
            result.addErrorMessage("");
            return result;
        }
        Map<Sections, Integer> map = sections();
        map.put(solarPanel.getSection(), map.get(solarPanel.getSection()) + 1);
        result = validateDomain(map);
        if (!result.isSuccess()) {
            return result;
        }
        SolarPanel s = repository.add(solarPanel);
        result.setPayload();

        return result;
    }

    public SolarPanelResult update(SolarPanel solarPanel) throws FileNotFoundException, DataAccessException {
        SolarPanelResult result = validateInputs(solarPanel);
        if (!result.isSuccess()) {
            return result;
        }
        SolarPanel exists = repository.findById(solarPanel.getId());
        if (exists == null) {
            result.addErrorMessage("Solar Panel ID: " + solarPanel.getId() + " is not found");
            return result;
        }
        if (exists.getSection() != solarPanel.getSection()) {
            result.addErrorMessage("cannot update Section");
            return result;
        }
        boolean Success = repository.update(solarPanel);
        if (!Success) {
            result.addErrorMessage("could not find Id" + solarPanel.getId());
        }
        return result;
    }

    public SolarPanelResult deleteById (int panelId) throws DataAccessException, FileNotFoundException {
        SolarPanelResult result = new SolarPanelResult();
        SolarPanel solarPanel = repository.findById(panelId);
        if (solarPanel == null) {
            result.addErrorMessage("Could not find SolarPanel Id " +solarPanel);
            return result;
        }

        Map<Sections, Integer> map = sections();
        map.put(solarPanel.getSection(), map.get(solarPanel.getSection()) - 1);
        result = validateDomain(map);
        if (!result.isSuccess()) {
            return result;
        }
        boolean success = repository.deleteById(panelId);
        if (!success){
            result.addErrorMessage("Could not find SolarPanel Id " +solarPanel);
        }
        return result;
    }


    private SolarPanelResult validateInputs(SolarPanel solarPanel){
        SolarPanelResult result = new SolarPanelResult();
        if (solarPanel == null){
            result.addErrorMessage("Solar Panel cannot be null");
            return result;
        }
        if (solarPanel.getId() == -1 ){
            result.addErrorMessage("ID is required");
        }
        return result;
    }
    private Map <Sections,Integer> sections() throws FileNotFoundException {
        HashMap <Sections,Integer> counts = new HashMap<>();
        counts.put(Sections.MAIN,0);
        counts.put(Sections.UPPERHILL,0);
        counts.put(Sections.LOWERHILL,0);
        try{
        List<SolarPanel> allSolarPanels = repository.findAll();
            for (SolarPanel S: allSolarPanels){
                switch(S.getSection()){
                    case MAIN:
                        counts.put(Sections.MAIN,counts.get(Sections.MAIN)+1);
                        break;
                    case UPPERHILL:
                        counts.put(Sections.UPPERHILL,counts.get(Sections.UPPERHILL)+1);;
                        break;
                    case LOWERHILL:
                        counts.put(Sections.LOWERHILL,counts.get(Sections.LOWERHILL)+1);;
                        break;
                }
            }
    }catch (DataAccessException ex){

        }return counts;
}
    private SolarPanelResult validateDomain (Map <Sections,Integer> counts) throws FileNotFoundException, DataAccessException {
        int main = counts.get(Sections.MAIN);
        int upperhill = counts.get(Sections.UPPERHILL);
        int lowerhill = counts.get(Sections.LOWERHILL);
    SolarPanelResult result = new SolarPanelResult();

            if (main > 250*250 ||upperhill > 250*250 || lowerhill>250*250){
                result.addErrorMessage("Not enough Space in this section.");
            }
    return result;
    }

}
