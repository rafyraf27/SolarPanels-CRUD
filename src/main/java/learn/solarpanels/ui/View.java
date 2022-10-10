package learn.solarpanels.ui;

import learn.solarpanels.domain.SolarPanelResult;
import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;

import java.util.List;
import java.util.Scanner;

import static java.time.chrono.JapaneseEra.values;

public class View {
    private final Scanner console = new Scanner(System.in);

    public MenuOption display() {
        MenuOption[] values = MenuOption.values();
        Header("Main Menu");
        for (int i = 0; i < MenuOption.values().length; i++) {
            System.out.printf("%s. %s%n", i, values[i].getTitle());
        }
        int index = readInt("Select [0-4]: ", 0, 4);

        return values[index];
    }

    public void Header(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("_".repeat(message.length()));
    }

    public void showSolarPanels(List<SolarPanel> solarPanels) {
        Header("Solar Panels: ");
        if (solarPanels.size() == 0) {
            System.out.println("No Solar Panels Found");
        } else {
            for (SolarPanel s : solarPanels) {
                System.out.printf("Id: %s| Section: %s| Row: %s| Column: %s| Year Installed: %s| Tracking: %s| Material: %s "
                        , s.getId(), s.getSection(), s.getRow(), s.getColumn(), s.getYearInstalled(), s.isTracking(), s.getMaterial());
                System.out.println("");
            }
        }
    }

    public void showResult(SolarPanelResult result) {
        if (result.isSuccess()) {
            Header("Success!");
        } else {
            Header("Error:");
            for (String err : result.getMessages()) {
                System.out.println(err);
            }
        }
    }

    public SolarPanel makeSolarPanel() {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection(readSection());
        solarPanel.setRow(readInt("Row: ", 1, 17));
        solarPanel.setColumn(readInt("Column: ", 1, 17));
        solarPanel.setYearInstalled(readInt("Year Installed: ", 1, 2023));
        solarPanel.setTracking(tracking("Tracking: "));
        solarPanel.setMaterial(readMaterial());
        return solarPanel;
    }

    public SolarPanel update(List<SolarPanel> solarPanels) {
        showSolarPanels(solarPanels);
        if (solarPanels.size() == 0) {
            return null;
        }
        int PanelId = readInt("SolarPanel Id: ");
        for (SolarPanel s : solarPanels) {
            if (s.getId() == PanelId) {
                return update(s);
            }
        }
        System.out.println("Id " + PanelId + " not found");
        return null;
    }
    public SolarPanel delete(List<SolarPanel> solarPanels) {
        showSolarPanels(solarPanels);
        if (solarPanels.size() == 0) {
            return null;
        }
        int PanelId = readInt("SolarPanel Id: ");
        for (SolarPanel s : solarPanels) {
            if (s.getId() == PanelId) {
                return s;
            }
        }
        System.out.println("Id " + PanelId + " not found");
        return null;
    }

        public SolarPanel update(SolarPanel solarPanel){
            int row = readInt("Row: (" + solarPanel.getRow() + "): ");
            if (row!=0 || row<250){
                solarPanel.setRow(row);
            }
            int column = readInt("Column: (" + solarPanel.getColumn() + "): ");
            if (column!=0 || column<250){
                solarPanel.setColumn(column);
            }
            int year = readInt("Year Installed: (" + solarPanel.getYearInstalled() + "): ");
            if (year<=2022){
                solarPanel.setYearInstalled(year);
            }
            boolean tracking = Boolean.parseBoolean(readString("Tracking: (" + solarPanel.isTracking() + "): "));
            if (tracking!=true||tracking!=false){
                solarPanel.setTracking(tracking);
            }
            return solarPanel;
    }
    public Sections readSection(){
        System.out.println("Section: ");
        Sections[] values = Sections.values();
        for (int i = 0;  i<values.length ; i++){
            System.out.printf("%s. %s%n",i,values[i]);
        }
        int index = readInt("Select [0-2]: ",0,2 );
        return values[index];
    }
    public SolarPanelMaterial readMaterial(){
        System.out.println("Material: ");
        SolarPanelMaterial[] values = SolarPanelMaterial.values();
        for (int i = 0;  i<values.length ; i++){
            System.out.printf("%s. %s%n",i,values[i]);
        }
        int index = readInt("Select [0-4]: ",0,4);
        return values[index];
    }

    private Boolean tracking (String prompt){
        System.out.print(prompt);
        return console.nextBoolean();
    }
private String readString (String prompt){
    System.out.print(prompt);
    return console.nextLine();
}
    private String requireString (String prompt) {
        String result = null;
        do {
            result = readString(prompt).trim();
            if (result.length() == 0){
                System.out.println("value is required");
            }
        } while (result.length() == 0);
        return result;
    }
    private int readInt (String prompt){
int result =0;
boolean isvalid = false;
do {
    String value = requireString(prompt);
    try{
        result = Integer.parseInt(value);
        isvalid = true;
    }catch(NumberFormatException ex){
        System.out.println("Value must be a number");
    }
}while(!isvalid);
return result;
    }
    private int readInt(String prompt,int min,int max){
        int result = 0;
        do {
            result = readInt(prompt);
            if (result<min || result>max){
                System.out.printf("value must be between %s and %s.%n", min, max);
            }
        }while (result<min || result>max);
        return result;
    }

}
