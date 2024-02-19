import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Err<Void, E> implements Result<Void, E> {

    final E errorValue;

    private Err(final E e) {
        this.errorValue = e;
    }

    public static <Void, E> Err<Void, E> of(@NotNull final E errorValue) {
        return new Err<>(errorValue);
    }
    @Override
    public Void orElseThrow() throws RuntimeException {
        throw new RuntimeException("Calling unwrap() on Err!");
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public void ifOk(final Consumer<? super Void> action) {
    }

    @Override
    public void ifErr(final Consumer<? super E> action) {
        action.accept(errorValue);
    }

    @Override
    public Optional<? extends Void> orElse(final Supplier<? extends Optional<? extends Void>> orSupplier) {
        return orSupplier.get();
    }

    @Override
    public Void orElseGet(final Supplier<? extends Void> supplier) {
        return supplier.get();
    }

    @Override
    public <U> Optional<U> mapOk(final Function<? super Void, ? extends U> okMapper) {
        return Optional.empty();
    }

    @Override
    public Void expect(final String message) throws RuntimeException {
        throw new RuntimeException(message);
    }
}