import lombok.Data;

@Data
public class Student {
    private Integer id;
    private String name;
    private String sex;
    private Integer phone;
    private Integer age;
    private String hobby;

    public Student(Integer id, String name, String sex, Integer phone, Integer age, String hobby) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.age = age;
        this.hobby = hobby;
    }

    public Student (){}

}
