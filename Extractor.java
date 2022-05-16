package my.build;

import my.Resource;
import my.build.Factory;

import java.util.ArrayList;

public class Extractor extends Factory {

    public Extractor(String id, String name, double miningSpeed, String type, double usage, double drain){
        super(id, name, miningSpeed, type, usage, drain);
    }

    public String toString() {
        return "Extractor |id: "+this.id+" |name: "+this.name+" |type: "+this.type+" |usage: "+this.usage+" |drain: "+this.drain+" |miningSpeed: "+this.speed;
    }

    public double getMiningSpeed() { return this.speed; }

}
