public class HW1A {
    private static volatile boolean flag;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!flag) {
                System.out.println("start a new thread count numbers");
                for (int i = 1; i <= 26; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    flag = true;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (flag) {
                System.out.println("start a new thread count alphabets");
                for (char c = 'a'; c <= 'z'; c++) {
                    System.out.println(Thread.currentThread().getName() + " " + c);
                    flag = false;
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
