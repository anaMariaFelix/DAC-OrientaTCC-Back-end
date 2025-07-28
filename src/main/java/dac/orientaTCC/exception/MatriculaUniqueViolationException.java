package dac.orientaTCC.exception;



public class MatriculaUniqueViolationException extends RuntimeException { //classe erro nova pode mudar o nome desse erro se quiser

    public MatriculaUniqueViolationException(String msg) {
        super(msg);
    }
}
