package devgraft.common.wrap;

import java.util.function.Supplier;

public abstract class ProcessOptional<T> {
    private T value;
    private String message;
    private boolean success;

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (!success) throw exceptionSupplier.get();
        return value;
    }

    public T orElseThrow() {
        if (!success) throw new ProcessOptionalException(message);
        return value;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
