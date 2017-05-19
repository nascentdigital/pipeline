```
public class People{
    public final String name;
    public final int age;
    public People(String name, int age){
        this.name = name;
        this.age = age;
    }
}

People[] arr =  new People[] {
        new People("John", 28),
        new People("Amy", 30),
        new People("Rick", 29),
        new People("Jane", 28)};

Map<String, Integer> map = Pipeline.from(arr)
        .toMap(m -> m.name, m -> (m.name + 10));
//map is {John = 38, Rick = 39, Amy = 40, Jane = 38}