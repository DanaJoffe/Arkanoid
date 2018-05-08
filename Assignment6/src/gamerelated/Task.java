package gamerelated;

/**
 * a Task is something that can run.
 * @author joffeda.
 * @param <T> a return type.
 */
public interface Task<T> {
    /**
     * when this task runs, it returns some information.
     * @return some value.
     */
    T run();
}