package tvprogram.service;

import org.junit.Test;
import tvprogram.infrastructure.retry.RetriableRunner;
import tvprogram.infrastructure.retry.SleepBeforeNextTryRetrier;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fest.assertions.api.Assertions.assertThat;

public class RetrierTest {

    private static final String RESULT = "result";
    private RetriableRunner<String> retrier;

    @Test
    public void testRunFailsAllTimes() throws Exception {
        givenRetriesTimes(5);

        AtomicInteger actual = new AtomicInteger();
        Optional<String> result = retrier.run(() -> {
            actual.getAndIncrement();
            throw new Exception("lets fail again");
        });

        assertThat(result.isPresent()).isFalse();
        assertThat(actual.get()).isEqualTo(5);
    }


    @Test
    public void testRunFailOnceAndThenReturns() throws Exception {
        givenRetriesTimes(2);

        AtomicInteger actual = new AtomicInteger();
        Optional<String> result = retrier.run(() -> {
            if (actual.getAndIncrement() < 1) {
                throw new Exception("lets fail again");
            }
            return RESULT;

        });

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(RESULT);
        assertThat(actual.get()).isEqualTo(2);
    }

    private void givenRetriesTimes(int retries) {
        retrier = new SleepBeforeNextTryRetrier<>(retries, 0);
    }
}
