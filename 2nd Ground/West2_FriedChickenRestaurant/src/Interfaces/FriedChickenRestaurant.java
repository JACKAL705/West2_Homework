package Interfaces;

import Exceptions.OverdraftBalanceException;

public interface FriedChickenRestaurant {
    public abstract void SoldSetMeals();   //出售套餐
    public abstract void ShowSetMealList();//显示套餐
    public abstract void BulkPurchase() throws OverdraftBalanceException;   //批量进货
}
