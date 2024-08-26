package com.panjohnny.pjgl.api.event;

/**
 * A {@code OperationInterceptor} object can be used to intercept an operation (before it started). Used for example in the exit event.
 *
 * @see com.panjohnny.pjgl.api.PJGLEvents#PJGL_EXIT_EVENT
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class OperationInterceptor {
    private boolean value;

    /**
     * Creates a new {@code OperationInterceptor} with the intercept state set to false.
     */
    public OperationInterceptor() {
        this.value = false;
    }

    /**
     * Interrupts the operation.
     */
    public void intercept() {
        this.value = true;
    }

    /**
     * Sets the intercept state of the operation.
     *
     * @param intercept the new intercept state.
     */
    public void setIntercept(boolean intercept) {
        this.value = intercept;
    }

    /**
     * Returns the current intercept state of the operation.
     *
     * @return true if the operation is intercepted, false otherwise.
     */
    public boolean isIntercepted() {
        return value;
    }
}
