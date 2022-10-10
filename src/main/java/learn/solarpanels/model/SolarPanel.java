package learn.solarpanels.model;

public class SolarPanel {
    private int id;
    private Sections Section;
    private int row;
    private int column;
    private int yearInstalled;
    private boolean isTracking;
    private SolarPanelMaterial material;
    public SolarPanel(){}

    public SolarPanel(int id, Sections section, int row, int column, int yearInstalled, boolean isTracking, SolarPanelMaterial material) {
        this.id = id;
        this.Section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.isTracking = isTracking;
        this.material = material;
    }

    public Sections getSection() {
        return Section;
    }

    public void setSection(Sections section) {
        this.Section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getYearInstalled() {
        return yearInstalled;
    }

    public void setYearInstalled(int yearInstalled) {
        this.yearInstalled = yearInstalled;
    }

    public SolarPanelMaterial getMaterial() {
        return material;
    }

    public void setMaterial(SolarPanelMaterial material) {
        this.material = material;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
