package Exceptions;

//未找到套餐
public class NotFoundSetMealException extends Throwable {
    public NotFoundSetMealException(){

    }
    public NotFoundSetMealException( String message ){
        super( message );
    }
}
