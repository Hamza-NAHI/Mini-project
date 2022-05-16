package my;

public class Component {
    protected String id;
    protected String name;

    public Component(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Component |id: "+this.id+" |name: "+this.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
