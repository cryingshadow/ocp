package ocp;

public record AppearanceInformation(
    boolean privateEntry,
    String showAs,
    String[] categories,
    String priority,
    String classification
) {

    static AppearanceInformation parse(final String[] fields) {
        return new AppearanceInformation(
            AppearanceInformation.parsePrivateEntry(fields[CSVField.PRIVATE_ENTRY.column]),
            fields[CSVField.SHOW_AS.column],
            AppearanceInformation.parseCategories(fields[CSVField.CATEGORIES.column]),
            fields[CSVField.PRIORITY.column],
            fields[CSVField.CLASSIFICATION.column]
        );
    }

    private static String[] parseCategories(final String categories) {
        // TODO Auto-generated method stub
        return null;
    }

    private static boolean parsePrivateEntry(final String privateEntry) {
        // TODO Auto-generated method stub
        return false;
    }}
