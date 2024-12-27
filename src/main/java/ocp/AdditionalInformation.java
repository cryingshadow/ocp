package ocp;

import java.time.*;
import java.util.*;

public record AdditionalInformation(
    MeetingInformation meetingInformation,
    Optional<LocalDateTime> reminder,
    Optional<String> accountingInformation,
    Optional<Integer> travelKilometers,
    AppearanceInformation appearanceInformation
) {

    static AdditionalInformation parse(final String[] fields) {
        return new AdditionalInformation(
            MeetingInformation.parse(fields),
            AdditionalInformation.parseReminder(
                fields[CSVField.REMINDER.column],
                fields[CSVField.REMINDER_DATE.column],
                fields[CSVField.REMINDER_TIME.column]
            ),
            AdditionalInformation.parseAccountingInformation(fields[CSVField.ACCOUNTING_INFORMATION.column]),
            AdditionalInformation.parseTravelKilometers(fields[CSVField.TRAVEL_KILOMETERS.column]),
            AppearanceInformation.parse(fields)
        );
    }

    static String[] parseArray(final String array) {
        if (array == null) {
            return new String[] {};
        }
        return array.split(";");
    }

    static Optional<String> parseNullable(final String text) {
        return text == null || text.isBlank() ? Optional.empty() : Optional.of(text);
    }

    private static Optional<String> parseAccountingInformation(final String accountingInformation) {
        return AdditionalInformation.parseNullable(accountingInformation);
    }

    private static Optional<LocalDateTime> parseReminder(
        final String isReminderSet,
        final String reminderDate,
        final String reminderTime
    ) {
        if (OCEntry.parseBoolean(isReminderSet)) {
            return Optional.of(OCEntry.parseLocalDateTime(reminderDate, reminderTime, "Aus", true));
        }
        return Optional.empty();
    }

    private static Optional<Integer> parseTravelKilometers(final String travelKilometers) {
        return AdditionalInformation.parseNullable(travelKilometers).map(Integer::parseInt);
    }

    @Override
    public String toString() {
        return String.format(
            "%s,\n\"reminder\": \"%s\",\n\"accountingInformation\": \"%s\",\n\"travelKilometers\": \"%s\",\n%s",
            this.meetingInformation(),
            this.reminder(),
            this.accountingInformation(),
            this.travelKilometers(),
            this.appearanceInformation()
        );
    }

}
