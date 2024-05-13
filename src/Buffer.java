public class Buffer {
    private final int[] buffer;
    private int count = 0;
    public Buffer(int size) {
        buffer = new int[size];
    }
    public synchronized void put(int value) {
        while (count == buffer.length) {
            try {
                // 如果缓冲区已满，等待消费者消费
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // 将数据放入缓冲区
        buffer[count] = value;
        count++;
        // 通知消费者有新数据可以消费
        notifyAll();
    }
    public synchronized int get() {
        while (count == 0) {
            try {
                // 如果缓冲区为空，等待生产者生产
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // 从缓冲区取出数据
        int value = buffer[count - 1];
        count--;
        // 通知生产者有空间可以放入新数据
        notifyAll();
        return value;
    }
}