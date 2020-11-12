import Exceptions.OverdraftBalanceException;
import Interfaces.West2FriedChickenRestaurant;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ){
        System.out.println("=== 欢迎来到西二炸鸡店 ===");
        System.out.print("请输入餐厅账目余额: ");

        Scanner in = new Scanner( System.in );
        West2FriedChickenRestaurant restaurant = new West2FriedChickenRestaurant( in.nextDouble() );

        while( true )
        {
            System.out.println("--- 主界面 ---");
            System.out.println("1. 点餐");
            System.out.println("2. 查看套餐");
            System.out.println("3. 进货");
            System.out.println("4. 查看餐厅余额");
            System.out.println("5. 退出系统");
            System.out.print("请输入要进行的操作: ");

            switch ( in.nextInt() ) {
                case 1:{
                    restaurant.SoldSetMeals();
                    break;
                }
                case 2:{
                    restaurant.ShowSetMealList();
                    break;
                }
                case 3:{
                    try {
                        restaurant.BulkPurchase();
                    } catch ( OverdraftBalanceException exception){
                        System.out.println( exception.getMessage() + "无法进货" );
                    }
                    break;
                }
                case 4:{
                    System.out.println("餐厅余额: " + restaurant.GetBalance() );
                    break;
                }
                case 5:{
                    System.out.println( "正在退出系统，祝您度过愉快的一天!" );
                    System.exit(1);
                }
                default:{
                    System.out.println("非法输入!");
                    break;
                }
            }

            System.out.print( "返回主界面请按1, 退出系统请按0: " );
            switch( in.nextInt() ){
                case 0:{
                    System.out.println( "正在退出系统，祝您度过愉快的一天!" );
                    System.exit(1);
                    break;
                }
                case 1:{
                    break;
                }
                default:{
                    System.out.println( "非法输入, 自动返回主界面!" );
                }
            }
        }
    }
}
