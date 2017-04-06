package tvprogram.test;

import tvprogram.domain.WeeklyNewProgramme;

import static org.fest.assertions.api.Assertions.assertThat;

public class TestUtils {
    public static void assertWeeklyNewProgrammesAreEqual(WeeklyNewProgramme actual, WeeklyNewProgramme expected) {
        assertThat(actual.getSeries().getName()).isEqualTo(expected.getSeries().getName());
        assertThat(actual.getSeries().getSeason().getNumber()).isEqualTo(expected.getSeries().getSeason().getNumber());
        assertThat(actual.getSeries().getSeason().getEpisode().getName()).isEqualTo(expected.getSeries().getSeason().getEpisode().getName());
        assertThat(actual.getSeries().getSeason().getEpisode().getNumber()).isEqualTo(expected.getSeries().getSeason().getEpisode().getNumber());
    }
}
