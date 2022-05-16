package my;

import java.util.ArrayList;

public class Resource extends Component{
    protected ArrayList<String> minedBy =new ArrayList<String>(); //de taille 1

    public Resource(String id, String name, String minedBy){
        super(id,name);
        this.minedBy.add(minedBy);
    }

    @Override
    public String toString() {
        return "Resource |id: "+this.id+" |name: "+this.name+" |minedBy: "+this.minedBy.get(0);
    }

    public ArrayList<String> getMinedBy() {
        return this.minedBy;
    }
}
