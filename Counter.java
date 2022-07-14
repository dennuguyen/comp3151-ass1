package ass1; // TODO: Remove this before submission.

import java.util.ArrayList;

public class Counter {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Unexpected number of arguments.");
        }

        int numReaderProcesses = Integer.parseInt(args[0]);
        int numCounterBytes = Integer.parseInt(args[1]);
        int numRounds = Integer.parseInt(args[2]); // k = 0 loops infinitely.

        // Initialise shared counter.
        new SharedCounter(numCounterBytes);

        // Create reader threads.
        ArrayList<Reader> readers = new ArrayList<Reader>();
        for (int i = 0; i < numReaderProcesses; i++) {
            readers.add(new Reader(numRounds));
        }

        // Create writer thread.
        Writer writer = new Writer(numRounds);

        // Start all threads.
        writer.start();
        for (Reader reader : readers) {
            reader.start();
        }
    }
}

class SharedCounter {
    static public volatile byte[] count;

    public SharedCounter(int maxCounterBytes) {
        SharedCounter.count = new byte[maxCounterBytes];
    }

    // Atomic operation.
    static public int readByByte() {
    }

    // Atomic operation.
    static public void incrementByByte() {
    }
}

class Reader extends Thread {

    private int maxRounds;

    public Reader(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public void run() {
        for (int round = 0; round < maxRounds || maxRounds == 0; round++) {
            read();
        }
    }

    public void read() {
        SharedCounter.readByByte();
    }
}

class Writer extends Thread {

    private int maxRounds;

    public Writer(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public void run() {
        for (int round = 0; round < maxRounds || maxRounds == 0; round++) {
            increment();
        }
    }

    public void increment() {
        SharedCounter.incrementByByte();
    }
}
