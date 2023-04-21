package com.solovev;

import com.solovev.factory.*;
import com.solovev.model.LogicElement;
import com.solovev.repository.ElementRepository;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        //Tests for first part of logic elements
//        LogicElement[] logicElements = {
//                new And(2),
//                new Or(2),
//                new Xor(2),
//        };
//
//        boolean[][] tests = {
//                {true, true},
//                {true, false},
//                {false, false}
//        };
//
//        for (LogicElement le : logicElements) {
//            Arrays.stream(tests).forEach(t -> Test(le, t));
//            System.out.println();
//        }
//        logicElements[1].fill(tests[1]);
//
//        System.out.println(Elements.unite(logicElements[1], new Or(3), logicElements[1]));

        //Tests of second part
        FactoryEnum[] allEnum = {
                FactoryEnum.AND,
                FactoryEnum.OR,
                FactoryEnum.XOR,
                null
        };
        //test 1
        int numberOfIns = 2;
        for (FactoryEnum in : allEnum) {
            System.out.println(ElementFactory.newInstance(in, numberOfIns));
        }
        //test 3
        Map<String, ElementFactoryI> factoryMap = new HashMap<>();
        factoryMap.put("AND", new AndFactory());
        factoryMap.put("OR", new OrFactory());
        factoryMap.put("XOR", new XorFactory());

        File filePath = new File("D:\\Git\\Practice_Projects\\LogicElement\\elements.csv");
        try {
            ElementRepository elementRepository = new ElementRepository(filePath, factoryMap);
            System.out.println(elementRepository + "\n");
            //Sorting tests
            List<Comparator<LogicElement>> comparators = List.of(
                    Comparator.comparing(LogicElement::getLength),
                    Comparator.comparing(LogicElement::result),
                    Comparator.comparing(le -> le.getClass().getSimpleName())
            );
            elementRepository.sort();
            System.out.println(elementRepository + "\n");
            for (Comparator<LogicElement> c : comparators) {
                elementRepository.sort(c);
                System.out.println(elementRepository + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //test 5
        try{
            ElementRepository elementRepository= new ElementRepository(filePath);
            System.out.println(elementRepository +"/n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}