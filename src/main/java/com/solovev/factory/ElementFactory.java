package com.solovev.factory;

import com.solovev.model.LogicElement;

/**
 * factory class to create instances of LogicElement
 */
public class ElementFactory {
    /**
     * Method will create instance of a LogicElement based on enum variable
     *
     * @param instance enum parameter
     * @param num      number of ins for Logic Element
     * @return LogicElement of required type
     */
    public static LogicElement newInstance(FactoryEnum instance, int num) {
        return instance == null ? null : instance.newInstance(num);
    }
}

