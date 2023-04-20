package com.solovev.factory;

import com.solovev.model.LogicElement;

public interface ElementFactoryI {
    LogicElement newInstance(int n);
}
