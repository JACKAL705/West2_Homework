import java.util.Scanner;

//命名解释：
// AmountOfSection 分块的数量
// NumberOfSection 分块的序号
// LengthOfSection 每块的长度
// NowLength 当前分块开始的长度
// n 总查找长度 (沿用题目的命名)
// x 要查找的数字 (沿用题目的命名)
// part_ans 分块答案
// ans 总答案

public class Main {
    private static long n = 1000000000;
    private static int AmountOfSection = 1000;
    private static int x = 9;
    private static volatile long ans = 0;

    private static boolean contain( long num, int x ){
        return String.valueOf( num ).contains( String.valueOf( x ) );
    }

//    private static synchronized void AddToAns( long x ){
//        ans += x;
//    }
    //synchronized很重要，没有它则答案错误（多线程各自持有同一变量的不同副本，导致最后ans每次都输出不同值）
    // 虽然synchronized能使不同线程操作同一个变量，但是同时也会阻塞线程（一个变量在同一时刻只允许一条线程对其操作，即人为把多线程变成单线程）
    //因此弃用，留作笔记

    public static void main( String[] args ) throws Exception{
//        Scanner in = new Scanner( System.in );
//        System.out.print("请输入总查询长度n: "); n = in.nextLong();
//        System.out.print("请输入分块数量AmountOfSection: "); AmountOfSection = in.nextInt();
//        System.out.print("请输入要查询的数字x: "); x = in.nextInt();
        //程序默认设定数值，要更改数值可以在【14~16】行更改，也可以使用【31~34】行输入

        long startTime = System.currentTimeMillis();    //获取开始时间

            Thread[] thread = new Thread[AmountOfSection];      //线程数组
            long[] part_ans = new long[AmountOfSection];        //答案数组
            long LengthOfSection = n/AmountOfSection;       //分块的长度
            for ( int i = 0; i < AmountOfSection; i++ ){
                final int NumberOfSection = i;              //分块的序号
                thread[i] = new Thread(() -> {
                    long NowLength = NumberOfSection * LengthOfSection;     //当前分块开始的长度
                    for ( long j = 0; j < LengthOfSection; j++ ){           //每次遍历分块长度
                        if ( contain( (NowLength + j), x ) ) {
                            part_ans[NumberOfSection] += NowLength + j;     //分块答案（虽然不符合原子性，但是并不影响ans的计算）
                        }
                    }
                });
                thread[i].start();
            }
            for( int i = 0;i < AmountOfSection; i++)
            {
                thread[i].join();       //使计算分块答案的线程计算结束，再执行后续操作
                ans += part_ans[i];
            }

        long endTime = System.currentTimeMillis();      //获取结束时间

        System.out.println( "ans = " + ans);
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}

//多线程编程要考虑3个性质：
//1.原子性：
//    说的是操作的原子性，意思是一个操作要么完成，要么不做，不能是做了一半的状态。
//        ① int x = 0;
//        ② int y = x;
//        ③ y++;
//        其中，①直接把0赋给x，有原子性；②首先读取x的值，然后赋值给y，无原子性；③首先读取y，然后进行加1操作，无原子性；
//2.可见性：
//    一个变量在某个线程里被修改后，应该立刻将改变刷新到其它线程。也就是说如果其它线程需要访问的时候，是访问的修改过后的值。
//3.有序性：
//    程序执行的顺序，不一定按照代码书写的顺序进行执行。而是编译器会对代码进行指令的优化，这样做的目的是为了保证程序执行的效率。
//在单线程中这样做没有太大的问题，编译器保证做过优化之后的代码和没做优化的代码，执行的结果是一样的。但是如果是多线程，这点编译器就无法保证。
