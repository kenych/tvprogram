package tvprogram.infrastructure.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;


public class SleepBeforeNextTryRetrier<T> implements RetriableRunner<T> {

    private Logger logger = LoggerFactory.getLogger(SleepBeforeNextTryRetrier.class);

    private int numberOfRetries = 0;
    private int sleepMs;

    public SleepBeforeNextTryRetrier(int numberOfRetries, int sleepMs) {
        checkArgument(numberOfRetries > 0, "numberOfRetries mist be positive: %s", numberOfRetries);
        checkArgument(sleepMs >= 0, "sleepMs mist be positive or 0: %s", sleepMs);

        this.numberOfRetries = numberOfRetries;
        this.sleepMs = sleepMs;
    }

    public Optional<T> run(Retriable<T> retriable) {

        int tries = 1;

        do {
            try {
                return Optional.of(retriable.retry());
            } catch (Exception e) {
                logger.error("Error try {} out of {}. Error details: {}", tries, numberOfRetries, e.toString());

                if (sleepMs > 0 && tries < numberOfRetries) {
                    sleepBeforeNextTry(sleepMs);
                }
            }
        } while (tries++ < numberOfRetries);

        return Optional.empty();
    }

    private void sleepBeforeNextTry(int ms) {
        logger.info("Waiting {} ms before next try...", ms);

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.error("InterruptedException during sleep", e);
        }
    }


}
