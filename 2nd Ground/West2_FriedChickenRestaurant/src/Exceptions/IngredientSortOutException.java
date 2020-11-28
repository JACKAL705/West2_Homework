package Exceptions;

//果汁或啤酒售完
public class IngredientSortOutException extends RuntimeException{
    public IngredientSortOutException( String message ){
        super( message );
    }
}
