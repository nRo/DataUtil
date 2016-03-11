package de.unknownreality.data.frame;

import de.unknownreality.data.common.Header;
import de.unknownreality.data.frame.column.DataColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Alex on 09.03.2016.
 */
public class DataFrameHeader implements Header<String> {
    private static Logger log = LoggerFactory.getLogger(DataFrameHeader.class);
    private Map<String,Integer> headerMap = new HashMap<>();
    private List<String> headers = new ArrayList<>();
    private Map<String,Class<? extends Comparable>> typesMap = new HashMap<>();
    private Map<String,Class<? extends DataColumn>> colTypeMap = new HashMap<>();

    public int size(){
        return headers.size();
    }

    public DataFrameHeader add(DataColumn column){
        return add(column.getName(),column.getClass(),column.getType());
    }
    public DataFrameHeader add(String name, Class<? extends DataColumn> colClass, Class<? extends Comparable> type){
        int index = headers.size();
        headers.add(name);
        headerMap.put(name,index);
        typesMap.put(name,type);
        colTypeMap.put(name,colClass);
        return this;
    }

    public void remove(String name){
        boolean fix = false;
        for(String s : headers){
            if(!fix && s.equals(name)){
                fix = true;
                continue;
            }
            if(fix){
                headerMap.put(s,headerMap.get(s) - 1);
            }
        }
        headers.remove(name);
        headerMap.remove(name);
        typesMap.remove(name);
        colTypeMap.remove(name);
    }

    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        }
        if(other.getClass() != this.getClass()){
            return false;
        }
        DataFrameHeader otherHeader = (DataFrameHeader)other;
        if(size() != otherHeader.size()){
            return false;
        }
        for(String s : headers){
            if(!otherHeader.contains(s)){
                return false;
            }
            if(getType(s) != otherHeader.getType(s)){
                return false;
            }
        }
        return true;
    }

    public Class<? extends DataColumn> getColumnType(String name){
        return colTypeMap.get(name);
    }

    public Class<? extends DataColumn> getColumnType(int index){
        return colTypeMap.get(get(index));
    }

    public Class<? extends Comparable> getType(String name){
        return typesMap.get(name);
    }

    public Class<? extends Comparable> getType(int index){
        return typesMap.get(get(index));
    }

    public String get(int index){
        if(index >= headers.size()){
            throw new IllegalArgumentException(String.format("header index out of bounds %d > %d",index,(headers.size()-1)));
        }
        return headers.get(index);
    }

    @Override
    public boolean contains(String value) {
        return headerMap.containsKey(value);
    }

    public void clear(){
        headerMap.clear();
        headers.clear();
        typesMap.clear();
    }


    public int getIndex(String name){
        Integer index = headerMap.get(name);
        index = index == null ? -1 : index;
        return index;
    }

    public DataFrameHeader copy(){
        DataFrameHeader copy = new DataFrameHeader();
        for(String h : headers){
            copy.add(h,getColumnType(h),getType(h));
        }
        return copy;
    }

    @Override
    public Iterator<String> iterator() {
        return headers.iterator();
    }

}