package com.solovev.factory;

import com.solovev.model.LogicElement;
import com.solovev.model.Or;

public class OrFactory implements ElementFactoryI{
    @Override
    public LogicElement newInstance(int n) {
        return new Or(n);
    }
}
