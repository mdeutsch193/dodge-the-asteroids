/*
 * Martin Deutsch
 * 11/20/15
 * MyHashMap.java
 */
 
import java.util.*;

/*
 * Represents a hashmap
 */
public class MyHashMap<K, V>
{
	// the array of entry nodes
    private EntryNode<K, V>[] entries;
    // the size of the hashmap
    private int size;

	// constructor makes an entries array of size 100
    public MyHashMap() {
        this.entries = new EntryNode[100];
        this.size = 0;
    }

	// constructor makes an entries array with the given size
    public MyHashMap(int initialCapacity) {
        this.entries = new EntryNode[initialCapacity];
        this.size = 0;
    }
    
	// put the given data in the map with the given key
    public void put(K key, V value) {
    	// there is not already an entry with the given key
        if (this.get(key) == null) {
        
        	 // if over half full, increase array size
            if (this.size >= this.entries.length / 2) {
                EntryNode<K, V>[] oldEntries = this.entries;
                this.entries = new EntryNode[this.entries.length * 2];
                this.size = 0;
                for (EntryNode<K, V> node : oldEntries) {
                    while (node != null) {
                        this.put(node.key, node.value);
                        node = node.next;
                    }
                }
            }
            
            // add the new node to the head of the list
            int index = Math.abs(key.hashCode()%this.entries.length);
            this.entries[index] = new EntryNode(key, value, this.entries[index]);
            this.size++;
        }
        
        // replace an entry, or add new entry to the list if different keys
        else {
            EntryNode<K, V> node = 
            			this.entries[Math.abs(key.hashCode()%this.entries.length)];
            while (!node.key.equals(key)) {
                node = node.next;
            }
            node.value = value;
        }
    }

	// return the value with the given key
    public V get(K key) {
        EntryNode<K, V> node = this.entries[Math.abs(key.hashCode()%this.entries.length)];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            else {
                node = node.next;
            }
        }
        return null;
    }

	// returns the size of the hashmap
    public int size() { 
    	return this.size; 
    }

	// returns true if the hashmap is empty, false if not
    public boolean isEmpty() { 
    	return this.size == 0; 
    }

	// clears the hashmap
    public void clear() {
        this.size = 0;
        this.entries = new EntryNode[100];
    }

	// removes the data with the given key from the hashmap
    public V remove(K key) {
        V value = this.get(key);
        
        // the key isn't being used currently, return null
        if (value == null) {
            return null;
        }
        else {
            int index = Math.abs(key.hashCode()%this.entries.length);
            if (this.entries[index].key.equals(key)) // remove the head
            {
                this.entries[index] = this.entries[index].next;
            }
            else {
                EntryNode<K, V> node = this.entries[index];
                while (!node.next.key.equals(key)) {
                    node = node.next;
                }
                node.next = node.next.next;  // remove node.next
            }
            size--;
            return value;
        }
    }
    
    // returns true if the hashmap has an entry with the given key, false if not
    public boolean containsKey(String key) {
    	return this.entries[Math.abs(key.hashCode()%this.entries.length)] != null;
    }
    
    // returns true if the hashmap has an entry with the given data, false if not
    public boolean containsValue(V value) {
    	for (EntryNode<K, V> node : this.entries) {
                while (node != null) {
                    if (node.value.equals(value)) {
                    	return true;
                    }
                    node = node.next;
                }
        }
        return false;
    }
    
    // returns the map represented as a string
    public String toString() {
    	String str = "";
    	for (EntryNode<K, V> node : this.entries) {
    		if (node != null) {
    			str += node.key.toString();
    			str += ": ";
    			str += node.value.toString();
    			str += "\n";
    		}
    	}
    	return str;
    }
    
    // returns an ArrayList of all the values in the map
    public Collection values() {
    	ArrayList<V> values = new ArrayList<V>();
    	for (EntryNode<K, V> node : this.entries) {
            if (node != null) {
            	values.add(node.value);
            }
        }
        return values;
    }
            	

	// unit test
    public static void main(String[] args) {
        MyHashMap<String, Double> map = new MyHashMap<String, Double>();
        map.put("Martin", 3.3);
        map.put("Deutsch", 5.7);
        // should print 3.3
        System.out.println(map.get("Martin"));
        // should print null
        System.out.println(map.get("Bob"));
        // should print 2
        System.out.println(map.size());
        // should print 5.7
        System.out.println(map.remove("Deutsch"));
        map.clear();
        // should print true
        System.out.println(map.isEmpty());
        
        MyHashMap<String, Double> map2 = new MyHashMap<String, Double>(2);
        map.put("Bob", 4.2);
        map.put("Larry", 5.8);
        map.put("Bob", 1.1);
        // should print true
        System.out.println(map.containsKey("Larry"));
        // should print false
        System.out.println(map.containsKey("Steve"));
        // should print true
        System.out.println(map.containsValue(1.1));
        // should print false
        System.out.println(map.containsValue(0.0));
        // should print contents of map
        System.out.println(map.toString());
    }

	// represents a single key-data pairing in the hashmap
    private class EntryNode<K, V>
    {
    	// the key
        public K key;
        // the data
        public V value;
        // the next entry in the list, if have same hashcode
        public EntryNode<K, V> next;
		
		// constructor sets the key, data, and next node in list
        public EntryNode(K k, V v, EntryNode<K, V> n) {
            key = k;
            value = v;
            next = n;
        }
    }
}

