import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
//命名解释：
// AmountOfSection 分块的数量
// NumberOfSection 分块的序号
// LengthOfSection 每块的长度
// x 要查找的数字 (沿用题目的命名)
public class Main {
    private static long n = 1000000000;
    private static int AmountOfSection = 10000;
    private static int x = 9;
    private static long ans = 0;

    private static boolean contain( long num, int x ){
        return String.valueOf( num ).contains( String.valueOf( x ) );
    }

//    private static volatile void AddToAns( long x ){
//        ans += x;
//    }
    //synchronized很重要，否则答案错误，但是拖慢了时间

    public static void main( String[] args ) throws Exception{
        Scanner in = new Scanner( System.in );
//        System.out.print("请输入总查询长度n: ");
//        System.out.print("请输入分块数量: ");
//        System.out.print("请输入要查询的数字x: ");
        Thread[] thread = new Thread[AmountOfSection];
        long[] part_ans = new long[AmountOfSection];

        long startTime = System.currentTimeMillis();    //获取开始时间

            long LengthOfSection = n/AmountOfSection;       //块的长度
            for ( int i = 0; i < AmountOfSection; i++ ){
                final int NumberOfSection = i;              //块的序号
                thread[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long NowLength = NumberOfSection * LengthOfSection;
                        for ( long j = 0; j < LengthOfSection; j++ ){
                            if ( contain( (NowLength + j), x ) ) {
//                                AddToAns( NowLength + j );
                                part_ans[NumberOfSection] += NowLength + j;
                            }
                        }
                        System.out.println( NowLength );
                    }
                });
                thread[i].start();
            }
            for( int i = 0;i < AmountOfSection; i++)
            {
                thread[i].join();
                ans += part_ans[i];
            }

        long endTime = System.currentTimeMillis();      //获取结束时间

        System.out.println( "ans = " + ans);
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
