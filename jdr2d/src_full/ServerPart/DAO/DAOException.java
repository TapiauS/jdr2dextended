package ServerPart.DAO;

public class DAOException extends Exception{
    private ErrorType errortype;


    public DAOException(ErrorType errortype) {
        this.errortype = errortype;
    }

    public DAOException(String message, ErrorType errortype) {
        super(message);
        this.errortype = errortype;
    }

    public DAOException(String message, Throwable cause, ErrorType errortype) {
        super(message, cause);
        this.errortype = errortype;
    }

    public DAOException(Throwable cause, ErrorType errortype) {
        super(cause);
        this.errortype = errortype;
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorType errortype) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errortype = errortype;
    }

    //getters


    public ErrorType getErrortype() {
        return errortype;
    }
}
