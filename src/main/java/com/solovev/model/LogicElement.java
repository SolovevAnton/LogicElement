package com.solovev.model;

import java.util.Arrays;
import java.util.Comparator;

public abstract class LogicElement implements Comparable<LogicElement> {
    private final boolean[] booleans;

    public LogicElement(int inCount) {
        this.booleans = new boolean[inCount];
    }

    /**
     * Method fills input with array of booleans
     * using this method sets isResulted to false
     *
     * @param booleans array of booleans to replace input. If it is larger than input, only the array till input size will be copied
     * @throws IllegalArgumentException if booleans is shorter than this.booleans
     **/
    public void fill(boolean... booleans) {
        if (booleans.length < this.booleans.length) {
            throw new IllegalArgumentException("Number of booleans must be larger than inCount");
        }
        System.arraycopy(booleans, 0, this.booleans, 0, this.booleans.length);
    }

    //** Method returns length of the input
    public int getLength() {
        return booleans.length;
    }

    //** method to return result of some operations
    protected abstract boolean operation(boolean input1, boolean input2);

    /**
     * method to calculate results from booleans array sequentially using operation(). Field out will have this result
     * Example: [true,true,true] -> operation is AND -> true ; [true,true,false] -> operation is AND -> false
     *
     * @return the final result of out; if booleans length is one  result will always be booleans[0], if booleans is empty result will be false
     **/
    public boolean result() {
        if (booleans.length < 1) {
            return false;
        }
        boolean out = booleans[0];
        for (int i = 1; i < booleans.length; i++) {
            out = operation(out, booleans[i]);
        }
        return out;
    }

    /**
     * method to unite to LogicElement instances
     * united element consists of arrays
     *
     * @return Type of return is most closely related instance.If obj and arg are the same type - the object of this type will be returned.
     * new element booleans consists of both, with two arrays united
     * @throws ClassCastException is elements are from different types
     */
    public LogicElement unite(LogicElement element) {
        if (element.getClass() != this.getClass()) {
            throw new ClassCastException("Objects must have same type");
        }
        try {
            int totalLength = this.getLength() + element.getLength();
            LogicElement unitedElement = element.getClass().getConstructor(int.class).newInstance(totalLength);
            boolean[] unitedArray = Arrays.copyOf(this.booleans, totalLength);
            System.arraycopy(element.booleans, 0, unitedArray, this.getLength(), element.getLength());
            unitedElement.fill(unitedArray);
            return unitedElement;
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Method to get Length of the booleans
     *
     * @return length of the booleans array
     */
    private int length() {
        return booleans.length;
    }

    /**
     * Method compares two LogicElements
     * Compartment is done by: Type(lexicographical order), then by length, then by Out
     *
     * @param other the object to be compared.
     * @return int > 0 if this>other, 0 if this == other, int<0 otherwise
     */
    @Override
    public int compareTo(LogicElement other) {
        return Comparator
                .comparing((LogicElement le) -> le.getClass().getSimpleName())
                .thenComparing(LogicElement::length)
                .thenComparing(LogicElement::result)
                .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogicElement that = (LogicElement) o;

        return Arrays.equals(booleans, that.booleans);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(booleans);
    }

    @Override
    public String toString() {
        return "LogicElement{" +
                "type=" + this.getClass().getSimpleName() +
                ", booleans=" + Arrays.toString(booleans) +
                ", out=" + this.result() +
                '}';
    }
}
