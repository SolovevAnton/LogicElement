package com.solovev.factory;

import com.solovev.model.LogicElement;

public class XorFactory implements ElementFactoryI {

    @Override
    public LogicElement newInstance(int n) {
        return FactoryEnum.XOR.newInstance(n);
    }
}
