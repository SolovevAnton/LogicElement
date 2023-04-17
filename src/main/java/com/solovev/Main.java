package com.solovev;

import com.solovev.model.And;
import com.solovev.model.LogicElement;
import com.solovev.model.Or;
import com.solovev.model.Xor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static private void Test(LogicElement le, boolean...test) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        le.fill(test);
        le.result();
        System.out.println(le);
        LogicElement united = le.unite(le);
        united.result();
        System.out.println(united);
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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

        for (LogicElement le: logicElements) {
            Arrays.stream(tests).forEach(t-> {
                try {
                    Test(le,t);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println();
        }

    }
}