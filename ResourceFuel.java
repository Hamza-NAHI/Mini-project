package my.fuel;

import my.Resource;

import java.util.ArrayList;

public class ResourceFuel extends Resource {
    protected String category;
    protected double value;

    public ResourceFuel(String id, String name, String minedBy[], String category, double value){
        super(id, name, minedBy[0]);

        for(int i = 1 ; i < minedBy.length ; i++) {
            this.minedBy.add(minedBy[i]);
        }
        this.category = category;
        this.value = value;
    }

    @Override
    public String toString() {
        String res = "ResourceFuel |id: "+this.id+" |name: "+this.name+" |category: "+this.category+" |value: "+this.value+" |minedBy: ";
        for(int i = 0 ; i < minedBy.size() ; i++){
            res += minedBy.get(i)+" ,";
        }
        return res;
    }

    public String getCategory() { return this.category; }

    public double getValue() { return this.value; }
    @Override
    public ArrayList<String> getMinedBy() {
        return this.minedBy;
    }
}
