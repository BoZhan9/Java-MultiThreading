public class HW1 {
    private static final Object lock = new Object();
    private static volatile boolean flag= true;

    static class Multithreading extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                String threadName = Thread.currentThread().getName();
                for (int i = 1; i <= 26; i++) {
                    if (flag) {
                        System.out.println(threadName + ": " + i);
                        flag = false;
                    } else {
                        System.out.println(threadName + ": " + (char) (i + 96));
                        flag = true;
                    }
                    lock.notify();
                    try {
                        if (i < 26) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Multithreading t1 = new Multithreading();
        Multithreading t2 = new Multithreading();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
