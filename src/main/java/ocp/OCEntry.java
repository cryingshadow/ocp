package ocp;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public record OCEntry(
    String subject,
    LocalDateTime start,
    LocalDateTime end,
    String owner,
    String description,
    AdditionalInformation additionalInformation
) implements Comparable<OCEntry> {

    private static final Comparator<OCEntry> COMPARATOR =
        new LexicographicComparator<OCEntry>(OCEntry::start, OCEntry::end, OCEntry::subject);

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("d.M.yyyyHH:mm:ss");

    public static <T> Map<T, List<OCEntry>> group(
        final Collection<OCEntry> entries,
        final Function<OCEntry, T> groupBy
    ) {
        final Map<T, List<OCEntry>> result = new LinkedHashMap<T, List<OCEntry>>();
        for (final OCEntry entry : entries) {
            final T key = groupBy.apply(entry);
            if (!result.containsKey(key)) {
                result.put(key, new LinkedList<OCEntry>());
            }
            result.get(key).add(entry);
        }
        return result;
    }

    public static List<OCEntry> parse(final BufferedReader reader) throws IOException {
        final List<String> lines = new LinkedList<String>();
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        return OCEntry.parse(lines);
    }

    public static List<OCEntry> parse(final File file) throws IOException {
        return OCEntry.parse(Files.lines(file.toPath()).skip(1).filter(line -> !line.isBlank()).toList());
    }

    public static <T> Map<T, List<OCEntry>> parseAndGroup(
        final BufferedReader reader,
        final Function<OCEntry, T> groupBy
    ) throws IOException {
        return OCEntry.group(OCEntry.parse(reader), groupBy);
    }

    public static OCEntry parseEntry(final String line) {
        final String[] fields = OCEntry.parseFields(line);
        return new OCEntry(
            OCEntry.parseSubject(fields[CSVField.SUBJECT.column]),
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
            OCEntry.parseOwner(fields[CSVField.OWNER.column]),
            OCEntry.parseDescription(fields[CSVField.DESCRIPTION.column]),
            AdditionalInformation.parse(fields)
        );
    }

    static boolean parseBoolean(final String text) {
        return text != null && "ein".equals(text.toLowerCase());
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

    static LocalDateTime parseLocalDateTime(
        final String date,
        final String time,
        final String wholeDay,
        final boolean start
    ) {
        final boolean isWholeDay = OCEntry.parseBoolean(wholeDay);
        final String adjustedTime = isWholeDay ? "00:00:00" : time;
        final LocalDateTime result = LocalDateTime.parse(date + adjustedTime, OCEntry.FORMAT);
        return isWholeDay && !start ? result.plusDays(1) : result;
    }

    private static Stream<String> combineQuoted(final List<String> lines) {
        final List<String> result = new LinkedList<String>();
        final Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String currentLine = iterator.next();
            while (OCEntry.endQuoted(currentLine) && iterator.hasNext()) {
                currentLine += "\n" + iterator.next();
            }
            result.add(currentLine);
        }
        return result.stream();
    }

    private static boolean endQuoted(final String line) {
        boolean quoted = false;
        final char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '"' && (i == 0 || chars[i - 1] != '\\')) {
                quoted = !quoted;
            }
        }
        return quoted;
    }

    private static List<OCEntry> parse(final List<String> lines) {
        return OCEntry.combineQuoted(lines).map(OCEntry::parseEntry).toList();
    }

    private static String parseDescription(final String description) {
        return OCEntry.parseString(description);
    }

    private static String parseOwner(final String owner) {
        return OCEntry.parseString(owner);
    }

    private static String parseString(final String text) {
        return text == null ? "" : text;
    }

    private static String parseSubject(final String subject) {
        return OCEntry.parseString(subject);
    }

    @Override
    public int compareTo(final OCEntry other) {
        return OCEntry.COMPARATOR.compare(this, other);
    }

    @Override
    public String toString() {
        return String.format(
            "{\"subject\": \"%s\",\n\"start\": \"%s\",\n\"end\": \"%s\",\n\"owner\": \"%s\",\n\"description\": \"%s\",\n%s}",
            this.subject(),
            this.start(),
            this.end(),
            this.owner(),
            this.description(),
            this.additionalInformation()
        );
    }

}
