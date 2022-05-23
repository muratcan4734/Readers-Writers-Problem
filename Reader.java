class Reader extends Thread {
    private final SharedData sd;

    Reader(SharedData sd) {

        this.sd = sd;
    }

    public void run() {
        for (int i = 1; i < 50000; i++) {
            int k = (int) (Math.random() * 10000);
            sd.read(k);
            sd.stopRead(k);
        }
    }
}
