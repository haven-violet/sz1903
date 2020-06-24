package day09;

/**
 * @Author liaojincheng
 * @Date 2020/5/23 10:21
 * @Version 1.0
 * @Description
 */
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    public Student(){
    }

    public Student(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        this.age = age;
    }
}
