import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Ok<T, Void> implements Result<T, Void> {
    final T okValue;

    private Ok(final T val) {
        this.okValue = val;
    }
    public static <T, Void> Ok<T, Void> of(@NotNull final T okValue) {
        return new Ok<>(okValue);
    }
    public static <Void> Ok<Void, Void> empty() {
        return new Ok<>(null);
    }
    @Override
    public T orElseThrow() {
        return okValue;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public void ifOk(final Consumer<? super T> action) {
        action.accept(okValue);
    }

    @Override
    public void ifErr(final Consumer<? super Void> action) {
    }
    @Override
    public Optional<? extends T> orElse(final Supplier<? extends Optional<? extends T>> orSupplier) {
        return Optional.of(okValue);
    }

    @Override
    public T orElseGet(final Supplier<? extends T> supplier) {
        return okValue;
    }

    @Override
    public <U> Optional<U> mapOk(final Function<? super T, ? extends U> okMapper) {
        return Optional.ofNullable(okMapper.apply(okValue));
    }

    @Override
    public T expect(final String message) throws RuntimeException {
        return okValue;
    }
}