public class BankAccount {
    private double balance;

    // 使用synchronized关键字修饰方法，实现线程同步
    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " 存入: " + amount);
            System.out.println("当前余额为: " + balance);
        }
    }

    // 使用synchronized关键字修饰方法，实现线程同步
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " 取出: " + amount);
            System.out.println("当前余额为: " + balance);
        } else {
            System.out.println("余额不足");
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // 创建两个线程分别进行存款和取款操作
        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
                try {
                    Thread.sleep(100); // 模拟耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "存款线程");

        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(200);
                try {
                    Thread.sleep(100); // 模拟耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "取款线程");

        // 启动线程
        depositThread.start();
        withdrawThread.start();
    }
}