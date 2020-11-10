package Exceptions;

//果汁或啤酒售完
public class IngredientSortOutException extends Throwable{
    public IngredientSortOutException(){

    }
    public IngredientSortOutException( String message ){
        super( message );
    }
}
