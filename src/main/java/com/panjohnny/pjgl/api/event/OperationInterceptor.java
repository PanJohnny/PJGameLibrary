package com.panjohnny.pjgl.api.event;

@SuppressWarnings("unused")
public class OperationInterceptor {
    private boolean value;

    public OperationInterceptor() {
        this.value = false;
    }

    public void intercept() {
        this.value = true;
    }

    public void setIntercept(boolean intercept) {
        this.value = intercept;
    }

    public boolean isIntercepted() {
        return value;
    }
}
