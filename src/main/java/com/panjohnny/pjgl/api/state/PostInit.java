package com.panjohnny.pjgl.api.state;

import com.panjohnny.pjgl.api.PJGL;

import java.lang.annotation.*;

/**
 * Indicates that the following content should be accessed only after init
 * @see PJGL#isInitialized()
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostInit {
}
