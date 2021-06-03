import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int listSize;

    private class Node {
        Item item;
        Node next, prev;
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("Deque is empty.");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public Deque() {

    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size() {
        return listSize;
    } 
    
    public void addFirst(Item item) {
        validate(item);

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (last == null) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        listSize++;
    }

    public void addLast(Item item) {
        validate(item);

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        listSize++;
    }
    
    public Item removeFirst() {
        checkIfEmpty();

        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        listSize--;

        return item;
    }

    public Item removeLast() {
        checkIfEmpty();

        Item item = last.item;

        if (listSize > 1) {
            last = last.prev;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        listSize--;

        return item;
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void validate(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument is Null.");
        }
    }

    private void checkIfEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();

        System.out.println("Empty? - " + dq.isEmpty());

        dq.addLast(2);
        dq.addFirst(1);

        System.out.println("Added last 2, first 1");

        Iterator<Integer> itr = dq.iterator();
        while(itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println();
        System.out.println("Size is " + dq.size());

        dq.addFirst(3);
        dq.removeLast();

        System.out.println("Added first 3, removed last");
        for (int i : dq) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Empty? - " + dq.isEmpty());

        System.out.println("Removing first: " + dq.removeFirst());
        System.out.println("And again: "+ dq.removeFirst());

        System.out.println("Empty? - " + dq.isEmpty());
        System.out.println("Size is " + dq.size());
        dq.removeLast();
    }
}