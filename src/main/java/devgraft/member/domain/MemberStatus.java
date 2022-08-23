package devgraft.member.domain;

public enum MemberStatus {
    N {
        @Override
        public boolean isNormal() {
            return true;
        }
    }, L {
        @Override
        public boolean isLeave() {
            return true;
        }
    };

    public boolean isNormal() {
        return false;
    }

    public boolean isLeave() {
        return false;
    }
}
