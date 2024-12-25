package ocp;

import java.util.*;

public record MeetingInformation(
    String[] requiredParticipants,
    String[] optionalParticipants,
    Optional<String> place,
    Optional<String> resources
) {

    static MeetingInformation parse(final String[] fields) {
        return new MeetingInformation(
            MeetingInformation.parseParticipants(fields[CSVField.REQUIRED_PARTICIPANTS.column]),
            MeetingInformation.parseParticipants(fields[CSVField.OPTIONAL_PARTICIPANTS.column]),
            MeetingInformation.parsePlace(fields[CSVField.PLACE.column]),
            MeetingInformation.parseResources(fields[CSVField.RESOURCES.column])
        );
    }

    private static Optional<String> parseResources(final String resources) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Optional<String> parsePlace(final String place) {
        // TODO Auto-generated method stub
        return null;
    }

    private static String[] parseParticipants(final String participants) {
        // TODO Auto-generated method stub
        return null;
    }

}
