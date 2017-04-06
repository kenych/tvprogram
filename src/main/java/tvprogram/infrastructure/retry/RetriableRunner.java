package tvprogram.infrastructure.retry;

import java.util.Optional;

public interface RetriableRunner<T> {
    public Optional<T> run(Retriable<T> retriable);
}