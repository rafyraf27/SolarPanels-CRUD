package learn.solarpanels.domain;

import learn.solarpanels.model.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelResult {
    private ArrayList<String> messages = new ArrayList<>();

    public void addErrorMessage(String message){
        messages.add(message);
    }
    public boolean isSuccess(){
        return messages.size() == 0;
    }

    public void setPayload() {
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}
