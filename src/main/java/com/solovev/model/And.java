package com.solovev.model;

public class And extends LogicElement {

    public And(int inCount) {
        super(inCount);
    }

    @Override
    protected boolean operation(boolean input1, boolean input2) {
        return input1 & input2;
    }
}
