package com.sparta.spartime.repository;

import com.sparta.spartime.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchInsert(List<Post> posts) {
        // sequence 테이블은 동기화가 안되는 이슈가 있음
        jdbcTemplate.batchUpdate(
                "insert into posts(post_id, title, contents, likes, user_id, type)" +
                        "values(?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, i+1);
                        ps.setString(2, posts.get(i).getTitle());
                        ps.setString(3, posts.get(i).getContents());
                        ps.setLong(4, posts.get(i).getLikes());
                        ps.setLong(5, posts.get(i).getUser().getId());
                        ps.setString(6, posts.get(i).getType().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return posts.size();
                    }
                }
        );
    }
}
