package my.build;

import my.Resource;

import java.util.ArrayList;

public class ExtractorWithoutElec extends Building{
    protected double miningSpeed;

    public ExtractorWithoutElec(String id, String name, double miningSpeed){
        super(id, name);
        this.miningSpeed = miningSpeed;
    }

    @Override
    public String toString(){
        return "ExtractorWithoutElec |id: "+this.id+" |name: "+this.name+" |miningSpeed: "+this.miningSpeed;
    }

    public double getMiningSpeed() { return this.miningSpeed; }

}
