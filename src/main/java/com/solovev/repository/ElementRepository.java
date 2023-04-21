package com.solovev.repository;

import com.solovev.factory.ElementFactory;
import com.solovev.factory.ElementFactoryI;
import com.solovev.factory.FactoryEnum;
import com.solovev.model.LogicElement;

import java.io.*;
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
     * Constructor of the class to fill the list of logic elements with values from the cvs file,
     * all incorrect data will just be ignored;
     * Examples:
     * Whole line that will NOT start from map key will be ignored;
     * Word "true1" will be ignored when parsing
     *
     * @param file file name
     * @param map  Map of names of the logic elements as well as their factories
     * @throws IOException in case file does not exist
     */
    public ElementRepository(File file, Map<String, ElementFactoryI> map) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCounter = 1;
            while ((line = bufferedReader.readLine()) != null) {
                Queue<String> separated = Arrays.stream(line.split(SEPARATOR))
                        .collect(Collectors.toCollection(ArrayDeque::new));
                try {
                    String startsWith = separated.poll().strip();
                    ElementFactoryI logicElementFactory = map.get(startsWith);
                    if (logicElementFactory == null) {
                        throw new InvalidObjectException(
                                String.format(" Line#%d skipped; Started with:\"%s\" ;To be parsed line have to start from: %s", lineCounter, startsWith, map.keySet()));
                    }
                    boolean[] booleans = getBooleans(separated);
                    LogicElement logicElement = logicElementFactory.newInstance(booleans.length);
                    logicElement.fill(getBooleans(separated));
                    LogicElements.add(logicElement);
                } catch (InvalidObjectException e) {
                    System.err.println(e);
                } finally {
                    lineCounter++;
                }
            }
        }
    }

    /**
     * Constructor of the class to fill the list of logic elements with values from the cvs file,
     * all incorrect data will just be ignored;
     * Examples:
     * Whole line that will NOT start from AND,OR,XOR (will be striped());
     * Word "true1" will be ignored when parsing
     *
     * @param file file name
     * @throws IOException in case file does not exist
     */
    public ElementRepository(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCounter = 1;
            while ((line = bufferedReader.readLine()) != null) {
                Queue<String> separated = Arrays.stream(line.split(SEPARATOR))
                        .collect(Collectors.toCollection(ArrayDeque::new));
                String startsWith = separated.poll().strip();
                try {

                    FactoryEnum type = FactoryEnum.valueOf(startsWith);
                    boolean[] booleans = getBooleans(separated);
                    LogicElement logicElement = ElementFactory
                            .newInstance(type, booleans.length);
                    logicElement.fill(booleans);
                    LogicElements.add(logicElement);
                } catch (IllegalArgumentException e) {
                String message = String.format(" Line#%d skipped; Started with:\"%s\" ;To be parsed line have to start from: %s",
                        lineCounter,
                        startsWith,
                        Arrays.toString(FactoryEnum.values()));
                System.err.println(e + message);
                } finally {
                    lineCounter++;
                }
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
        for (int i = 0; i < listOfBooleans.size(); i++) {
            booleans[i] = listOfBooleans.get(i);
        }
        return booleans;
    }

    /**
     * Method to sort collection in this object by natural order
     */
    public void sort() {
        LogicElements.sort(Comparator.naturalOrder());
    }

    /**
     * Method to sort collection based on the given Comparator
     *
     * @param comparator to sort LogicElements List
     */
    public void sort(Comparator<LogicElement> comparator) {
        LogicElements.sort(comparator);
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

    @Override
    public String toString() {
        return "ElementRepository{" +
                "LogicElements=[\n" + listToString(LogicElements) +
                "\n]" +
                '}';
    }


}
