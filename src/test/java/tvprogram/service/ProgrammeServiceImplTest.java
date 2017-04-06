package tvprogram.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tvprogram.domain.Programme;
import tvprogram.domain.WeeklyNewProgramme;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static tvprogram.domain.BroadcastFiler.ALL;
import static tvprogram.domain.BroadcastFiler.NOT_SHOWN;
import static tvprogram.test.TestUtils.assertWeeklyNewProgrammesAreEqual;
import static tvprogram.test.Tests.*;

public class ProgrammeServiceImplTest {
    private ProgrammeService programmeService;

    @Mock
    private BroadcastService broadcastService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        programmeService = new ProgrammeServiceImpl(broadcastService);
    }

    @Test
    public void testWeeklyNewContainsNewOnly() {
        givenWeeklyProgrammes(
                new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW),
                new Programme(SERIES_2, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY),
                new Programme(SERIES_3, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO));

        List<WeeklyNewProgramme> actualProgrammes = programmeService.weeklyNewProgrammes(ALL);

        assertThat(actualProgrammes).hasSize(2);
        assertWeeklyNewProgrammesAreEqual(actualProgrammes.get(0), new WeeklyNewProgramme(SERIES_1));
        assertWeeklyNewProgrammesAreEqual(actualProgrammes.get(1), new WeeklyNewProgramme(SERIES_2));
    }

    @Test
    public void testWeeklyNewIsEmptyWhenNotNew() {
        givenWeeklyProgrammes(
                new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO),
                new Programme(SERIES_2, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO));

        List<WeeklyNewProgramme> actualProgrammes = programmeService.weeklyNewProgrammes(ALL);

        assertThat(actualProgrammes).isEmpty();
    }

    @Test
    public void testWeeklyNewIsEmptyWhenNewButShowAlready() {
        givenWeeklyProgrammes(
                new Programme(SERIES_1, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY));

        List<WeeklyNewProgramme> actualProgrammes = programmeService.weeklyNewProgrammes(NOT_SHOWN);

        assertThat(actualProgrammes).isEmpty();
    }

    private void givenWeeklyProgrammes(Programme... programmes) {
        when(broadcastService.weeklyProgrammes()).thenReturn(Arrays.asList(programmes));
    }
}
