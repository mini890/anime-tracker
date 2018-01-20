package miguelsilva.a029187.pokemoncarddatabase.JavaClasses;

import java.io.Serializable;

/**
 * Created by migue on 20/01/2018.
 */

public class Card implements Serializable {

    protected String id, name;

    public Card(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Card() {
        this.id = "bw11-RC24";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}