package kingdom;

import java.util.Objects;

public class Name {
    String name;

    //These two functions are quite important for this equals to work.
    // To generate equals, rightclick -> Generate -> Equals and hashCode.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Name(String n){
        name = n;
    }
}
