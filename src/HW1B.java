import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class HW1B {

    public static ReentrantLock rLock = new ReentrantLock();
    public static Condition con1 = rLock.newCondition();
    public static Condition con2 = rLock.newCondition();
    public static boolean flag= true;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            rLock.lock();
            try {
                int n = 0;
                while (n <= 26) {
                    while (!flag) {
                        con1.await();
                    }
                    String threadName = Thread.currentThread().getName();
                    System.out.print("(" + threadName + "): " + n + " ");
                    n++;
                    flag = false;
                    con2.signal();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                rLock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            rLock.lock();
            try {
                char c = 'a';
                while (c <= 'z') {
                    while (flag) {
                        con2.await();
                    }
                    String threadName = Thread.currentThread().getName();
                    System.out.print("(" + threadName + "): " + c + " ");
                    c++;
                    flag = true;
                    con1.signal();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                rLock.unlock();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }
}
