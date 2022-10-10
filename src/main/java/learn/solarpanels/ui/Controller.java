package learn.solarpanels.ui;

import learn.solarpanel.data.DataAccessException;
import learn.solarpanels.domain.SolarPanelResult;
import learn.solarpanels.domain.SolarPanelService;
import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;

import java.io.FileNotFoundException;
import java.util.List;

public class Controller {

    private final SolarPanelService service;
    private final View view;

    public Controller(SolarPanelService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            runMenu();
        } catch (DataAccessException | FileNotFoundException ex) {
            view.Header("Error" + ex);

        }
    }
    private void runMenu() throws FileNotFoundException, DataAccessException {
        MenuOption option;
        do {
            option = view.display();

            switch (option){
                case EXIT:
                    view.Header("GoodBye");
                    break;
                case SECTION:
                    findPanel();
                    break;
                case ADD:
                    addPanel();
                    break;
                case UPDATE:
                    updatePanel();
                    break;
                case REMOVE:
                    removePanel();
                    break;
}
        }while(option != MenuOption.EXIT);
    }
    private void findPanel() throws FileNotFoundException, DataAccessException {
        view.Header(MenuOption.SECTION.getTitle());
        Sections sections = view.readSection();
        List<SolarPanel> solarPanels = service.findByType(sections);
        view.showSolarPanels(solarPanels);
    }
    private void addPanel() throws FileNotFoundException, DataAccessException {
        view.Header(MenuOption.ADD.getTitle());
        SolarPanel solarPanel = view.makeSolarPanel();
        SolarPanelResult result = service.add(solarPanel);
        view.showResult(result);

    }
    private void updatePanel() throws FileNotFoundException, DataAccessException {
        view.Header(MenuOption.UPDATE.getTitle());
        Sections sections = view.readSection();
        List<SolarPanel> solarPanels = service.findByType(sections);
        SolarPanel solarPanel = view.update(solarPanels);
        if (solarPanel == null){
            return;
        }
        SolarPanelResult result = service.update(solarPanel);
        view.showResult(result);
    }
    private void removePanel() throws FileNotFoundException, DataAccessException {
        view.Header(MenuOption.REMOVE.getTitle());
        Sections sections = view.readSection();
        List<SolarPanel> solarPanels = service.findByType(sections);
        SolarPanel solarPanel= view.delete(solarPanels);
    if (solarPanel==null){
        return;
    }
        SolarPanelResult result = service.deleteById(solarPanel.getId());
        view.showResult(result);
}
}
