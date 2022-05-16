package my.build;

public abstract class Central extends Building{
    protected String type;
    protected double value;

    protected Central(String id, String name, String type, double value){
        super(id,name);
        this.type = type;
        this.value = value;
    }

    public String getType(){ return this.type; }

    public double getValue(){ return this.value; }
}
