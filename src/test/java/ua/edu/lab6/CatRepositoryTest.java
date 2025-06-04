package ua.edu.lab6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.lab6.model.Cat;
import ua.edu.lab6.model.CatBehaviour;
import ua.edu.lab6.repository.CatRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatRepositoryTest {

    private Connection connection;
    private CatRepository catRepository;

    @BeforeEach
    void setUp() throws SQLException {
        // Використовуємо in-memory SQLite для тестів
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        catRepository = new CatRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testSaveAndFindById() {
        Cat cat = new Cat("Тест", CatBehaviour.CALM, 1);

        catRepository.save(cat);
        Cat found = catRepository.findById("Тест");

        assertNotNull(found);
        assertEquals("Тест", found.getName());
        assertEquals(CatBehaviour.CALM, found.getBehaviour());
        assertEquals(1, found.getAge());
    }

    @Test
    void testFindAll() {
        Cat cat1 = new Cat("Кіт1", CatBehaviour.CALM, 1);
        Cat cat2 = new Cat("Кіт2", CatBehaviour.CRAZY, 2);

        catRepository.save(cat1);
        catRepository.save(cat2);

        List<Cat> cats = catRepository.findAll();
        assertEquals(2, cats.size());
    }

    @Test
    void testUpdate() {
        Cat cat = new Cat("Тест", CatBehaviour.CALM, 1);
        catRepository.save(cat);

        cat.setAge(2);
        cat.setBehaviour(CatBehaviour.CRAZY);
        catRepository.update(cat);

        Cat updated = catRepository.findById("Тест");
        assertEquals(2, updated.getAge());
        assertEquals(CatBehaviour.CRAZY, updated.getBehaviour());
    }

    @Test
    void testDeleteById() {
        Cat cat = new Cat("Тест", CatBehaviour.CALM, 1);
        catRepository.save(cat);

        catRepository.deleteById("Тест");

        Cat deleted = catRepository.findById("Тест");
        assertNull(deleted);
    }
}