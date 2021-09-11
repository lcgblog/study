package introspector;

public class Person {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        System.out.println("Set age " + age);
        this.age = age;
    }

    @Override
    public String toString() {
        return "introspector.Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
