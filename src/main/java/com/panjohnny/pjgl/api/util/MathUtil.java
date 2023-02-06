package com.panjohnny.pjgl.api.util;

/**
 * Utility class containing clamp and between methods for float, int and double values.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public final class MathUtil {

    /**
     * Clamps the given float value within the specified minimum and maximum values.
     *
     * @param val the value to be clamped
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Clamps the given int value within the specified minimum and maximum values.
     *
     * @param val the value to be clamped
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Clamps the given double value within the specified minimum and maximum values.
     *
     * @param val the value to be clamped
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Determines if the given integer value is between the specified minimum and maximum values.
     *
     * @param n the value to be tested
     * @param min the minimum value
     * @param max the maximum value
     * @return {@code true} if the value is between the minimum and maximum, {@code false} otherwise
     */
    public static boolean between(int n, int min, int max) {
        return n >= min && n <= max;
    }
}