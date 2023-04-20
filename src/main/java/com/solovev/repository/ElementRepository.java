package com.solovev.repository;

import com.solovev.factory.ElementFactoryI;
import com.solovev.model.LogicElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ElementRepository {
    private ArrayList<LogicElement> LogicElements = new ArrayList<>();

    public ElementRepository(String file, Map<String, ElementFactoryI> map) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line = bufferedReader.readLine();
            String[] separated = line.split(";");
            LogicElement logicElement = map.get(separated[0]).newInstance(separated.length - 1);
            LogicElements.add(logicElement) ;
//            boolean[] booleans = ;
//            for(){
//                Boolean.parseBoolean(...);
//            }
//            logicElement.fill(booleans);
        }
    }
}
