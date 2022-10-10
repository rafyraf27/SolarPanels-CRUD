package learn;

import learn.solarpanel.data.SolarPanelFileRepository;
import learn.solarpanels.domain.SolarPanelService;
import learn.solarpanels.ui.Controller;
import learn.solarpanels.ui.View;

public class App {
    public static void main(String[] args) {
        SolarPanelFileRepository repository = new SolarPanelFileRepository("./data/solarpanels.csv");
        SolarPanelService service = new SolarPanelService(repository);
        View view = new View();
        Controller controller = new Controller(service, view);
        controller.run();
    }
}
