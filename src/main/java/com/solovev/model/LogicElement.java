package com.solovev.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class LogicElement {
    private final boolean[] booleans;
    private boolean out;
    // field indicates if this object have been resulted, thus, its out field is relevant. If false - it is not resulted and out will always be false; Value resets everytime the object is filled
    private boolean isResulted;
    public LogicElement(int inCount){
        this.booleans = new boolean[inCount];
    }

    /** Method fills input with array of booleans
     * using this method sets isResulted to false
     * @param booleans array of booleans to replace input. If it is larger than input, only the array till input size will be copied
     * @throws  IllegalArgumentException if booleans is shorter than this.booleans
     **/
    public void fill(boolean... booleans) {
        if(booleans.length < this.booleans.length){ throw new IllegalArgumentException("Number of booleans must be larger than inCount");}
        System.arraycopy(booleans,0, this.booleans,0, this.booleans.length);
        isResulted = false;
    }
    //** Method returns length of the input
    public int getLength(){ return booleans.length;}

    //** method to return result of some operations
    protected abstract boolean operation(boolean input1, boolean input2);

    /** method to calculate results from booleans array sequentially using operation(). Field out will have this result
     * Example: [true,true,true] -> operation is AND -> true ; [true,true,false] -> operation is AND -> false
    * @return the final result of out; if booleans length is one  result will always be booleans[0]
    **/
    public boolean result() {
        out = booleans[0];
        for(int i = 1; i < booleans.length; i++){
            out = operation(out,booleans[i]);
        }
        isResulted = true;
        return out;
    }

    /** method to unite to LogicElement instances
     * united element consists of arrays
     * @return Type of return is most closely related instance.If obj and arg are the same type - the object of this type will be returned.
     * new element booleans consists of both, with two arrays united
     * @throws ClassCastException is elements are from different types
     */
    public <T extends LogicElement> T unite(T element) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if( (element.getClass() != this.getClass())) { throw new ClassCastException("Objects must have same type");}
        int totalLength = this.getLength() + element.getLength();
        // this object creation was solved using Google
        T unitedElement = (T) element.getClass().getConstructor(int.class).newInstance(totalLength);
        boolean[] unitedArray = Arrays.copyOf(this.booleans,totalLength);
        System.arraycopy(element.getBooleans(),0,unitedArray,this.getLength(),element.getLength());
        unitedElement.fill(unitedArray);
        return unitedElement;
    }

    public boolean[] getBooleans() {
        return booleans;
    }

    public boolean isOut() {
        return out;
    }

    public boolean isResulted() {
        return isResulted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogicElement that = (LogicElement) o;

        if (out != that.out) return false;
        if (isResulted != that.isResulted) return false;
        return Arrays.equals(booleans, that.booleans);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(booleans);
        result = 31 * result + (out ? 1 : 0);
        result = 31 * result + (isResulted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogicElement{" +
                "type=" + this.getClass().getSimpleName() +
                ", booleans=" + Arrays.toString(booleans) +
                ", out=" + out +
                ", isResulted=" + isResulted +
                '}';
    }
}
