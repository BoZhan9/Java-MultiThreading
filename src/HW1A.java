public class HW1A {

    // Flag always be true. t1 will run till the end, and t2 won't get any chance to run
    private static volatile boolean flag = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (flag) {
                System.out.println("start a new thread count numbers");
                for (int i = 1; i <= 26; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    flag = false;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (!flag) {
                System.out.println("start a new thread count alphabets");
                for (char c = 'a'; c <= 'z'; c++) {
                    System.out.println(Thread.currentThread().getName() + " " + c);
                    flag = true;
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
