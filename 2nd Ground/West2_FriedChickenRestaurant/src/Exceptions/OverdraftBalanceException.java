package Exceptions;

//进货费用超出拥有余额
public class OverdraftBalanceException extends Throwable{
    public OverdraftBalanceException(){

    }
    public OverdraftBalanceException( String message ){
        super( message );
    }
}
