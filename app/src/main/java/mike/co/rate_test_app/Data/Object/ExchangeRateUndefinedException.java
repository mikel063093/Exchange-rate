package mike.co.rate_test_app.Data.Object;

/**
 * Created by miguelalegria on 7/29/15.
 */

public class ExchangeRateUndefinedException extends Exception {

    private static final long serialVersionUID = 1L;

    public ExchangeRateUndefinedException() {
    }

    public ExchangeRateUndefinedException(String message) {
        super(message);
    }

    public ExchangeRateUndefinedException(Throwable cause) {
        super(cause);
    }

    public ExchangeRateUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

}