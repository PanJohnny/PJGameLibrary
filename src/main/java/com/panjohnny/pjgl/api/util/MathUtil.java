package com.panjohnny.pjgl.api.util;

@SuppressWarnings("unused")
public final class MathUtil {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static boolean between(int n, int min, int max) {
        return n >= min && n <= max;
    }
}
