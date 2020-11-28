package Exceptions;

//未找到套餐
public class NotFoundSetMealException extends RuntimeException {
    public NotFoundSetMealException( String message ){
        super( message );
    }
}
