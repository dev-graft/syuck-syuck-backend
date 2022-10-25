package devgraft.auth.domain;

public enum AccessStatusType {
    OFFLINE("오프라인", "gray"),
    ONLINE("온라인", "green"),
    EMPTY("자리비움", "yellow"),
    OTHER("다른 용무 중", "red");

    private final String label;
    private final String color;

    AccessStatusType(final String label, final String color) {
        this.label = label;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }
}
