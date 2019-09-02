package mcc.Exception;

public class MissingFileException extends Exception {

    private static final long serialVersionUID = 4367685163662350536L;

    public MissingFileException(String message) {
        super(message);
    }
}