package my.recipe;

import my.Component;

import java.util.ArrayList;

public class Recipe extends Component {
    protected ArrayList<String> ingredient = new ArrayList<String>();
    protected ArrayList<Double> quantity = new ArrayList<Double>();
    protected double time;
    protected String producers;

    public Recipe(String id, String name, String[] ingredient , double quantity[], double time, String producers){
        super(id, name);
        for(int i=0 ; i<ingredient.length ; i++) {
            this.ingredient.add(ingredient[i]);
            this.quantity.add(quantity[i]);
        }
        this.time = time;
        this.producers = producers;
    }

    @Override
    public String toString() {
        String res = "recipe id: "+this.id+" name: "+this.name+" time: "+this.time+" ingredients: \n";
        for(int i = 0 ; i < this.ingredient.size() ; i++){
            res +="  -"+ingredient.get(i)+" : "+quantity.get(i)+"\n";
        }
        res += "produit : "+this.getName();
        return res;
    }

    public ArrayList<String> getIngredient(){ return this.ingredient;}

    public ArrayList<Double> getQuantity(){ return this.quantity;}

    public double getTime(){ return this.time;}

    public String getProducers(){ return this.producers;}
}
