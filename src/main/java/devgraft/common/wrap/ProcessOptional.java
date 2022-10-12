package devgraft.common.wrap;

import java.util.function.Supplier;

public class ProcessOptional<T> extends ProcessResult {
    private final T value;

    public ProcessOptional(final T value, final String message, final boolean success) {
        super(message, success);
        this.value = value;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (!isSuccess()) throw exceptionSupplier.get();
        return value;
    }

    public T orElseThrow() {
        if (!isSuccess()) throw new ProcessOptionalException(getMessage());
        return value;
    }
}
