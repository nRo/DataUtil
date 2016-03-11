package de.unknownreality.data.frame;

import de.unknownreality.data.common.Header;
import de.unknownreality.data.common.Row;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 09.03.2016.
 */
public class DataRow implements Row<Comparable> {
    private Comparable[] values;
    private Header<String> header;

    public DataRow(Header<String> header, Comparable[] values) {
        this.header = header;
        this.values = values;
    }

    public Comparable get(String headerName) {
        int index = header.getIndex(headerName);
        if (index == -1) {
            throw new IllegalArgumentException(String.format("header name not found '%s'", headerName));
        }
        return get(index);
    }

    public Comparable get(int index) {
        return this.values[index];
    }

    public Double getDouble(int index) {
        Object value = get(index);
        try {
            return Number.class.cast(get(index)).doubleValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("no double value in col " + index + " (" + value + ")");
        }
    }

    public String getString(int index) {
        Object value = get(index);
        if (value != null) {
            return value.toString();
        }
        throw new IllegalArgumentException("no String value in col " + index + " (" + value + ")");
    }

    public int size() {
        return values.length;
    }

    public Boolean getBoolean(int index) {
        Object value = get(index);
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new IllegalArgumentException("no boolean value in col " + index + " (" + value + ")");
    }


    public Double getDouble(String name) {
        Object value = get(name);
        try {
            return Number.class.cast(get(name)).doubleValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("no double value in col " + name + " (" + value + ")");
        }
    }

    public String getString(String name) {
        return getString(header.getIndex(name));
    }

    public Boolean getBoolean(String name) {
        return getBoolean(header.getIndex(name));

    }

    @Override
    public Integer getInteger(int index) {
        Object value = get(index);
        try {
            return Number.class.cast(get(index)).intValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("no int value in col " + index + " (" + value + ")");
        }
    }

    @Override
    public Integer getInteger(String headerName) {
        Object value = get(headerName);
        try {
            return Number.class.cast(value).intValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("no int value in col " + headerName + " (" + value + ")");
        }
    }

    @Override
    public Float getFloat(int index) {
        Object value = get(index);
        try {
            return Number.class.cast(value).floatValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("no float value in col " + index + " (" + value + ")");
        }
    }

    @Override
    public Float getFloat(String headerName) {
        Object value = get(headerName);
        try {
            return Number.class.cast(value).floatValue();
        } catch (Exception e) {
            throw new IllegalArgumentException("no float value in col " + headerName + " (" + value + ")");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append("\t");
            }
        }
        return sb.toString();
    }
}