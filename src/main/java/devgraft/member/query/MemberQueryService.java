package devgraft.member.query;

import devgraft.member.domain.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberQueryService {
    private final JdbcTemplate jdbcTemplate;

    public Optional<MemberData> getMemberData(final String loginId) {
        return jdbcTemplate.query(
                """
                            select * from MEMBER m where m.login_id=? and m.status=?
                        """, memberDataRowMapper(), loginId, MemberStatus.N.name()).stream().limit(1L).findFirst();
    }

    private RowMapper<MemberData> memberDataRowMapper() {
        return ((rs, rowNum) ->
                MemberData.builder()
                        .id(rs.getLong("id"))
                        .loginId(rs.getString("login_id"))
                        .password(rs.getString("password"))
                        .nickname(rs.getString("nickname"))
                        .profileImage(rs.getString("profile_image"))
                        .stateMessage(rs.getString("state_message"))
                        .status(MemberStatus.valueOf(rs.getString("status")))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build());
    }
}
