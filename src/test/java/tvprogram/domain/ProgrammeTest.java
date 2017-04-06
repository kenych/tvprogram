package tvprogram.domain;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static tvprogram.test.Tests.*;

public class ProgrammeTest {

    @Test
    public void testIsNew() {
        assertThat(new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW).isNew()).isTrue();
        assertThat(new Programme(SERIES_1, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY).isNew()).isTrue();
        assertThat(new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO).isNew()).isFalse();
    }

    @Test
    public void testIsNotShown() {
        assertThat(new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW).isNotShown()).isTrue();
        assertThat(new Programme(SERIES_1, BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY).isNotShown()).isFalse();
        assertThat(new Programme(SERIES_1, BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO).isNotShown()).isTrue();
    }

}
