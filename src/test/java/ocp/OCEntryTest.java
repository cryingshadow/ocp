package ocp;

import java.time.*;

import org.testng.*;
import org.testng.annotations.*;

public class OCEntryTest {

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
