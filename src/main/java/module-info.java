open module com.panjohnny.pjgl {
    requires java.desktop;
    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires com.google.gson;
    requires org.lwjgl.stb;
    requires jdk.httpserver;
    requires org.java_websocket;

    exports com.panjohnny.pjgl.adapt;
    exports com.panjohnny.pjgl.adapt.desktop;
    exports com.panjohnny.pjgl.adapt.lwjgl;

    exports com.panjohnny.pjgl.api.asset.atlas;
    exports com.panjohnny.pjgl.api.asset;
    exports com.panjohnny.pjgl.api.camera;
    exports com.panjohnny.pjgl.api.event;
    exports com.panjohnny.pjgl.api.object.components;
    exports com.panjohnny.pjgl.api.object;
    exports com.panjohnny.pjgl.api.util;
    exports com.panjohnny.pjgl.api;

    exports com.panjohnny.pjgl.core;
    exports com.panjohnny.pjgl.core.adapters;
}