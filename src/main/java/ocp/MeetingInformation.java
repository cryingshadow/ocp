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
            AdditionalInformation.parseArray(fields[CSVField.REQUIRED_PARTICIPANTS.column]),
            AdditionalInformation.parseArray(fields[CSVField.OPTIONAL_PARTICIPANTS.column]),
            MeetingInformation.parsePlace(fields[CSVField.PLACE.column]),
            MeetingInformation.parseResources(fields[CSVField.RESOURCES.column])
        );
    }

    private static Optional<String> parsePlace(final String place) {
        return AdditionalInformation.parseNullable(place);
    }

    private static Optional<String> parseResources(final String resources) {
        return AdditionalInformation.parseNullable(resources);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof MeetingInformation) {
            final MeetingInformation other = (MeetingInformation)o;
            return Arrays.deepEquals(this.requiredParticipants(), other.requiredParticipants())
                && Arrays.deepEquals(this.optionalParticipants(), other.optionalParticipants())
                && this.place().equals(other.place())
                && this.resources().equals(other.resources());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.requiredParticipants()) * 3
            + Arrays.hashCode(this.optionalParticipants()) * 5
            + this.place().hashCode() * 7
            + this.resources().hashCode() * 11;
    }

    @Override
    public String toString() {
        return String.format(
            "\"requiredParticipants\": %s,\n\"optionalParticipants\": %s,\n\"place\": \"%s\",\n\"resources\": \"%s\"",
            Arrays.toString(this.requiredParticipants()),
            Arrays.toString(this.optionalParticipants()),
            this.place(),
            this.resources()
        );
    }

}
