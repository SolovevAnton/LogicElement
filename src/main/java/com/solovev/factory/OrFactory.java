package com.solovev.factory;

import com.solovev.model.LogicElement;

public class OrFactory implements ElementFactoryI {
    @Override
    public LogicElement newInstance(int n) {
        return FactoryEnum.OR.newInstance(n);
    }
}
