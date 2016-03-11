package de.unknownreality.data.frame.column;


import de.unknownreality.data.frame.MapFunction;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Alex on 09.03.2016.
 */
public abstract class BasicColumn<T extends Comparable<T>> implements DataColumn<T>{
    private static double GROW_FACTOR = 1.6d;
    public static final int INIT_SIZE = 128;

    private int size = 0;


    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void sort(Comparator<T> comparator) {
        Arrays.sort(values,0,size(), comparator);
    }

    public void sort() {
        Arrays.sort(values,0,size());
    }

    @Override
    public DataColumn<T> set(int index, T value) {
        values[index] = value;
        return this;
    }

    @Override
    public DataColumn<T> map(MapFunction<T> dataFunction) {
        for (int i = 0; i < size(); i++) {
            values[i] = dataFunction.map(values[i]);
        }
        return this;
    }

    private T[] values;

    @Override
    public void reverse() {
        for (int i = 0; i < size() / 2; i++) {
            T temp = values[i];
            values[i] = values[size() - i - 1];
            values[size() - i - 1] = temp;
        }
    }

    public BasicColumn(String name){
        this.size = 0;
        this.name = name;
        values = (T[]) Array.newInstance(getType(), INIT_SIZE);
    }

    public BasicColumn(){
        this(null);
    }

    public BasicColumn(String name, T[] values) {
        this.values = values;
        this.name = name;
        size = values.length;
    }

    @Override
    public T get(int index) {
        return values[index];
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return new HashSet<T>(Arrays.asList(values)).contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                return values[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return values;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size())
            return (T1[]) Arrays.copyOf(values, size(), a.getClass());
        System.arraycopy(values, 0, a, 0, size());
        if (a.length > size())
            a[size()] = null;
        return a;
    }

    @Override
    public boolean append(T t) {
        if(size == values.length){
            values = Arrays.copyOf(values, (int)(values.length * GROW_FACTOR));
        }
        values[size++] = t;
        return true;
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<T>(Arrays.asList(values)).containsAll(c);
    }


    @Override
    public boolean appendAll(Collection<T> c) {
        for(T o : c){
            append(o);
        }
        return true;
    }

    @Override
    public void clear() {
        values = (T[]) Array.newInstance(getType(), INIT_SIZE);
        size = 0;
    }

    public T[] getValues() {
        return values;
    }

}