package learn.solarpanels.ui;

public enum MenuOption {
    EXIT("Exit"),
    SECTION("Find Panels by Section"),
    ADD("Add a Panel"),
    UPDATE("Update a Panel"),
    REMOVE("Remove a Panel")
    ;
    private String title;
    MenuOption(String title) {
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
}
