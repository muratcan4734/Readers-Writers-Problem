public class Question2 {
    public static void main(String[] args) {
        SharedData sd = new SharedData();
        Reader[] read = new Reader[100];
        Writer[] write = new Writer[100];
        int[] data;
        data = new int[100000];
        for (int j = 0; j < data.length; j++)
            data[j] = (int) (Math.random() * 10000);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            read[i] = new Reader(sd);
            write[i] = new Writer(sd);
            read[i].start();
            write[i].start();
        }


        try {
            for (int i = 0; i < 100; i++) {
                read[i].join();
                write[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error:" + e);
        }

        long endTime = System.currentTimeMillis();
        long runningTime = endTime - startTime;
        System.out.println(runningTime + " milliseconds (" + (runningTime / 1000.0) + ")");
    }
}