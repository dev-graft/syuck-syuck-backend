package devgraft.member.domain;

public enum MemberStatus {
    N,
    L {
        @Override
        public boolean isLeave() {
            return  true;
        }
    };

    public boolean isLeave() {
        return false;
    }

}
