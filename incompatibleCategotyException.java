package my;

public class incompatibleCategotyException extends Exception{

    public incompatibleCategotyException(String central, String carburant){
        super(central+" doesn't support the category of "+carburant);
    }
}
