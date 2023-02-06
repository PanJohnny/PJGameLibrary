package com.panjohnny.pjgl.core;

/**
 * Fields for the engine config.
 *
 * @implNote May be moved and modified.
 * @author PanJohnny
 */
public class EngineOptions {
    /**
     * Whether to log FPS or not
     */
    public static boolean logFPS = false;

    /**
     * Whether to log engine state messages such as: PJGL started etc.
     */
    public static boolean logEngineState = true;

    /**
     * FPS setting
     */
    public static int frameLimit = 60;
}
