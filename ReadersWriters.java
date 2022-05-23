import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ReadersWriters {
    private final int[] readersArray;
    private final int[] waitingWritersArray;
    private final boolean[] writingArray;
    private final Lock[] lockArray;
    private final Condition[] wCondArray;
    private final Condition[] rCondArray;

    public ReadersWriters(int sizeOfArray) {
        readersArray = new int[sizeOfArray];
        waitingWritersArray = new int[sizeOfArray];
        writingArray = new boolean[sizeOfArray];
        lockArray = new ReentrantLock[sizeOfArray];
        wCondArray = new Condition[sizeOfArray];
        rCondArray = new Condition[sizeOfArray];
        populateValues(sizeOfArray);
    }


    public void startWrite(int arrayIndex) {
        lockArray[arrayIndex].lock();
        try {
            while (writingArray[arrayIndex] || readersArray[arrayIndex] > 0) {
                try {
                    waitingWritersArray[arrayIndex]++;
                    wCondArray[arrayIndex].await();
                    waitingWritersArray[arrayIndex]--;
                } catch (InterruptedException e) {
                    waitingWritersArray[arrayIndex]--;
                }
            } // !writing && readers == 0
            writingArray[arrayIndex] = true;
        } finally {
            lockArray[arrayIndex].unlock();
        }
    }

    public void stopWrite(int arrayIndex) {
        lockArray[arrayIndex].lock();
        try {
            writingArray[arrayIndex] = false;
            if (waitingWritersArray[arrayIndex] > 0) wCondArray[arrayIndex].signal();
            else rCondArray[arrayIndex].signalAll();
        } finally {
            lockArray[arrayIndex].unlock();
        }
    }

    public void startRead(int arrayIndex) {
        lockArray[arrayIndex].lock();
        try {
            while (writingArray[arrayIndex] || waitingWritersArray[arrayIndex] > 0) {
                try {
                    rCondArray[arrayIndex].await();
                } catch (InterruptedException e) {
                    System.out.println("Error:" + e);
                }
            }
            readersArray[arrayIndex]++;
        } finally {
            lockArray[arrayIndex].unlock();
        }
    }

    public void stopRead(int arrayIndex) {
        lockArray[arrayIndex].lock();
        try {
            readersArray[arrayIndex]--;
            if (readersArray[arrayIndex] == 0) wCondArray[arrayIndex].signal();
        } finally {
            lockArray[arrayIndex].unlock();
        }
    }

    private void populateValues(int sizeOfArray) {
        for (int i = 0; i < lockArray.length; i++) {
            readersArray[i] = 0;
            waitingWritersArray[i] = 0;
            writingArray[i] = false;
            lockArray[i] = new ReentrantLock();
            wCondArray[i] = lockArray[i].newCondition();
            rCondArray[i] = lockArray[i].newCondition();
        }
    }
}
