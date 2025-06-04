package ua.edu.lab6.repository;

import java.util.List;

public interface DbRepository<ENTITY, ID> {

    ENTITY findById(ID id);

    List<ENTITY> findAll();

    ENTITY save(ENTITY entity);

    ENTITY update(ENTITY entity);

    void deleteById(ID id);
}