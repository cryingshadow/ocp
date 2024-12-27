package ocp;

import java.time.*;
import java.util.Optional;

import org.testng.*;
import org.testng.annotations.*;

public class OCEntryTest {

    @DataProvider
    public Object[][] parseEntryData() {
        return new Object[][] {
            {
                "\"Mittagspause\",\"19.11.2025\",\"13:00:00\",\"19.11.2025\",\"13:45:00\",\"Aus\",\"Aus\",\"19.11.2025\",\"12:45:00\",\"Thomas Ströder\",,,,,\" \n\",,\"\",\"Normal\",\"Aus\",,\"Normal\",\"4\"",
                new OCEntry(
                    "Mittagspause",
                    LocalDateTime.of(2025, 11, 19, 13, 0),
                    LocalDateTime.of(2025, 11, 19, 13, 45),
                    "Thomas Ströder",
                    " \n",
                    new AdditionalInformation(
                        new MeetingInformation(
                            new String[] {},
                            new String[] {},
                            Optional.empty(),
                            Optional.empty()
                        ),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        new AppearanceInformation(
                            false,
                            OCShowType.ELSEWHERE,
                            new String[] {},
                            "Normal",
                            "Normal"
                        )
                    )
                )
            }
        };
    }

    @Test(dataProvider="parseEntryData")
    public void parseEntryTest(final String line, final OCEntry expected) {
        Assert.assertEquals(OCEntry.parseEntry(line), expected);
    }

    @DataProvider
    public Object[][] parseFieldsData() {
        return new Object[][] {
            {
                "\"Mittagspause\",\"19.11.2025\",\"13:00:00\",\"19.11.2025\",\"13:45:00\",\"Aus\",\"Aus\",\"19.11.2025\",\"12:45:00\",\"Thomas Ströder\",,,,,\" \n\",,\"\",\"Normal\",\"Aus\",,\"Normal\",\"4\"",
                new String[] {
                    "Mittagspause",
                    "19.11.2025",
                    "13:00:00",
                    "19.11.2025",
                    "13:45:00",
                    "Aus",
                    "Aus",
                    "19.11.2025",
                    "12:45:00",
                    "Thomas Ströder",
                    null,
                    null,
                    null,
                    null,
                    " \n",
                    null,
                    "",
                    "Normal",
                    "Aus",
                    null,
                    "Normal",
                    "4"
                }
            },
            {
                "",
                new String[] {
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                }
            }
        };
    }

    @Test(dataProvider="parseFieldsData")
    public void parseFieldsTest(final String line, final String[] expected) {
        Assert.assertEquals(OCEntry.parseFields(line), expected);
    }

    @DataProvider
    public Object[][] parseLocalDateTimeData() {
        return new Object[][] {
            {"01.10.2024", "13:45:00", "Aus", true, LocalDateTime.of(2024, 10, 1, 13, 45, 0)},
            {"01.10.2024", "13:45:00", "Aus", false, LocalDateTime.of(2024, 10, 1, 13, 45, 0)},
            {"01.10.2024", "13:45:00", "Ein", true, LocalDateTime.of(2024, 10, 1, 0, 0, 0)},
            {"01.10.2024", "13:45:00", "Ein", false, LocalDateTime.of(2024, 10, 2, 0, 0, 0)}
        };
    }

    @Test(dataProvider="parseLocalDateTimeData")
    public void parseLocalDateTimeTest(
        final String date,
        final String time,
        final String wholeDay,
        final boolean start,
        final LocalDateTime expected
    ) {
        Assert.assertEquals(OCEntry.parseLocalDateTime(date, time, wholeDay, start), expected);
    }

}
