import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int N;

    private class ListIterator implements Iterator<Item> {
        boolean[] returned = new boolean[N];
        int numNotReturned = N;

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue is empty.");
            }

            int rand = StdRandom.uniform(N);

            while (returned[rand]) {
                rand = StdRandom.uniform(N);
            }
            returned[rand] = true;
            numNotReturned--;

            return arr[rand];
        }

        public boolean hasNext() {
            return numNotReturned > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        validate(item);

        if (N == arr.length) {
            resize(arr.length * 2);
        }
        arr[N++] = item;
    }

    public Item dequeue() {
        checkIfEmpty();
        int rand = StdRandom.uniform(N);
        Item item = arr[rand];
        
        if (N > 1) {
            Item[] newArr;
            if (N == arr.length/4) {
                newArr = (Item[]) new Object[arr.length/2];
            } else {
                newArr = (Item[]) new Object[arr.length];
            }
            for (int i = 0; i < rand; i++) {
                newArr[i] = arr[i];
            }
            for (int i = rand + 1; i < N; i++) {
                newArr[i - 1] = arr[i];
            }
            arr = newArr;
        } else {
            arr[0] = null;
        }
        N--;

        return item;
    }

    public Item sample() {
        checkIfEmpty();
        return arr[StdRandom.uniform(N)];
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void resize(int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];

        for (int i = 0; i < N; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    private void validate(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument is Null.");
        }
    }

    private void checkIfEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println("Empty? - " + rq.isEmpty());

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);

        System.out.print("After 3 enqueues: ");
        for (int i : rq) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Sample: " + rq.sample());

        Iterator<Integer> itr1 = rq.iterator();
        Iterator<Integer> itr2 = rq.iterator();

        System.out.println("Itr1: " + itr1.next());
        System.out.println("Itr2: " + itr2.next());

        System.out.println("Size is " + rq.size());
        System.out.println("Empty? - " + rq.isEmpty());

        rq.dequeue();
        rq.dequeue();

        System.out.print("After 2 dequeues: ");
        for (int i : rq) {
            System.out.print(i + " ");
        }
        System.out.println("");
        System.out.println("Size is " + rq.size());

        System.out.println("Finished");
    }
}