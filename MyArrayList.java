/* 
 * Martin Deutsch
 * 11/19/15
 * MyArrayList.java
 */

import java.util.*;

/*
 * Represents a dynamic array list
 */
public class MyArrayList<T> implements Iterable<T> {

	// the array of items
    private Object[] list;
    
    // the size of the list
    private int size;
	
	// constructor builds array with 100 entries
    public MyArrayList() {
        this.list = new Object[100];
        this.size = 0;
    }

	// returns the item at the given position
    public T get(int i) {
        return (T) this.list[i];
    }

	// returns the size of the list
    public int size() {
        return this.size;
    }
    
	// adds new item at the given index
    public void add(int index, T thing) {
        // make sure array has available space
        if (this.size == this.list.length) { // double the length of the array
            Object[] temp = new Object[this.list.length * 2];
            for (int i = 0; i < this.list.length; i++)
        	{
                temp[i] = this.list[i];
            }
            this.list = temp;
        }

        // shift entries to right to make room
        for (int i = this.size; i >= index; i--) {
        	if (i != 0) {
        		list[i] = list[i - 1];
        	}
        }

        // add the new value
        list[index] = thing;
        size++;
    }
    
    // adds the given item to the end of the list
    public void add(T thing) {
        if (this.size == this.list.length) { // double the length of the array
            Object[] temp = new Object[this.list.length * 2];
            for (int i = 0; i < this.list.length; i++)
            {
                temp[i] = this.list[i];
            }
            this.list = temp;
        }
        //now add the new value
        this.list[this.size] = thing;
        this.size++;
    }
    
    // replaces the entry at the given index of the list with the specified item
    public void set(int index, T thing) {
    	list[index] = thing;
    }

	// removes the item at the given index
    public Object remove(int index) {
    	T thing = (T) this.list[index];
        // shift itmes to the left
        for (int i = index; i < this.size; i++) {
            this.list[i] = this.list[i + 1];
        }
        size--;
        return thing;
    }
    
    // removes the first instance of the given item
    public boolean remove(T thing) {
    	int index = 0;
    	for (int i = 0; i < this.size; i++) {
    		T entry = (T) this.list[i];
    		if (entry.equals(thing)) {
    			// shift items left
    			for (int n = i; n < this.size; n++) {
            		this.list[n] = this.list[n + 1];
        		}
        	}
        	this.size--;
        	return true;
        }
        return false;
    }
    
    // clears the list
    public void clear() {
    	this.list = new Object[100];
    	this.size = 0;
    }
    
    // returns true if the list is empty, false if not
    public boolean isEmpty() {
    	return this.size == 0;
    }
    
    // returns an iterator object
    public Iterator<T> iterator() {
        return new MyArrayListIterator<T>();
    }

    // private inner class that creates an iterator
    private class MyArrayListIterator<T> implements Iterator<T> {
        
        // the current position in the list
        private int count;

		// constructor initializes count
        public MyArrayListIterator() {
            count = 0;
        }

		// returns next entry in list
        public T next() {
            T nextVal = (T) list[count];
            count++;
            return nextVal;
        }
		
		// checks if still entries in list
        public boolean hasNext() {
            return count < size;
        }
        
        // does nothing
        public void remove() {
        	return;
        }
    }

	// unit test
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<Integer>();
    
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
   		
   		// should print false
   		System.out.println(list.isEmpty());
   		// should print 4
   		System.out.println(list.size());
   		
   		list.add(0, 5);

        // should print 5, 0, 1, 2, 3
        for (Integer x : list) {
            System.out.println(x);
        }
		
		list.remove(0);
		
		// should print 0, 1, 2, 3
        for (Integer x : list) {
            System.out.println(x);
        }
        
        list.clear();
        // should print true
        System.out.println(list.isEmpty());
        
        list.add(10);
        // should print 10
        System.out.println(list.get(0));
        
        list.set(0, 10);
        // should print 0
        System.out.println(list.get(0));
        
        list.remove(new Integer(10));
        // should print null
        System.out.println(list.get(0));
    }
}
