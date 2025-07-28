package dac.orientaTCC.exception;

public class SiapeUniqueViolationException extends RuntimeException {//exceção nova

    public SiapeUniqueViolationException(String msg) {//pode mudar o nome do parametro
        super(msg);
    }
}
