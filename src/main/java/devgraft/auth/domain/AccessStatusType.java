package devgraft.auth.domain;

public enum AccessStatusType {
    OFFLINE("오프라인", "gray", 0) {
        @Override
        public boolean isOffline() {
            return true;
        }
    },
    ONLINE("온라인", "green", 4),
    EMPTY("자리비움", "yellow", 3),
    OTHER("다른 용무 중", "red", 2);

    private final String label;
    private final String color;
    private final int codeNum;
    AccessStatusType(final String label, final String color, final int codeNum) {
        this.label = label;
        this.color = color;
        this.codeNum = codeNum;
    }

    public String getLabel() {
        return label;
    }

    public String getColor() {
        return color;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public boolean isOffline() {
        return false;
    }
}
