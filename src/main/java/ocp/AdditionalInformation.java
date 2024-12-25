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

    private static Optional<Integer> parseTravelKilometers(final String travelKilometers) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Optional<String> parseAccountingInformation(final String accountingInformation) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Optional<LocalDateTime> parseReminder(
        final String isReminderSet,
        final String reminderDate,
        final String reminderTime
    ) {
        // TODO Auto-generated method stub
        return null;
    }

}
