package org.yanemr.ccjq;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "ccjq")
public class Ccjq {

    @Parameters(
            index = "0",
            description = "The path to the JSON file. Enter '.' to read from the input stream"
    )
    private String filePath;

    enum Type {
        LIST, WHOLE_NUMBER, DOUBLE, STRING, OBJECT;
    }

    String json;

    /**
     * Parses a JSON string and checks if it is valid. In case it is not valid, throws an exception.
     * @param json
     * @return true in case the JSON is valid
     */
    public boolean parse(String json) {
        this.json = json;
        CharacterIterator characterIterator = new StringCharacterIterator(json);

        skipWhitespaces(characterIterator);
        checkType(characterIterator);
        skipWhitespaces(characterIterator);

        if (characterIterator.current() != CharacterIterator.DONE) {
            throw new IllegalArgumentException("Unexpected token");
        }
        return true;
    }

    //TODO: Refactor so that it returns the expected type to parse from the enum **Type**
    // And once the value is returned, apply the required check
    private void checkType(CharacterIterator iterator) {
        char currentChar = iterator.current();
        if (currentChar == '{') {
            parseObject(iterator);

        } else if (Character.isDigit(currentChar) || currentChar == '.') {
            parseNumber(iterator);

        } else if (currentChar == '[') {
            parseList(iterator);

        } else if (currentChar == '"') {
            extractString(iterator);

        } else {
            throw new IllegalArgumentException("Unexpected token");
        }
    }

    /**
     * Parses a JSON object. It iterates over the characters provided and extracts the <br>
     * keys and values present. Currently, the supported value types are strings and numerals.
     * @param characterIterator the input iterator
     */
    private void parseObject(CharacterIterator characterIterator) {
        characterIterator.next();

        boolean expectingNextKey = false;
        while (characterIterator.current() != '}') {
            expectingNextKey = false;
            skipWhitespaces(characterIterator);
            extractString(characterIterator);

            skipWhitespaces(characterIterator);
            if (characterIterator.current() != ':') {
                throw new IllegalArgumentException("Expected a separator");
            }
            characterIterator.next();
            skipWhitespaces(characterIterator);

            checkType(characterIterator);
            skipWhitespaces(characterIterator);
            char currentChar = characterIterator.current();
            if (currentChar == ',') {
                expectingNextKey = true;
                characterIterator.next();
            } else if (currentChar != '}') {
                throw new IllegalArgumentException();
            }
        }
        if  (expectingNextKey) {
            throw new IllegalArgumentException("Unexpected separator");
        }
        characterIterator.next();
    }

    private String extractString(CharacterIterator iterator) {
        if (iterator.current() != '"') {
            throw new IllegalArgumentException();
        }
        StringBuilder key = new StringBuilder();
        while (iterator.next() != '"') {
            key.append(iterator.current());
        }
        iterator.next();
        return key.toString();
    }

    /**
     * Parses the incoming value as a number.<br>
     * This number can be either an integer / long or a float / double (with a floating point)
     * @param iterator the input iterator
     * @return a number encapsulated in a string
     */
    private String parseNumber(CharacterIterator iterator) {
        skipWhitespaces(iterator);
        while (iterator.current() == '0') {
            iterator.next();
        }
        boolean floatingPointFound = false;
        StringBuilder number = new StringBuilder();
        char currentChar = iterator.current();
        while (currentChar == '.'
                || Character.isDigit(currentChar)) {

            if (currentChar == '.' && !floatingPointFound) {
                floatingPointFound = true;
                number.append('.');
            } else if (currentChar == '.') {
                throw new IllegalArgumentException("Invalid numerical expression");
            }
            number.append(currentChar);
            currentChar = iterator.next();
        }
        return number.toString();
    }

    private void parseList(CharacterIterator iterator) {
        iterator.next();

        boolean expectingValue = false;
        while (iterator.current() != ']') {
            expectingValue = false;
            skipWhitespaces(iterator);
            checkType(iterator);
            skipWhitespaces(iterator);

            if (iterator.current() == ',') {
                expectingValue = true;
                iterator.next();
            } else if (iterator.current() != ']') {
                throw new IllegalArgumentException("Invalid token for list");
            }
        }
        if (expectingValue) {
            throw new IllegalArgumentException("Unexpected separator");
        }
        iterator.next();
    }

    private void skipWhitespaces(CharacterIterator characterIterator) {
        while (Character.isWhitespace(characterIterator.current())) {
            characterIterator.next();
        }
    }

}
