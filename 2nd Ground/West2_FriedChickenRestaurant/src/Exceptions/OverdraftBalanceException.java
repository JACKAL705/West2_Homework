package Exceptions;

//进货费用超出拥有余额
public class OverdraftBalanceException extends RuntimeException{
    public OverdraftBalanceException(){

    }
    public OverdraftBalanceException( String message ){
        super( message );
    }
}
