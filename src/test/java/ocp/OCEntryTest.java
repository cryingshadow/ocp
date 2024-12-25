package ocp;

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

    @Test
    public void parseLocalDateTimeTest() {
        throw new RuntimeException("Test not implemented");
    }

}
