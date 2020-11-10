package Ingredient.Drinks;

import java.time.LocalDate;

public class Beer extends Drinks{
    private float AlcoholContent;

    public Beer(){
        super();
        this.AlcoholContent = 0;
    }
    public Beer( String name, double cost, LocalDate ProductionDate, float AlcoholContent ) {
        super( name, cost, ProductionDate, 30 );
        this.AlcoholContent = AlcoholContent;
    }

    @Override
    public String toString() {
        return "Beer{" + '\n' +
                "Name: " + name + '\n' +
                "Alcohol Content: " + AlcoholContent + '\n' +
                "Cost: " + cost + '\n' +
                "Production Date: " + ProductionDate + '\n' +
                "Shelf Life: " + ShelfLife + '}' + '\n';
    }

    public float getAlcoholContent(){
        return AlcoholContent;
    }
}
