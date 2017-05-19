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

Map<String, People> map = Pipeline.from(arr)
        .toMap(m -> {
            String key = m.name + m.age;
            return key;
            });
//map is {Amy30 = People("Amy",30),
//       Jane28 = People("Jane",28),
//       John28 = People("John",28),
//       Rick29 = People("Rick",29)};