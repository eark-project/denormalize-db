package org.eu.eark.denormalizedb.model.analytics;

public class IsSentence implements Predicate {

    @Override
    public boolean satisfiedBy(Object value) {
        if (value instanceof String) {

            String s = (String) value;
            String word = "[\\pL!,.:;()]++"; // hack, allow braces inside every word
            String wordEnding = "(?:\\s++|$)";
            String sentence = "(?:" + word + wordEnding + ")+";

            return s.matches(sentence);
        }
        return false;
    }

}
