package com.solovev.util;

import com.solovev.model.LogicElement;

public class Elements {
    /**
     * method unites all given elements
     *
     * @return united logical object, consists of all elements. If there is only one element return element
     * @throws ClassCastException is elements are from different types
     */
    public static LogicElement unite(LogicElement... elements) {
        LogicElement unitedElement = elements[0];
        for (int i = 1; i < elements.length; i++) {
            unitedElement = unitedElement.unite(elements[i]);
        }
        return unitedElement;
    }
}
