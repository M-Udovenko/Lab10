package ua.edu.lab6;

import ua.edu.lab6.model.Cat;
import ua.edu.lab6.model.CatBehaviour;
import ua.edu.lab6.repository.CatRepository;
import ua.edu.lab6.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Тестування DbRepository");

        try (Connection connection = DatabaseConnection.getSqliteConnection()) {
            CatRepository catRepository = new CatRepository(connection);

            Cat cat1 = new Cat("Мурзик", CatBehaviour.CALM, 3);
            Cat cat2 = new Cat("Барсик", CatBehaviour.CRAZY, 2);
            Cat cat3 = new Cat("Васька", CatBehaviour.NORMAL, 5);

            System.out.println("\n1. Збереження котів:");
            catRepository.save(cat1);
            catRepository.save(cat2);
            catRepository.save(cat3);
            System.out.println("Коти збережені!");

            System.out.println("\n2. Всі коти в базі:");
            List<Cat> allCats = catRepository.findAll();
            allCats.forEach(System.out::println);

            System.out.println("\n3. Пошук кота 'Мурзик':");
            Cat foundCat = catRepository.findById("Мурзик");
            System.out.println(foundCat);

            System.out.println("\n4. Оновлення віку Мурзика:");
            foundCat.setAge(4);
            foundCat.setBehaviour(CatBehaviour.CRAZY);
            catRepository.update(foundCat);
            System.out.println("Оновлено: " + catRepository.findById("Мурзик"));

            System.out.println("\n5. Видалення Барсика:");
            catRepository.deleteById("Барсик");
            System.out.println("Залишилось котів: " + catRepository.findAll().size());

            System.out.println("\n6. Фінальний список котів:");
            catRepository.findAll().forEach(System.out::println);

        } catch (SQLException e) {
            System.err.println("Помилка роботи з базою даних: " + e.getMessage());
        }
    }
}