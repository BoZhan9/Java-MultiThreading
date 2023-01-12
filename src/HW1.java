public class HW1 {
    // private static final Object lock = new Object();
    public static boolean flag= true;

    public static class Multithreading extends Thread {
        @Override
        public void run() {
            synchronized (Multithreading.class) {
                String threadName = Thread.currentThread().getName();
                for (int i = 1; i <= 26; i++) {
                    if (flag) {
                        System.out.print("(" + threadName + "): " + i + " ");
                        flag = false;
                    } else {
                        System.out.print("(" + threadName + "): " + (char) (i + 96) + " ");
                        flag = true;
                    }
                    Multithreading.class.notify();
                    try {
                        if (i < 26) {
                            Multithreading.class.wait();
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

/*

Sample output:

(Thread-0): 1 (Thread-1): a (Thread-0): 2 (Thread-1): b ... (Thread-0): 26 (Thread-1): z

Process finished with exit code 0

 */