package Ingredient.Drinks;

import java.time.LocalDate;

public class Juice extends Drinks{
    public Juice(){
        super();
    }
    public Juice( String name, double cost, LocalDate ProductionDate ){
        super( name, cost, ProductionDate, 2 );
    }

    @Override
    public String toString() {
        return "Juice{" + '\n' +
                "Name: " + name + '\n' +
                "Cost: " + cost + '\n' +
                "Production Date: " + ProductionDate + '\n' +
                "Shelf Life: " + ShelfLife + '}' + '\n';
    }
}
