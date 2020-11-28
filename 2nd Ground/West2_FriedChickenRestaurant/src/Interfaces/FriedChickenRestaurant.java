package Interfaces;

import Exceptions.OverdraftBalanceException;

public interface FriedChickenRestaurant {
    void SoldSetMeals();   //出售套餐
    void ShowSetMealList();//显示套餐
    void BulkPurchase() throws OverdraftBalanceException;   //批量进货
}
