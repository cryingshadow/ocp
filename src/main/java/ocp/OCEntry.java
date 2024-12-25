package ocp;

import java.time.*;

public record OCEntry(
    String subject,
    LocalDateTime start,
    LocalDateTime end,
    String owner,
    String description,
    AdditionalInformation additionalInformation
) {

    static LocalDateTime parseLocalDateTime(
        final String date,
        final String time,
        final String wholeDay,
        final boolean start
    ) {
        // TODO Auto-generated method stub
        return null;
    }

    public static OCEntry parseEntry(final String line) {
        final String[] fields = OCEntry.parseFields(line);
        return new OCEntry(
            fields[CSVField.SUBJECT.column],
            OCEntry.parseLocalDateTime(
                fields[CSVField.START_DATE.column],
                fields[CSVField.START_TIME.column],
                fields[CSVField.WHOLE_DAY.column],
                true
            ),
            OCEntry.parseLocalDateTime(
                fields[CSVField.END_DATE.column],
                fields[CSVField.END_TIME.column],
                fields[CSVField.WHOLE_DAY.column],
                false
            ),
            fields[CSVField.OWNER.column],
            fields[CSVField.DESCRIPTION.column],
            AdditionalInformation.parse(fields)
        );
    }

    static String[] parseFields(final String line) {
        final String[] fields = new String[CSVField.values().length];
        int currentStart = 0;
        int field = 0;
        final char[] lineArray = line.toCharArray();
        boolean quoted = false;
        for (int i = 0; i < lineArray.length; i++) {
            if (quoted) {
                if (
                    lineArray[i] == '"'
                    && (i == lineArray.length - 1 || i + 1 < lineArray.length && lineArray[i + 1] == ',')
                ) {
                    fields[field] = line.substring(currentStart, i);
                    quoted = false;
                    i++;
                    field++;
                    currentStart = i + 1;
                }
            } else {
                switch (lineArray[i]) {
                case '"':
                    quoted = true;
                    currentStart = i + 1;
                    break;
                case ',':
                    field++;
                    currentStart = i + 1;
                }
            }
        }
        return fields;
    }

}
