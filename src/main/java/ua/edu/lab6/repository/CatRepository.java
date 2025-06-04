package ua.edu.lab6.repository;

import ua.edu.lab6.model.Cat;
import ua.edu.lab6.model.CatBehaviour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatRepository extends AbstractDbRepository<Cat, String> {

    public CatRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "cats";
    }

    @Override
    protected String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS cats (" +
                "name TEXT PRIMARY KEY, " +
                "behaviour TEXT NOT NULL, " +
                "age INTEGER)";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT OR REPLACE INTO cats (name, behaviour, age) VALUES (?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE cats SET behaviour = ?, age = ? WHERE name = ?";
    }

    @Override
    protected String getSelectByIdSql() {
        return "SELECT * FROM cats WHERE name = ?";
    }

    @Override
    protected String getSelectAllSql() {
        return "SELECT * FROM cats";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM cats WHERE name = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Cat cat) throws SQLException {
        stmt.setString(1, cat.getName());
        stmt.setString(2, cat.getBehaviour().toString());
        stmt.setInt(3, cat.getAge());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Cat cat) throws SQLException {
        stmt.setString(1, cat.getBehaviour().toString());
        stmt.setInt(2, cat.getAge());
        stmt.setString(3, cat.getName()); // WHERE clause
    }

    @Override
    protected void setIdParameter(PreparedStatement stmt, String id) throws SQLException {
        stmt.setString(1, id);
    }

    @Override
    protected Cat mapResultSetToEntity(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        CatBehaviour behaviour = CatBehaviour.valueOf(rs.getString("behaviour"));
        Integer age = rs.getInt("age");
        return new Cat(name, behaviour, age);
    }
}