package ocp;

public enum CSVField {

    ACCOUNTING_INFORMATION(13),
    CATEGORIES(15),
    CLASSIFICATION(20),
    DESCRIPTION(14),
    END_DATE(3),
    END_TIME(4),
    OPTIONAL_PARTICIPANTS(11),
    OWNER(9),
    PLACE(16),
    PRIORITY(17),
    PRIVATE_ENTRY(18),
    REMINDER(6),
    REMINDER_DATE(7),
    REMINDER_TIME(8),
    REQUIRED_PARTICIPANTS(10),
    RESOURCES(12),
    SHOW_AS(21),
    START_DATE(1),
    START_TIME(2),
    SUBJECT(0),
    TRAVEL_KILOMETERS(19),
    WHOLE_DAY(5);

    public final int column;

    private CSVField(final int column) {
        this.column = column;
    }

}
