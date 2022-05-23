class Writer extends Thread {
    private final SharedData sd;

    Writer(SharedData sd) {
        this.sd = sd;
    }

    public void run() {
        for (int i = 1; i < 50000; i++) {
            int k = (int) (Math.random() * 10000);
            sd.write(k, (int) (Math.random() * 1000));
            sd.stopWrite(k);
        }
    }
}