package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.util.MathUtil;

import java.awt.*;
import java.util.List;

/**
 * Simple 2d position implementation with integer x and y.
 *
 * @implNote Please note that I don't advise using getDirection method.
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class Position extends Component {
    public int x, y;

    public Position(GameObject owner) {
        super(owner);
    }

    public Position(GameObject owner, int x, int y) {
        super(owner);
        this.x = x;
        this.y = y;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void update(long deltaTime) {

    }

    public Direction directionTo(GameObject object) {
        Position pos = object.getComponent(Position.class);
        Size size = object.getComponent(Size.class);

        if (pos == null)
            return Direction.NONE;

        if (size != null) {
            if (MathUtil.between(x, pos.x, pos.x + size.width)) {
                // is on the top or bottom
                if (y < pos.y + size.height / 2) {
                    return Direction.DOWN;
                } else if (y > pos.y + size.height / 2) {
                    return Direction.UP;
                }
            } else if (MathUtil.between(y, pos.y - 2, pos.y + size.height - 2)) {
                // is on the left or right
                if (x < pos.x + size.width) {
                    return Direction.LEFT;
                } else if (x > pos.x + size.width) {
                    return Direction.RIGHT;
                }
            }
        }

//        return Direction.fromTo(this, pos);
        return Direction.NONE;
    }

    public int distanceX(GameObject object) {
        Position pos = object.getComponent(Position.class);

        if (pos == null) {
            return -1;
        }

        return Math.abs(this.x - pos.x);
    }

    public int distanceY(GameObject object) {
        Position pos = object.getComponent(Position.class);

        if (pos == null) {
            return -1;
        }

        return Math.abs(this.y - pos.y);
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public enum Direction {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0),
        UP_RIGHT(1, -1),
        UP_LEFT(-1, -1),
        DOWN_LEFT(-1, 1),
        DOWN_RIGHT(1, 1),
        NONE(0, 0);

        public static final List<Direction> DOWN_ALL = List.of(
                DOWN,
                DOWN_LEFT,
                DOWN_RIGHT
        );
        public static final List<Direction> UP_ALL = List.of(
                UP,
                UP_LEFT,
                UP_RIGHT
        );
        public static final List<Direction> RIGHT_ALL = List.of(
                RIGHT,
                UP_RIGHT,
                DOWN_RIGHT
        );
        public static final List<Direction> LEFT_ALL = List.of(
                LEFT,
                UP_LEFT,
                DOWN_LEFT
        );
        public final int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Direction fromTo(Position from, Position to) {
            Direction dx = Direction.NONE;
            Direction dy = Direction.NONE;

            if (from.x < to.x) {
                dx = RIGHT;
            } else if (from.x > to.x) {
                dx = LEFT;
            }

            if (from.y < to.y) {
                dy = DOWN;
            } else if (from.y > to.y) {
                dy = UP;
            }

            return dy.combine(dx);
        }

        public static Direction fromTo(Position from, Point to) {
            Direction dx = Direction.NONE;
            Direction dy = Direction.NONE;

            if (from.x < to.x) {
                dx = RIGHT;
            } else if (from.x > to.x) {
                dx = LEFT;
            }

            if (from.y < to.y) {
                dy = DOWN;
            } else if (from.y > to.y) {
                dy = UP;
            }

            return dy.combine(dx);
        }

        public Direction combine(Direction direction) {
            if ((this == UP || this == DOWN) && (direction == RIGHT || direction == LEFT)) {
                return Direction.valueOf(this.name() + "_" + direction.name());
            } else if ((direction == UP || direction == DOWN) && (this == RIGHT || this == LEFT)) {
                return Direction.valueOf(direction.name() + "_" + this.name());
            } else if (direction == NONE && this != NONE) {
                return this;
            } else if (this == NONE) {
                return direction;
            }
            return null;
        }
    }
}
