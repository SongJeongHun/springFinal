package com.example.test.spring0525.ex9_4;
import java.util.List;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.springframework.jdbc.core.*;

public class MemberDao {
    private JdbcTemplate jdbcTemplate;
    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); }
    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query("select * from MEMBER",
                (ResultSet rs, int rowNum) -> {
                    Member member = new Member( rs.getString("EMAIL"), rs.getString("PASSWORD"),
                            rs.getString("NAME"), rs.getTimestamp("REGDATE").toLocalDateTime());
                    member.setId(rs.getLong("ID"));
                    return member;
                }); return results;
    }
}
