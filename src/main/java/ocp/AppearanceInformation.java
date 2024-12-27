package ocp;

import java.util.*;

public record AppearanceInformation(
    boolean privateEntry,
    OCShowType showAs,
    String[] categories,
    String priority,
    String classification
) {

    static AppearanceInformation parse(final String[] fields) {
        return new AppearanceInformation(
            AppearanceInformation.parsePrivateEntry(fields[CSVField.PRIVATE_ENTRY.column]),
            OCShowType.parse(fields[CSVField.SHOW_AS.column]),
            AppearanceInformation.parseCategories(fields[CSVField.CATEGORIES.column]),
            fields[CSVField.PRIORITY.column],
            fields[CSVField.CLASSIFICATION.column]
        );
    }

    private static String[] parseCategories(final String categories) {
        return AdditionalInformation.parseArray(categories);
    }

    private static boolean parsePrivateEntry(final String privateEntry) {
        return OCEntry.parseBoolean(privateEntry);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof AppearanceInformation) {
            final AppearanceInformation other = (AppearanceInformation)o;
            return this.privateEntry() == other.privateEntry()
                && this.showAs().equals(other.showAs())
                && Arrays.deepEquals(this.categories(), other.categories())
                && this.priority().equals(other.priority())
                && this.classification().equals(other.classification());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.privateEntry()) * 3
            + this.showAs().hashCode() * 5
            + Arrays.hashCode(this.categories()) * 7
            + this.priority().hashCode() * 11
            + this.classification().hashCode() * 13;
    }

    @Override
    public String toString() {
        return String.format(
            "\"privateEntry\": %s,\n\"showAs\": \"%s\",\n\"categories\": %s,\n\"priority\": \"%s\",\n\"classification\": \"%s\"",
            String.valueOf(this.privateEntry()),
            this.showAs(),
            Arrays.toString(this.categories()),
            this.priority(),
            this.classification()
        );
    }

}
