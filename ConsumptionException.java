package my;

import my.build.Factory;

public class ConsumptionException extends Exception{

    public ConsumptionException(String id) {
        super(id+" consommerait plus au repos qu’il ne le fait au travail");
    }
}
