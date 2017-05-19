```
public class People{
    public final String name;
    public final int age;
    
    public People(String name, int age){
        this.name = name;
        this.age = age;
    }
}

People[] lst = new People[] {
        new People("John", 29),
        new People("Amy", 30),
        new People("Rick", 29),
        new People("Anna", 28)};

Pipeline<Grouping<Integer, People>> result = Pipeline.from(lst)
        .groupBy(p -> p.age);
//result is {{28,[People("Anna",28)]}
//           {29,[People("Rick",29),People("John",29)]}
//           {30,[People("Amy",30)]}}