package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.api.utils.Identifier;
import com.panjohnny.pjgl.api.utils.AbstractRegistry;

@SuppressWarnings("unused")
public class GameObjectRegistry<T extends GameObject> extends AbstractRegistry<Identifier, GameObjectFactory<T>> {
}
