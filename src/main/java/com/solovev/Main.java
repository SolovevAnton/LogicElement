package com.solovev;

import com.solovev.factory.ElementFactory;
import com.solovev.factory.Instances;
import com.solovev.model.And;
import com.solovev.model.LogicElement;
import com.solovev.model.Or;
import com.solovev.model.Xor;
import com.solovev.util.Elements;

import java.util.Arrays;

public class Main {

    static private void Test(LogicElement le, boolean... test) {
        le.fill(test);
        le.result();
        System.out.println(le);
        LogicElement united = le.unite(le);
        united.result();
        System.out.println(united);
    }

    public static void main(String[] args) {
        //Tests for first part of logic elements
        LogicElement[] logicElements = {
                new And(2),
                new Or(2),
                new Xor(2),
        };

        boolean[][] tests = {
                {true, true},
                {true, false},
                {false, false}
        };

        for (LogicElement le : logicElements) {
            Arrays.stream(tests).forEach(t -> Test(le, t));
            System.out.println();
        }
        logicElements[1].fill(tests[1]);

        System.out.println(Elements.unite(logicElements[1], new Or(3), logicElements[1]));

        //Tests of second part
        Instances[] allEnum = {
            Instances.AND,
            Instances.OR,
            Instances.XOR
        };
        //test 1
        int numberOfIns = 5;
        for(Instances in: allEnum ){
            System.out.println(ElementFactory.newInstance(in,numberOfIns));
        }

    }
}