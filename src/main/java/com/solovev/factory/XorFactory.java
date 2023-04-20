package com.solovev.factory;

import com.solovev.model.LogicElement;
import com.solovev.model.Xor;

public class XorFactory implements ElementFactoryI{

    @Override
    public LogicElement newInstance(int n) {
        return new Xor(n);
    }
}
