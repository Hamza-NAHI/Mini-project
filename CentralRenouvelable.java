package my.build;

import my.build.Central;

public class CentralRenouvelable extends Central {
    public CentralRenouvelable(String id, String name, String type, double value){
        super(id, name, type, value);
    }

    @Override
    public String toString(){
        return "CentralRenouvelable |id: "+this.id+" |name: "+this.name+" |type: "+this.type+" |value: "+this.value;
    }
}
