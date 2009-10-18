package com.mcherm.zithiacharsheet.client.modeler;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * This is a list of objects which can itself be observed
 * for changes to the list.
 * <p>
 * This alerts its observers if an item is added to the list or removed
 * from the list.
 */
public class ObservableList<T> extends SimpleObservable implements Iterable<T> {
    
    private final ArrayList<T> items;
    
    /**
     * Constructor.
     */
    public ObservableList() {
        items = new ArrayList<T>(4);
    }
    
    public Iterator<T> iterator() {
        return items.iterator();
    }
    
    public void add(T item) {
        items.add(item);
        alertObservers();
    }
    
    /* Commented out for the moment
    public void remove(T item) {
        items.remove(item);
        alertObservers();
    }
    */
}