package gift.repository;

import gift.domain.Menu;
import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class MenuRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Menu> menuRowMapper = new RowMapper<Menu>() {
        @Override
        public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Menu(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("imageUrl")
            );
        }
    };


    public MenuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("menus")
                .usingGeneratedKeyColumns("id")
        ;
    }

    public Menu save(Menu menu) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(menu);
        long menuId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Menu(menuId, menu.getName(), menu.getPrice(), menu.getImageUrl());
    }

    public Menu findById(Long id) {
        String sql = "select id, name, price,imageUrl from menus where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                menuRowMapper,
                id
        );
    }

    public List<Menu> findAll() {
        String sql = "select id, name, price,imageUrl from menus";
        List<Menu> menus = jdbcTemplate.query(
                sql,
                menuRowMapper);
        return menus;
    }

    public void update(Long id, Menu menu) {
        String sql = "UPDATE menus SET name = ?, price = ?,imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, menu.getName(), menu.getPrice(), menu.getImageUrl(), id);
    }

    public Long delete(Long id) {
        var sql = "delete from menus where id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }

}
