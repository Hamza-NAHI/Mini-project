package my.build;
import my.Component;
import my.fuel.*;
import my.incompatibleCategotyException;

public class CentralCarburant extends Central {
    protected String category;
    protected double speed;

    public CentralCarburant(String id, String name , String type, double value ,String category ,double speed){
        super(id, name, type, value);
        this.category = category;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "CentralCarburant |id: "+this.id+" |name: "+this.name+" |type: "+this.type+" |value: "+this.value+" |category: "+this.category+" |speed: "+this.speed;
    }

    public void inputFuel(Component f) throws incompatibleCategotyException{
        try {
            if((f instanceof Fuel) || (f instanceof ResourceFuel) && this.getCategory().equals(((ResourceFuel) f).getCategory())){
                System.out.println("ok");
            } else throw new incompatibleCategotyException(this.getId() , f.getId());
        } catch (incompatibleCategotyException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getCategory() { return this.category; }

    public double getSpeed() { return this.speed; }


    //pour assurer la coherence entre le carburant et le central
    public void central_carburant_category(Fuel carburant) throws incompatibleCategotyException {
        if(this.category.equals(carburant.getCategory())) throw new incompatibleCategotyException(this.id,carburant.getId());
    }
    public void central_carburant_category(ResourceFuel carburant) throws incompatibleCategotyException {
        if(this.category.equals(carburant.getCategory())) throw new incompatibleCategotyException(this.id,carburant.getId());
    }
}
