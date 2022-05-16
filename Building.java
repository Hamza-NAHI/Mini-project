package my.build;
import my.Component;
public class Building extends Component {
    public Building(String id , String name){
        super(id, name);
    }

    @Override
    public String toString() {
        return "Building |id: "+this.id+" |name: "+this.name;
    }

}
