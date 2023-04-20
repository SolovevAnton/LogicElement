package com.solovev.repository;

import com.solovev.factory.ElementFactoryI;
import com.solovev.model.LogicElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to read some LogicElements from cvs file
 */
public class ElementRepository {
    //csv separator
    private static final String SEPARATOR = ";";
    private ArrayList<LogicElement> LogicElements = new ArrayList<>();

    /**
     * constructor of the class to fill the list of logic elements with values from the cvs file
     *
     * @param file //file name
     * @param map  //Map of names of the logic elements as well as their factories
     * @throws IOException //in case file does not exist
     */
    public ElementRepository(File file, Map<String, ElementFactoryI> map) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int elementToStartWith = 1; //cannot be less than 1

            while ((line = bufferedReader.readLine()) != null) {
                String[] separated = line.split(SEPARATOR);
                LogicElement logicElement = map
                        .get(separated[elementToStartWith - 1])
                        .newInstance(separated.length - elementToStartWith);
                logicElement.fill(getBooleans(separated, elementToStartWith));
                LogicElements.add(logicElement);
            }
        }
    }

    /**
     * Method that creates an array of parsed booleans, starting with some element from the array of strings
     *
     * @param startWith element of the array to start with
     * @param arr       of strings to be made boolean[]
     * @return booleans array
     */
    private boolean[] getBooleans(String[] arr, int startWith) {
        boolean[] booleans = new boolean[arr.length - startWith];
        for (int i = startWith, j = 0; i < arr.length; i++, j++) {
            booleans[j] = Boolean.parseBoolean(arr[i]);
        }
        return booleans;
    }

    @Override
    public String toString() {
        return "ElementRepository{" +
                "LogicElements=" + listToString(LogicElements) +
                '}';
    }

    /**
     * Method to create a string representation of an arrayList, where every element will start from new line
     *
     * @param list - a list for representation
     * @return string
     */
    private String listToString(List<LogicElement> list) {
        return list
                .stream()
                .map(LogicElement::toString)
                .collect(Collectors.joining("\n"));
    }
}
