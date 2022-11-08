package com.panjohnny.pjgl.api.utils;

import java.util.Objects;

public record Identifier(String namespace, String name) {
    @Override
    public String toString() {
        return namespace + ":" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        if (!Objects.equals(namespace, that.namespace)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
