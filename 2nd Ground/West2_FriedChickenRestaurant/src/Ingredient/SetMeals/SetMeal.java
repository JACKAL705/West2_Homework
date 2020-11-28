package Ingredient.SetMeals;

import Ingredient.Drinks.Drinks;
import Ingredient.Drinks.Juice;

public class SetMeal {
    private final String name;
    private final double price;
    private final String FriedChickenName;
    private final Drinks drink;

    public SetMeal(){
        this.name = "DefaultName";
        this.price = 0.0;
        this.FriedChickenName = "DefaultName";
        this.drink = new Juice();
    }
    public SetMeal( String name, double price, String FriedChickenName, Drinks drink ){
        this.name = name;
        this.price = price;
        this.FriedChickenName = FriedChickenName;
        this.drink = drink;
    }

    public boolean MatchName( String name ){
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return "Set Meal{" + '\n' +
                "Name: " + name + '\n' +
                "Price: " + price + '\n' +
                "Fried Chicken Name: " + FriedChickenName + '\n' +
                "Drink: " + drink.getName() + '}' + '\n';
    }

    public double getPrice() {
        return price;
    }

    public Drinks getDrink() {
        return drink;
    }
}
