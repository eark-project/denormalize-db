package org.eu.eark.denormalizedb.model.analytics;

public interface Predicate {

    boolean satisfiedBy(Object value);
}
