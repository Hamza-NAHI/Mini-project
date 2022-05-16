package my.build;

import my.ConsumptionException;

import java.util.Objects;

public class Factory extends Building{
    protected double speed;//vitesse de production
    protected String type;
    protected double usage;//kW/s
    protected double drain;//kW/s



    public Factory(String id, String name, double speed, String type, double usage, double drain) {
        super(id, name);
        try {
            if (drain > usage) {
                throw new ConsumptionException(id);
            } else {
                this.speed = speed;
                this.type = type;
                this.usage = usage;
                this.drain = drain;
            }
        } catch (ConsumptionException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "Factory |id: "+this.id+" |name: "+this.name+" |speed: "+this.speed+" |type: "+this.type+" |usage: "+this.usage+" |drain: "+this.drain;
    }

    public double getSpeed() { return speed; }

    public String getType() { return this.type; }

    public double getUsage(){ return this.usage; }

    public double getDrain() { return this.drain; }


}
