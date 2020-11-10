package Interfaces;

import Exceptions.IngredientSortOutException;
import Exceptions.NotFoundSetMealException;
import Ingredient.Drinks.Beer;
import Ingredient.Drinks.Juice;
import Ingredient.SetMeals.SetMeal;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class West2FriedChickenRestaurant implements FriedChickenRestaurant{
    private double RestaurantBalance;       //餐厅账目余额
    private LinkedList<Beer> BeerList;      //啤酒列表
    private LinkedList<Juice> JuiceList;    //果汁列表
    private final static LinkedList<SetMeal> SetMealList;//套餐列表

    //选用LinkedList是因为，在炸鸡店的场景下，可能随时要在商品列表的头、尾、中间插入/删除元素，
    //这种情况下使用基于链表的LinkedList，相较于使用基于动态数组的ArrayList，要更优，
    //因为在上述操作中，LinkedList只需要改变前后指针的指向，而ArrayList需要移动大量元素，前者在时间复杂度上显然优于后者

    //————————————————————构造函数————————————————————//
    public West2FriedChickenRestaurant(){
        RestaurantBalance = 0.0;
        BeerList = new LinkedList<>();
        JuiceList = new LinkedList<>();
    }
    public West2FriedChickenRestaurant( double RestaurantBalance ){
        this.RestaurantBalance = RestaurantBalance;
    }
    //————————————————————————————————————————————————————————————————————//

    //————————————————————静态代码块————————————————————//
    static{
//        System.out.println( "初始化套餐列表中……" );

        SetMealList = new LinkedList<>();
        SetMealList.add( new SetMeal( "套餐1", 25.0, "椒盐炸鸡", new Beer( "百威", 6.0, LocalDate.now(), (float) 3.1 ) ) );
        SetMealList.add( new SetMeal( "套餐2", 25.0, "蒜香炸鸡", new Beer( "青岛", 6.0, LocalDate.now(), (float) 4.0 ) ) );
        SetMealList.add( new SetMeal( "套餐3", 25.0, "果木香烤鸡", new Juice( "橙汁", 6.0, LocalDate.now() ) ) );

//        System.out.println("套餐初始化完毕!");
    }
    //————————————————————————————————————————————————————————————————————//

    //————————————————————取用成分————————————————————//

    private void use( Beer UsedBeer ) throws IngredientSortOutException{         //取用啤酒
        if( BeerList != null ) for( Beer i:BeerList ){
            if( i.IsOverdue() ) BeerList.remove( i );
        }   //移除过期产品

        boolean FindDrink = false;
        if( BeerList != null ) for(Beer i:BeerList ){
            if( i.getName().equals(UsedBeer.getName()) && i.getAlcoholContent() == UsedBeer.getAlcoholContent()){
                FindDrink = true;
                break;
            }
        }   //找到要出售的产品
        if(!FindDrink) throw new IngredientSortOutException( UsedBeer.getName() );
    }

    private void use( Juice UsedJuice ) throws IngredientSortOutException{       //取用果汁
        if( JuiceList != null ) for( Juice i:JuiceList ){
            if( i.IsOverdue() ) JuiceList.remove( i );
        }   //移除过期产品

        boolean FindDrink = false;
        if( JuiceList != null ) for(Juice i:JuiceList ){
            if( i.getName().equals(UsedJuice.getName())){
                FindDrink = true;
                break;
            }
        }   //找到要出售的产品
        if(!FindDrink) throw new IngredientSortOutException( UsedJuice.getName() );
    }

    private void use( String NameOfSetMeal ) throws NotFoundSetMealException{   //取用套餐
        boolean FindSetMeal = false;
        SetMeal ChosenSetMeal = new SetMeal();

        for( SetMeal i: SetMealList ){
            if( i.MatchName( NameOfSetMeal ) ){
                FindSetMeal = true;//找到套餐
                ChosenSetMeal = i;
                break;
            }
        }

        if( !FindSetMeal ){ //未找到套餐
            throw new NotFoundSetMealException( "未找到所选套餐" );
        }
        else{               //找到套餐
            boolean FindDrink = true;
            RestaurantBalance += ChosenSetMeal.getPrice();
            try{
                if( ChosenSetMeal.getDrink() instanceof Beer ){
                    use( (Beer)ChosenSetMeal.getDrink() );
                }
                if( ChosenSetMeal.getDrink() instanceof Juice ){
                    use( (Juice)ChosenSetMeal.getDrink() );
                }
            } catch ( IngredientSortOutException exception ){
                FindDrink = false;
                System.out.println("抱歉，" + exception.getMessage() + "已售完, 请另选其它套餐");
            }
            if( FindDrink ) System.out.println( "选中" + NameOfSetMeal + ", 祝您用餐愉快!");
        }
    }
    //————————————————————————————————————————————————————————————————————//

    //————————————————————实现炸鸡店接口————————————————————//
    @Override//出售套餐
    public void SoldSetMeals() {
        ShowSetMealList();
        System.out.print( "请输入要选择的套餐编号: " );

        Scanner in = new Scanner( System.in );
        int NumberOfSetMeal = in.nextInt();

        try{
            use( "套餐" + NumberOfSetMeal );
        } catch ( NotFoundSetMealException exception){
            System.out.println( exception.getMessage() + ", 请重新选择" );
        }
    }

    @Override//批量进货
    public void BulkPurchase() {

    }

    @Override//显示套餐
    public void ShowSetMealList() {
        System.out.println( "套餐菜单如下：" );
        for( SetMeal i: SetMealList ){
            System.out.println( i );
        }
    }
    //————————————————————————————————————————————————————————————————————//

    //啤酒：百威、科罗娜、青岛、喜力
    //果汁：橙汁、西瓜汁、椰子汁
}
