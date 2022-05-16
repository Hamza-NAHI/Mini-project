package my.fuel;

import my.Component;

public class Fuel extends Component {
    protected String category;
    protected double value;

    public Fuel(String id, String name, String category ,double value){
        super(id, name);
        this.category = category;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Fuel |id: "+this.id+" |name: "+this.name+" |category: "+this.category+" |value:"+this.value;
    }

    public String getCategory() { return this.category; }

    public double getValue() { return this.value; }
}
