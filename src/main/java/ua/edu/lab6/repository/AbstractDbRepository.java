package ua.edu.lab6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDbRepository<ENTITY, ID> implements DbRepository<ENTITY, ID> {

    protected final Connection connection;

    public AbstractDbRepository(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    protected abstract String getTableName();
    protected abstract String getCreateTableSql();
    protected abstract String getInsertSql();
    protected abstract String getUpdateSql();
    protected abstract String getSelectByIdSql();
    protected abstract String getSelectAllSql();
    protected abstract String getDeleteSql();

    protected abstract void setInsertParameters(PreparedStatement stmt, ENTITY entity) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement stmt, ENTITY entity) throws SQLException;
    protected abstract void setIdParameter(PreparedStatement stmt, ID id) throws SQLException;
    protected abstract ENTITY mapResultSetToEntity(ResultSet rs) throws SQLException;

    protected void createTableIfNotExists() {
        try (PreparedStatement stmt = connection.prepareStatement(getCreateTableSql())) {
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create table", e);
        }
    }

    @Override
    public ENTITY findById(ID id) {
        try (PreparedStatement stmt = connection.prepareStatement(getSelectByIdSql())) {
            setIdParameter(stmt, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find entity by id", e);
        }
    }

    @Override
    public List<ENTITY> findAll() {
        List<ENTITY> entities = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(getSelectAllSql());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find all entities", e);
        }
    }

    @Override
    public ENTITY save(ENTITY entity) {
        try (PreparedStatement stmt = connection.prepareStatement(getInsertSql())) {
            setInsertParameters(stmt, entity);
            stmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save entity", e);
        }
    }

    @Override
    public ENTITY update(ENTITY entity) {
        try (PreparedStatement stmt = connection.prepareStatement(getUpdateSql())) {
            setUpdateParameters(stmt, entity);
            stmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update entity", e);
        }
    }

    @Override
    public void deleteById(ID id) {
        try (PreparedStatement stmt = connection.prepareStatement(getDeleteSql())) {
            setIdParameter(stmt, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete entity", e);
        }
    }
}