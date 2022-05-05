package qa.quru.domain;

public enum MenuItem {
    VINO("ВИНО"),
    KONYAK("КОНЬЯК");

    public final String rusName;

    MenuItem(String rusName) {
        this.rusName = rusName;
    }
}