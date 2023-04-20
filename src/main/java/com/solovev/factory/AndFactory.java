package com.solovev.factory;

import com.solovev.model.And;
import com.solovev.model.LogicElement;

public class AndFactory implements ElementFactoryI {
    @Override
    public LogicElement newInstance(int n) {
        return new And(n);
    }
}