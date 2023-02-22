public class EvalError extends Exception {
    public EvalError(String msg) {
        super(msg);
    } // Throw when failed to evaluate expression via message
}