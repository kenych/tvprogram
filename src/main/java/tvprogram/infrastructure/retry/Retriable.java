package tvprogram.infrastructure.retry;

public interface Retriable<T> {
    T retry() throws Exception;
}
