# Readers-Writers-Problem
Parallel and Distributed Programming Java

Readers/Writers Problem – Write a fine grained solution control that manages access to a given data set. There are two kinds of threads that require access. Readers that need to access information and writers that need to update the data set. The control must enforce the following list of rules:
• only allow one writer at a time;
• readers must be blocked when a writer is writing;
• writers must be blocked when readers are reading;
• allow multiple concurrent readers;
• Give writers precedence over readers.
The coarse-grained solution is attached, you need to re-write it to add more concurrency to reader/writers’ threads. Your test program should have 100 readers threads and 100 writers’ threads, each thread task is looping 50,000 time either to read or write. Your data array is of size 100,000 random integers between 0 to 9,999.

private ReadersWriters wr = new ReadersWriters(); private int data[] = new int[100000];
SharedData() {
for (int j = 0; j < data.length; j++)
  data[j] = (int) (Math.random() * 10000);
}
