package com.migu;

import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 */
public class App 
{
    private static CountDownLatch ctl = new CountDownLatch(200);
    private static final int count = 200;
    private static LockFreeQueue queue = new LockFreeQueue(10);
    private static Thread[] threads = new Thread[count];
    
    public static void main( String[] args ) throws InterruptedException
    {
        for(int i =0; i< count; i++)
        {
            final int j = i;
            threads[i] = new Thread(new Runnable()
            {
                @Override public void run()
                {
                    try
                    {
                        ctl.await();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    boolean add = queue.add(j);
                    if(!add)
                    {
                        if(queue.size() != 10)
                        {
                            System.err.println("queue size is not 10!");
                        }
                    }
                    else
                    {
                        System.out.println("thread[" + j + "] enqueued.");
                    }
                }
            });
            threads[i].start();
            ctl.countDown();
        }

        for(Thread thread : threads)
        {
            thread.join();
        }
        queue.print();
        System.out.println("done.");
    }
}
