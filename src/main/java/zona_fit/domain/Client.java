package zona_fit.domain;

import java.util.Objects;

public class Client {
    private int id;
    private String name;
    private String lastname;
    private int membership;

    // constructor vacio
    public Client() {}

    public Client(int id) {
        this.id = id;
    }

    public Client(String name, String lastname, int membership) {
        this.name = name;
        this.lastname = lastname;
        this.membership = membership;
    }

    // constructor con todos los parametros
    public Client(int id,String name, String lastname, int membership) {
        this(name, lastname, membership);  // llama al constructor de linea 16
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", membership=" + membership +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && membership == client.membership && Objects.equals(name, client.name) && Objects.equals(lastname, client.lastname);
    }

    // búsqueda y almacenamiento en estructuras de datos basadas en hash
    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, membership);
    }
}
