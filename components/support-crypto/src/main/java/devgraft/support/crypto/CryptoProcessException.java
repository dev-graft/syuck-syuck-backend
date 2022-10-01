package devgraft.support.crypto;

public class CryptoProcessException extends RuntimeException {
    public CryptoProcessException(final Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
