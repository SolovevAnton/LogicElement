package com.solovev.factory;

import com.solovev.model.And;
import com.solovev.model.LogicElement;
import com.solovev.model.Or;
import com.solovev.model.Xor;

public enum FactoryEnum implements ElementFactoryI {
    AND {
        @Override
        public LogicElement newInstance(int n) {
            return new And(n);
        }
    },
    OR {
        @Override
        public LogicElement newInstance(int n) {
            return new Or(n);
        }
    },
    XOR {
        @Override
        public LogicElement newInstance(int n) {
            return new Xor(n);
        }
    },

}
