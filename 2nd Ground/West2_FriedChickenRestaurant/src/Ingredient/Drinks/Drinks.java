package Ingredient.Drinks;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Drinks {
    protected String name;
    protected double cost;
    protected LocalDate ProductionDate;
    protected int ShelfLife;

    protected Drinks() {
        name = "DefaultName";
        cost = 0;
        ProductionDate = LocalDate.now();
        ShelfLife = 0;
    }
    protected Drinks( String name, double cost, LocalDate ProductionDate, int ShelfLife ) {
        this.name = name;
        this.cost = cost;
        this.ProductionDate = ProductionDate;
        this.ShelfLife = ShelfLife;
    }

    public abstract String toString();

    public boolean IsOverdue(){
        long period = ChronoUnit.DAYS.between( ProductionDate, LocalDate.now() );
        if( period > ShelfLife ) return true;   //超过保质期，变质
        else return false;
    }

    public String getName(){
        return name;
    }
}
