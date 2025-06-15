package com.lcgblog.study.springboot.domain.mapper;

import com.lcgblog.study.springboot.domain.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

@Component
public class UserMapper {

    public static final String TABLE_NAME = "register_user";
    public static final String COLUMNS = "id, username, password, comment, created_time, updated_time, last_login_time";
    public static final String INSERT_COLUMNS = "username, password, comment, created_time, updated_time, last_login_time";
    public static final String PRIMARY_KEY_COLUMN = "id";

    public final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .comment(rs.getString("comment"))
                    .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                    .updatedTime(rs.getTimestamp("updated_time").toLocalDateTime())
                    .lastLoginTime(rs.getTimestamp("last_login_time").toLocalDateTime())
                    .build();
        }
    };

    public final Function<User, Map<String, Object>> dtoMapper = user -> Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "comment", user.getComment(),
            "createdTime", user.getCreatedTime(),
            "lastLoginTime", user.getLastLoginTime(),
            "updatedTime", user.getUpdatedTime()
    );

    public String getInsertSql() {
        return String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?)",
                TABLE_NAME, INSERT_COLUMNS);
    }

    public Object[] getInsertParams(User user, LocalDateTime now) {
        return new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getComment(),
                now,
                now,
                now
        };
    }

    public String getDeleteSql() {
        return String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, PRIMARY_KEY_COLUMN);
    }

    public Object[] getDeleteParams(long id) {
        return new Object[]{id};
    }

    public String getUpdateSql() {
        return String.format("UPDATE %s set comment = ?, last_login_time = ?, updated_time = ? where id = ?", TABLE_NAME);
    }

    public Object[] getUpdateParams(User user) {
        return new Object[]{
                user.getComment(),
                user.getLastLoginTime(),
                user.getUpdatedTime(),
                user.getId()
        };
    }

    public String getSelectAllSql() {
        return String.format("SELECT %s FROM %s", COLUMNS, TABLE_NAME);
    }

    public String getSelectByIdSql() {
        return String.format("SELECT %s FROM %s where %s = ?", COLUMNS, TABLE_NAME, PRIMARY_KEY_COLUMN);
    }

    public Object[] getSelectByIdParams(long id) {
        return new Object[]{id};
    }

}