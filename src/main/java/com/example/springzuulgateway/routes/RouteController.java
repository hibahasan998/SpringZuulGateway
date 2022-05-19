package com.example.springzuulgateway.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    List<Route> routes;

    @PostConstruct
    @Cacheable("routes")
    public List<Route> getRoutes(){
        String sql_query = "SELECT * FROM public.\"Routes\";";
        this.routes=this.jdbcTemplate.query(sql_query, new RouteMapper());
        System.out.println(this.routes);
        return this.routes;
    }

    private static final class RouteMapper implements RowMapper<Route> {
        public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
            Route route = new Route();
            route.setId(rs.getInt("id"));
            route.setName(rs.getString("name"));
            return route;
        }
    }
}