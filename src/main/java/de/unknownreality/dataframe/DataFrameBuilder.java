/*
 *
 *  * Copyright (c) 2019 Alexander Grün
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package de.unknownreality.dataframe;

import de.unknownreality.dataframe.column.*;
import de.unknownreality.dataframe.filter.FilterPredicate;
import de.unknownreality.dataframe.group.GroupUtil;
import de.unknownreality.dataframe.io.ColumnInformation;
import de.unknownreality.dataframe.io.DataIterator;
import de.unknownreality.dataframe.join.JoinUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Alex on 09.03.2016.
 */
public class DataFrameBuilder {
    private final LinkedHashMap<String, DataFrameColumn<?, ?>> columns = new LinkedHashMap<>();

    private JoinUtil joinUtil = null;
    private GroupUtil groupUtil = null;
    private DataIterator<?> dataIterator;
    private FilterPredicate filterPredicate = FilterPredicate.EMPTY_FILTER;
    private String name;

    protected DataFrameBuilder() {
    }

    protected DataFrameBuilder(DataIterator<?> dataIterator) {
        this.dataIterator = dataIterator;
    }

    public static DataFrame createDefault() {
        return new DefaultDataFrame();
    }

    /**
     * Creates a data frame builder instance based on a parent data container.
     *
     * @param dataIterator parent data reader
     * @return data frame builder
     *
     * @deprecated use {@link DataFrame#load} instead.
     *
     * */
    @Deprecated
    public static DataFrameBuilder createFrom(DataIterator<?> dataIterator) {
        return new DataFrameBuilder(dataIterator);
    }

    public static DataFrameBuilder create() {
        return new DataFrameBuilder();
    }

    /**
     * Defines the name of the resulting dataframe
     *
     * @param name data frame name
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Adds a new column to the builder.
     *
     * @param column data frame column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addColumn(DataFrameColumn<?, ?> column) {
        columns.put(column.getName(), column);
        return this;
    }

    /**
     * Adds a new {@link BooleanColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addBooleanColumn(String name) {
        BooleanColumn column = new BooleanColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link ByteColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addByteColumn(String name) {
        ByteColumn column = new ByteColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link DoubleColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addDoubleColumn(String name) {
        DoubleColumn column = new DoubleColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link FloatColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addFloatColumn(String name) {
        FloatColumn column = new FloatColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link IntegerColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addIntegerColumn(String name) {
        IntegerColumn column = new IntegerColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link LongColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addLongColumn(String name) {
        LongColumn column = new LongColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link ShortColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addShortColumn(String name) {
        ShortColumn column = new ShortColumn(name);
        return addColumn(column);
    }

    /**
     * Adds a new {@link StringColumn} to the builder.
     *
     * @param name name of the column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addStringColumn(String name) {
        StringColumn column = new StringColumn(name);
        return addColumn(column);
    }


    public DataFrameBuilder setGroupUtil(GroupUtil groupUtil) {
        this.groupUtil = groupUtil;
        return this;
    }

    public DataFrameBuilder setJoinUtil(JoinUtil joinUtil) {
        this.joinUtil = joinUtil;
        return this;
    }

    public DataFrameBuilder withFilterPredicate(String predicate) {
        this.filterPredicate = FilterPredicate.compile(predicate);
        return this;
    }

    public DataFrameBuilder withFilterPredicate(FilterPredicate predicate) {
        this.filterPredicate = predicate;
        return this;
    }

    public DataFrameBuilder from(DataIterator<?> dataIterator) {
        this.dataIterator = dataIterator;
        return this;
    }


    /**
     * Adds a new column to the builder and defines the name of the column in the parent data container.
     *
     * @param header column name in the parent data container
     * @param column data frame column
     * @return <tt>self</tt> for method chaining
     */
    public DataFrameBuilder addColumn(String header, DataFrameColumn<?, ?> column) {
        columns.put(header, column);
        return this;
    }

    public LinkedHashMap<String, DataFrameColumn<?, ?>> getColumns() {
        return columns;
    }

    /**
     * Builds a new data frame.
     *
     * @return created data frame
     */
    public DataFrame build() {
        if (dataIterator != null) {
            List<ColumnInformation> columnInformationList = new ArrayList<>();
            int i = 0;
            for (String n : columns.keySet()) {
                ColumnInformation columnInformation = new ColumnInformation(i, n);
                columnInformation.setColumnType(columns.get(n).getClass());
                columnInformationList.add(columnInformation);
                i++;
            }
            if (columnInformationList.isEmpty()) {
                columnInformationList = dataIterator.getColumnsInformation();
            }
            return DataFrameConverter.fromDataIterator(dataIterator, columnInformationList, filterPredicate);
        }
        DefaultDataFrame dataFrame = new DefaultDataFrame(name);
        for (String n : columns.keySet()) {
            DataFrameColumn<?, ?> col = columns.get(n);
            col.setName(n);
            dataFrame.addColumn(col);
        }
        if (joinUtil != null) {
            dataFrame.setJoinUtil(joinUtil);
        }
        if (groupUtil != null) {
            dataFrame.setGroupUtil(groupUtil);
        }
        return dataFrame;
    }

}
