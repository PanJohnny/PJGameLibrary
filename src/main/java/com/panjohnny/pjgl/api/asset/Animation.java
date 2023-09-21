package com.panjohnny.pjgl.api.asset;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Animation {
    private final String id;
    private final ArrayList<Frame> frames;

    public Animation(String id, ArrayList<Frame> frames) {
        this.id = id;
        this.frames = frames;
    }

    private long lastTime = 0;
    private int lastFrame = 0;
    public Frame getFrame() {
        Frame last = frames.get(lastFrame);
        if (lastTime + last.duration * 1_000_000L <= System.nanoTime()) {
            // Switch frames
            lastFrame++;
            if (lastFrame >= frames.size()) {
                lastFrame = 0;
            }

            lastTime = System.nanoTime();
            return frames.get(lastFrame);
        } else
            return last;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public void reset() {
        lastTime = 0;
        lastFrame = 0;
    }

    public String getId() {
        return id;
    }

    public record Frame(Sprite<?> sprite, int duration) {

    }

    /**
     * Creates flipped animation (vertically). Counts with the Sprite T to be BufferedImage. Identifier will be id_flipped.
     */
    public Animation createFlipped() {
        ArrayList<Frame> frames1 = new ArrayList<>(frames.size());

        for (Frame frame : frames) {
            if (!frame.sprite.of(BufferedImage.class)) {
                PJGL.LOGGER.log(System.Logger.Level.WARNING, "Animation#flip is only available when having sprite of T extends BufferedImage.");
                return null;
            }
            BufferedImage image = (BufferedImage) frame.sprite().getImage();

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(image, null);

            frames1.add(new Frame(new Sprite<>(frame.sprite().getID() + "_flipped", image), frame.duration()));
        }

        return new Animation(id + "_flipped", frames1);
    }

    public enum TimingMode {
        SIMPLE,
        INDIVIDUAL;
        public static TimingMode getTimingMode(String name) {
            for (TimingMode mode : values()) {
                if (mode.name().equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            return null;
        }
    }

    /**
     * Class used for constructing animations easily.
     */
    public static class AnimationBuilder {
        private final String id;
        private int frameDuration = -1;
        private TimingMode mode = TimingMode.SIMPLE;
        private int[] durations;
        private int[] order;
        private final ArrayList<Sprite<?>> frames;
        public AnimationBuilder(String id) {
            this.id = id;
            this.frames = new ArrayList<>();
        }

        /**
         * Specifies same timing for all frames.
         */
        public AnimationBuilder simpleTiming(int frameDuration) {
            this.frameDuration = frameDuration;
            this.mode = TimingMode.SIMPLE;
            return this;
        }

        /**
         * Specifies individual timing for every frame in millis.
         * @param durations Must be the same length as order!
         */
        public AnimationBuilder individualTiming(int... durations) {
            this.durations = durations;
            this.mode = TimingMode.INDIVIDUAL;
            return this;
        }

        /**
         * Adds frames.
         */
        public AnimationBuilder frames(Sprite<?>... frames) {
            this.frames.addAll(List.of(frames));
            return this;
        }

        /**
         * Sets order of frames.
         */
        public AnimationBuilder order(int... order) {
            this.order = order;
            return this;
        }

        public Animation build() throws AnimationBuilderException {
            final ArrayList<Frame> animationFrames = new ArrayList<>();

            check(order != null);

            // Check durations.
            if (mode == TimingMode.INDIVIDUAL) {
                check(durations != null);
                if (order.length != durations.length) {
                    PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to create animation: " + id);
                    throw new AnimationBuilderException("Order must be the same length as durations in INDIVIDUAL mode");
                }
            } else
                check(frameDuration != -1);

            for (int i = 0; i < order.length; i++) {
                int elementIndex = order[i];
                if (elementIndex >= frames.size()) {
                    PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to create animation: " + id);
                    throw new AnimationBuilderException("Order references frames, not enough frames detected! %d >= %d".formatted(elementIndex, frames.size()));
                }

                int duration = frameDuration;

                if (mode == TimingMode.INDIVIDUAL)
                    duration = durations[i];

                animationFrames.add(new Frame(frames.get(elementIndex), duration));
            }


            return new Animation(id, animationFrames);
        }

        public static class AnimationBuilderException extends Exception {
            public AnimationBuilderException(String message) {
                super(message);
            }
        }
    }

    /**
     * <h1>JSON Format:</h1>
     * <pre>
     * {
     *   "id": "player_front",
     *   "folder": "/front",
     *   "frames": [
     *     "0.png",
     *     "1.png",
     *     "2.png"
     *   ],
     *   "order": [
     *     0, 1, 0, 2
     *   ],
     *   "timing": {
     *     "mode": "simple",
     *     "frameDuration": 100,
     *     "durations": []
     *   }
     * }
     * </pre>
     *
     * <h2>Exceptions:</h2>
     * <ul>
     *     <li>
     *         folder is optional
     *     </li>
     *     <li>
     *         timing modes are simple/individual
     *     </li>
     *     <li>
     *         when simple only frameDuration is required (durations not, ignored if provided)
     *     </li>
     *     <li>
     *         when individual only durations are required (frameDuration not, ignored if provided)
     *     </li>
     * </ul>
     * @see TimingMode
     */
    @SuppressWarnings("deprecation")
    public static Animation loadFromJson(String file) throws AnimationBuilder.AnimationBuilderException {
        JsonObject json = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(PJGL.class.getResourceAsStream(file)))).getAsJsonObject();

        // Run checks.
        check(json.has("id"), "mandatory argument not provided");
        check(json.has("frames"), "mandatory argument not provided");
        check(json.get("frames").getAsJsonArray().size() > 0, "there must be minimally one frame of animation");
        check(json.has("order"), "mandatory argument not provided");
        check(json.get("order").getAsJsonArray().size() > 0);

        String folder = new File(file).getParent();
        String id = json.get("id").getAsString();

        if (json.has("folder")) {
            folder += json.get("folder").getAsString() + "/";
        }

        folder = folder.replace("\\", "/").replace("//", "/").trim();

        check(json.has("timing"), "timing required");
        JsonObject timing = json.get("timing").getAsJsonObject();

        check(timing.has("mode"), "timing mode required");
        check(timing.has("durations") || timing.has("frameDuration"));
        TimingMode mode = TimingMode.getTimingMode(timing.get("mode").getAsString());
        check(mode != null, "valid mode must be provided");

        final Gson gson = new Gson();

        int[] order = gson.fromJson(json.get("order"), int[].class);

        int frameDuration = -1;
        int[] durations = null;


        if (mode == TimingMode.SIMPLE) {
            check(timing.has("frameDuration"), "timing duration must be provided");
            frameDuration = timing.get("frameDuration").getAsInt();
        } else {
            check(timing.has("durations"), "timing duration must be provided");
            durations = gson.fromJson(timing.get("durations"), int[].class);
            check(order.length == durations.length, "orders and durations must be of the same length");
        }

        String[] frames = gson.fromJson(json.get("frames"), String[].class);

        // Build it!
        AnimationBuilder builder = new AnimationBuilder(id);

        List<Sprite<Image>> images = new ArrayList<>();

        for (int i = 0; i < frames.length; i++) {
            images.add(SpriteUtil.createImageSprite(id + "_frame_" + i, folder + frames[i]));
        }

        builder.frames(images.toArray(Sprite[]::new));
        builder.order(order);

        if (mode == TimingMode.INDIVIDUAL)
            builder.individualTiming(durations);
        else
            builder.simpleTiming(frameDuration);

        return builder.build();
    }

    private static void check(boolean b, String... message) throws AnimationBuilder.AnimationBuilderException {
        if (!b) {
            throw new AnimationBuilder.AnimationBuilderException(message != null && message.length > 0?message[0]:"Failed to pass check when building animation");
        }
    }
}
