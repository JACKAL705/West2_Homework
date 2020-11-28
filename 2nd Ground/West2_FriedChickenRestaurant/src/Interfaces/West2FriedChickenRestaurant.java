package Interfaces;

import Exceptions.IngredientSortOutException;
import Exceptions.NotFoundSetMealException;
import Exceptions.OverdraftBalanceException;
import Ingredient.Drinks.Beer;
import Ingredient.Drinks.Drinks;
import Ingredient.Drinks.Juice;
import Ingredient.SetMeals.SetMeal;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class West2FriedChickenRestaurant implements FriedChickenRestaurant{
    private double RestaurantBalance;       //餐厅账目余额
    private final LinkedList<Beer> BeerList;      //啤酒列表
    private final LinkedList<Juice> JuiceList;    //果汁列表
    private final static LinkedList<SetMeal> SetMealList;//套餐列表

    //选用LinkedList是因为，在炸鸡店的场景下，可能随时要在商品列表的头、尾、中间插入/删除元素，
    //这种情况下使用基于链表的LinkedList，相较于使用基于动态数组的ArrayList，要更优，
    //因为在上述操作中，LinkedList只需要改变前后指针的指向，而ArrayList需要移动大量元素，前者在时间复杂度上优于后者

    //————————————————————构造函数————————————————————//
    public West2FriedChickenRestaurant( double RestaurantBalance ){     //有参
        this.RestaurantBalance = RestaurantBalance;
        BeerList = new LinkedList<>();
        JuiceList = new LinkedList<>();
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
        BeerList.removeIf(Drinks::IsOverdue);       //移除过期产品

        boolean FindDrink = false;
        for(Beer i:BeerList ){
            if( i.getName().equals(UsedBeer.getName()) && i.getAlcoholContent() == UsedBeer.getAlcoholContent()){
                BeerList.remove( i );
                FindDrink = true;
                break;
            }
        }   //找到要出售的产品
        if(!FindDrink) throw new IngredientSortOutException( UsedBeer.getName() );
    }

    private void use( Juice UsedJuice ) throws IngredientSortOutException{       //取用果汁
        JuiceList.removeIf(Drinks::IsOverdue);      //移除过期产品

        boolean FindDrink = false;
        for(Juice i:JuiceList ){
            if( i.getName().equals(UsedJuice.getName())){
                JuiceList.remove( i );
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
            try{
                if( ChosenSetMeal.getDrink() instanceof Beer ){     //饮料是啤酒
                    use( (Beer)ChosenSetMeal.getDrink() );
                }
                if( ChosenSetMeal.getDrink() instanceof Juice ){    //饮料是果汁
                    use( (Juice)ChosenSetMeal.getDrink() );
                }
            } catch ( IngredientSortOutException exception ){
                FindDrink = false;
                System.out.println("抱歉，" + exception.getMessage() + "已售完, 请另选其它套餐");
            }
            if( FindDrink ){
                RestaurantBalance += ChosenSetMeal.getPrice();  //收银
                System.out.println( "选中" + NameOfSetMeal + ", 祝您用餐愉快!");
            }
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
    public void BulkPurchase() throws OverdraftBalanceException {
        Scanner in = new Scanner( System.in );

        while( true ){
            System.out.println("--- 进货界面 ---");
            System.out.println("1. 进货饮料");
            System.out.println("2. 退出进货界面");
            System.out.print("请输入要进行的操作序号: ");
            switch (in.nextInt()) {
                case 1 -> {
                    System.out.println("饮料种类选择：");
                    System.out.println("1. 啤酒");
                    System.out.println("2. 果汁");
                    System.out.print("请输入要进货的种类: ");
                    //啤酒：百威、青岛
                    //果汁：橙汁、西瓜汁
                    switch (in.nextInt()) {
                        case 1 -> {
                            System.out.println("啤酒有以下几类：");
                            System.out.println("1. 百威( 3.1%vol )  进价: 6.0元");
                            System.out.println("2. 青岛( 4.0%vol )  进价: 6.0元");
                            System.out.print("请输入要进货的种类、数量: ");
                            int order = in.nextInt(), amount = in.nextInt();
                            switch (order) {
                                case 1 -> {
                                    if (RestaurantBalance >= 6.0 * amount) RestaurantBalance -= 6.0 * amount;
                                    else {
                                        throw new OverdraftBalanceException("餐厅余额不足, 还缺" + (6.0 * amount - RestaurantBalance) + "元, ");
                                    }
                                    while ((amount--) > 0) {
                                        System.out.print("生产日期(年 月 日): ");
                                        LocalDate ProductionDate = LocalDate.of(in.nextInt(), in.nextInt(), in.nextInt());
                                        BeerList.add(new Beer("百威", 6.0, ProductionDate, (float) 3.1));
                                    }
                                }
                                case 2 -> {
                                    if (RestaurantBalance >= 6.0 * amount) RestaurantBalance -= 6.0 * amount;
                                    else {
                                        throw new OverdraftBalanceException("餐厅余额不足, 还缺" + (6.0 * amount - RestaurantBalance) + "元, ");
                                    }
                                    while ((amount--) > 0) {
                                        System.out.print("生产日期(年 月 日): ");
                                        LocalDate ProductionDate = LocalDate.of(in.nextInt(), in.nextInt(), in.nextInt());
                                        BeerList.add(new Beer("青岛", 6.0, ProductionDate, (float) 4.0));

                                    }
                                }
                                default -> System.out.println("非法输入!");
                            }
                        }
                        case 2 -> {
                            System.out.println("果汁有以下几类：");
                            System.out.println("1. 橙汁  进价: 6.0元");
                            System.out.println("2. 西瓜汁  进价: 6.0元");
                            System.out.print("请输入要进货的种类、数量: ");
                            int order = in.nextInt(), amount = in.nextInt();
                            switch (order) {
                                case 1 -> {
                                    if (RestaurantBalance >= 6.0 * amount) RestaurantBalance -= 6.0 * amount;
                                    else {
                                        throw new OverdraftBalanceException("餐厅余额不足, 还缺" + (6.0 * amount - RestaurantBalance) + "元, ");
                                    }
                                    while ((amount--) > 0) {
                                        System.out.print("生产日期(年 月 日): ");
                                        LocalDate ProductionDate = LocalDate.of(in.nextInt(), in.nextInt(), in.nextInt());
                                        JuiceList.add(new Juice("橙汁", 6.0, ProductionDate));
                                    }
                                }
                                case 2 -> {
                                    if (RestaurantBalance >= 6.0 * amount) RestaurantBalance -= 6.0 * amount;
                                    else {
                                        throw new OverdraftBalanceException("餐厅余额不足, 还缺" + (6.0 * amount - RestaurantBalance) + "元, ");
                                    }
                                    while ((amount--) > 0) {
                                        System.out.print("生产日期(年 月 日): ");
                                        LocalDate ProductionDate = LocalDate.of(in.nextInt(), in.nextInt(), in.nextInt());
                                        JuiceList.add(new Juice("西瓜汁", 6.0, ProductionDate));
                                    }
                                }
                                default -> System.out.println("非法输入!");
                            }
                        }
                        default -> System.out.println("非法输入!");
                    }
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println("非法输入!");
            }

            System.out.print("继续进货请按1, 完成进货请按0: ");
            switch (in.nextInt()) {
                case 0 -> {
                    System.out.println("进货完毕!");
                    return;
                }
                case 1 -> System.out.println("继续进货……");
                default -> System.out.println("非法输入, 自动返回进货界面!");
            }
        }

    }

    @Override//显示套餐
    public void ShowSetMealList() {
        System.out.println( "套餐菜单如下：" );
        for( SetMeal i: SetMealList ){
            System.out.println( i );
        }
    }
    //————————————————————————————————————————————————————————————————————//

    //————————————————————SET/GET函数————————————————————//
    public double GetBalance(){
        return RestaurantBalance;
    }
    //————————————————————————————————————————————————————————————————————//
}
