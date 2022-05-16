package my.recipe;

import my.recipe.Recipe;

import java.util.ArrayList;

public class RecipeOut extends Recipe {
    protected ArrayList<String> out = new ArrayList<String>();
    protected ArrayList<Double> outQuantity = new ArrayList<Double>();

    public RecipeOut(String id, String name, String ingredient[], double quantity[], double time, String producers, String out[], double outQuantity[]){
        super(id, name, ingredient, quantity, time, producers);
        for(int i = 0 ; i < out.length ; i++){
            this.out.add(out[i]);
            this.outQuantity.add(outQuantity[i]);
        }
    }

    @Override
    public String toString() {
        String res = "recipeOut id: "+this.id+" name: "+this.name+" time: "+this.time+" ingredients: \n";
        for(int i = 0 ; i < this.ingredient.size() ; i++){
            res +="  -"+ingredient.get(i)+" : "+quantity.get(i)+"\n";
        }
        res += "output :\n";
        for(int i = 0 ; i < this.out.size() ; i++){
            res +="  -"+out.get(i)+" : "+outQuantity.get(i)+"\n";
        }
        return res;
    }

    public ArrayList<String> getOut(){ return this.out; }

    public ArrayList<Double> getOutQuantity(){ return this.outQuantity; }
}
