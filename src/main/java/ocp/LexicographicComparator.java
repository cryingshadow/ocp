package ocp;

import java.util.*;
import java.util.function.*;

@SuppressWarnings("rawtypes")
public class LexicographicComparator<T> implements Comparator<T> {

    private final List<Function<T, Comparable>> extractors;

    @SafeVarargs
    public LexicographicComparator(final Function<T, Comparable>... extractors) {
        this(List.of(extractors));
    }

    public LexicographicComparator(final List<Function<T, Comparable>> extractors) {
        this.extractors = extractors;
    }

    @Override
    public int compare(final T o1, final T o2) {
        for (final Function<T, Comparable> extractor : this.extractors) {
            @SuppressWarnings("unchecked")
            final int comparison = extractor.apply(o1).compareTo(extractor.apply(o2));
            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(o1.hashCode(), o2.hashCode());
    }

}
