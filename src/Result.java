import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T, E> permits Ok, Err {

    /**
     * This method evaluates the supplying function and wraps the resulting value into the
     * respective Result-Type. If the supplying function throws any Throwable it is wrapped inside
     * an instance of {@link Err}, otherwise the value of the Suppliers get()-method is wrapped inside an instance of {@link Ok}
     *
     * @param supplier the supplying-function that can throw an Exception
     * @return If the supplier throws an Exception an instance of {@link Err}
     *         If the supplier returns a value without any Exceptions the value
     *         produced by the Supplier is returned wrapped into an instance of {@link Ok}
     */
    static <T> Result<T, RuntimeException> of(@NotNull final Supplier<? extends T> supplier) {
        try {
            return Ok.of(supplier.get());
        } catch (Throwable t) {
            if (t instanceof RuntimeException exception) {
                return Err.of(exception);
            }
        }
        return Err.of(new RuntimeException());
    }

    /**
     * If the Result is an instance of {@link Ok}, returns the Ok-value, otherwise throws a RuntimeException.
     * If the Result is an instance of an empty {@link Ok} (as created by Ok.empty()), this will return null.
     *
     * @return the Ok-value if the Result is an instance of {@link Ok}
     *
     * @throws RuntimeException if the Result is an instance of {@link Err}
     */
    T orElseThrow() throws RuntimeException;

    /**
     * If the Result is an instance of {@link Ok}, returns true, otherwise false.
     *
     * @return true if the Result is an instance of {@link Ok},
     *         false if the Result is an instance of {@link Err}
     */
    boolean isOk();

    /**
     * If the Result is an instance of {@link Err}, returns true, otherwise false.
     *
     * @return false if the Result is an instance of {@link Ok},
     *         true if the Result is an instance of {@link Err}
     */
    boolean isErr();

    /**
     * If the Result is an instance of {@link Ok}, performs the given action with the Ok-value, otherwise does nothing.
     *
     * @param action the action to be performed, if the Result is an instance of {@link Ok}
     *
     * @throws NullPointerException if the action function is null and the Result is an instance of {@link Ok}
     *
     */
    void ifOk(final Consumer<? super T> action);

    /**
     * If the Result is an instance of {@link Err}, performs the given action with the Err-value, otherwise does nothing.
     *
     * @param action the action to be performed, if the Result is an instance of {@link Err}
     *
     * @throws NullPointerException if the action function is null and the Result is an instance of {@link Err}
     */
    void ifErr(final Consumer<? super E> action);

    /**
     * If the Result is an instance of {@link Ok}, returns an Optional describing the ok-value, otherwise returns an Optional produced by the supplying function.
     *
     * @param orSupplier the supplying function that produces an Optional to be returned
     *
     * @return returns an Optional describing the value of this Optional, if the Result is an instance of {@link Ok},
     * otherwise an Optional produced by the supplying function.
     *
     * @throws NullPointerException if the supplying function is null and the Result is an instance of {@link Err}
     */
    Optional<? extends T> orElse(final Supplier<? extends Optional<? extends T>> orSupplier);

    /**
     * If the Result is an instance of {@link Ok}, returns the ok-value, otherwise returns the value produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     *
     * @return the ok-value, if the Result is an instance of {@link Ok}, otherwise the value produced by the supplying function
     *
     * @throws NullPointerException if the supplying function is null and the Result is an instance of {@link Err}
     */
    T orElseGet(final Supplier<? extends T> supplier);

    /**
     *  If the Result is an instance of {@link Ok}, returns an Optional describing (as if by ofNullable) the result of applying the given mapping function to the Ok-value,
     *  otherwise returns an empty Optional. If the mapping function returns a null result then this method returns an empty Optional.
     *
     *  @param okMapper the mapping function to apply to the Result, if it is an instance of {@link Ok}
     *
     *  @return an Optional describing the result of applying the mapping function to the Ok-value of this Result,
     *          if the Result is an instance of {@link Ok}, otherwise an empty Optional
     *
     *  @throws NullPointerException if the mapping function is null
     *
     */
    <U> Optional<U> mapOk(final Function<? super T, ? extends U> okMapper);

    /**
     * This method returns the Ok-value if the Result is an instance of {@link Ok}, otherwise
     * it will throw a RuntimeException using the given Message-String
     * It is meant to be used the same way assert() is used, thus the thrown RuntimeException should not be caught.
     *
     * @param message the failure message
     * @return the Ok-value if the Result is an instance of {@link Ok}, otherwise nothing
     * @throws RuntimeException if the Result is an instance of {@link Err}
     */
    T expect(final String message) throws RuntimeException;
}
