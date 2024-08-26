package com.panjohnny.pjgl.adapt;

import com.panjohnny.pjgl.api.PJGLInitializer;

import java.lang.annotation.*;

/**
 * This is annotation used to declare to which adaptation this belongs
 * @see PJGLInitializer#getIdentifier()
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Adaptation {
    /**
     * @return An identifier of your adaptation
     * @see PJGLInitializer#getIdentifier()
     */
    String value();
}
