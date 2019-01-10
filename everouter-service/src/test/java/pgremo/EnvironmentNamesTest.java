package pgremo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class EnvironmentNamesTest {

    enum Case {
        iterator("a-MIXED-property", new String[]{
                "a-MIXED-property",
                "a_MIXED_property",
                "aMIXEDProperty",
                "aMixedProperty",
                "a-mixed-property",
                "a_mixed_property",
                "amixedproperty",
                "A-MIXED-PROPERTY",
                "A_MIXED_PROPERTY",
                "AMIXEDPROPERTY"
        }),
        underscore("nes_ted", new String[]{
                "nes_ted",
                "nes.ted",
                "nesTed",
                "nested",
                "NES_TED",
                "NES.TED",
                "NESTED"
        }),
        plain("plain", new String[]{
                "plain",
                "PLAIN"
        }),
        camelCase("caMel", new String[]{
                "caMel",
                "ca_mel",
                "ca-mel",
                "camel",
                "CAMEL",
                "CA_MEL",
                "CA-MEL"
        }),
        compoundCamelCase("caMelCase", new String[]{
                "caMelCase",
                "ca_mel_case",
                "ca-mel-case",
                "camelcase",
                "CAMELCASE",
                "CA_MEL_CASE",
                "CA-MEL-CASE"
        }),
        periods("environment.value", new String[]{
                "environment.value",
                "environment_value",
                "environmentValue",
                "environmentvalue",
                "ENVIRONMENT.VALUE",
                "ENVIRONMENT_VALUE",
                "ENVIRONMENTVALUE"
        }),
        prefixEndingInPeriod("environment.", new String[]{
                "environment.",
                "environment_",
                "ENVIRONMENT.",
                "ENVIRONMENT_"
        }),
        empty("", new String[]{
                ""
        });

        public String given;
        public String[] expected;

        Case(String given, String[] expected) {
            this.given = given;
            this.expected = expected;
        }

    }

    @DisplayName("Should generate variations on names")
    @ParameterizedTest(name = "{index} {0}")
    @EnumSource(Case.class)
    public void should(Case data) {
        assertThat(new EnvironmentNames(data.given)).containsExactly(data.expected);
    }
}