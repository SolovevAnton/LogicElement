package com.solovev.repository;

import com.solovev.factory.ElementFactoryI;
import com.solovev.model.LogicElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class to read some LogicElements from cvs file
 */
public class ElementRepository {
    //csv separator
    private static final String SEPARATOR = ";";
    private ArrayList<LogicElement> LogicElements = new ArrayList<>();

    /**
     * constructor of the class to fill the list of logic elements with values from the cvs file,
     * all incorrect data will just be ignored;
     * Examples:
     * Whole line that will NOT start from AND,OR,XOR;
     * Word "true1" will be ignored when parsing
     *
     * @param file file name
     * @param map  Map of names of the logic elements as well as their factories
     * @throws IOException in case file does not exist
     */
    public ElementRepository(File file, Map<String, ElementFactoryI> map) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int elementToStartWith = 1; //cannot be less than 1

            while ((line = bufferedReader.readLine()) != null) {

                Queue<String> separated = Arrays.stream(line.split(SEPARATOR))
                        .collect(Collectors.toCollection(ArrayDeque::new));

                LogicElement logicElement = map
                        .get(separated[elementToStartWith - 1])
                        .newInstance(separated.length - elementToStartWith);
                logicElement.fill(getBooleans(separated));
                LogicElements.add(logicElement);
            }
        }
    }

    /**
     * Method that creates an array of parsed booleans,Queue of
     * Strings. Only String that is exactly "true" ar "false" case Ignored after strip(), others are skipped
     * Example:
     * "fAlse " -> false, "fa lse" -> ignored
     *
     * @param queue of strings to be made boolean[]
     * @return booleans array, empty array if nothing matched
     */
    private boolean[] getBooleans(Queue<String> queue) {
        Predicate<String> isBooleanString = s -> s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        List<Boolean> listOfBooleans = queue
                .stream()
                .map(String::strip)
                .filter(isBooleanString)
                .map(Boolean::parseBoolean)
                .toList();
        boolean[] booleans = new boolean[listOfBooleans.size()];
        for(int i = 0; i < listOfBooleans.size(); i++){
            booleans[i] = listOfBooleans.get(i);
        }
        return booleans;
    }


    @Override
    public String toString() {
        return "ElementRepository{" +
                "LogicElements=[\n" + listToString(LogicElements) +
                "\n]" +
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
                .collect(Collectors.joining(",\n"));
    }
}
