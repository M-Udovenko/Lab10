package ua.edu.lab6.model;

public class Cat {
    private String name;
    private CatBehaviour behaviour;
    private Integer age;

    public Cat() {}

    public Cat(String name, CatBehaviour behaviour, Integer age) {
        this.name = name;
        this.behaviour = behaviour;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CatBehaviour getBehaviour() { return behaviour; }
    public void setBehaviour(CatBehaviour behaviour) { this.behaviour = behaviour; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return "Cat{name='" + name + "', behaviour=" + behaviour + ", age=" + age + "}";
    }
}