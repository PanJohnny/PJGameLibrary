package com.panjohnny.pjgl.api.asset;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.util.FileUtil;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Create animations!
 *
 * @see Animation#loadFromJson(String, BiConsumer)
 * @see AnimationBuilder
 *
 * @author PanJohnny
 */
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
        if (System.nanoTime() - lastTime >= last.duration * 1_000_000L ) {
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

            Sprite<?> sprite = new Sprite<>(frame.sprite().getID() + "_flipped", image);
            frames1.add(new Frame(sprite, frame.duration()));

            SpriteRegistry.registerSprite(sprite);
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
     * Method for loading animation from JSON file
     *
     * <h2>JSON Format:</h2>
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
     * <h3>Exceptions:</h3>
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
     *
     * <b>Currently does not support atlas textures</b>
     *
     * @see TimingMode
     * @param file Path to JSON file
     * @param spriteRegistryMethod Consumer accepting (id, file) parameters for example: {@link SpriteRegistry#registerTextureSprite(String, String)} or {@link SpriteRegistry#registerImageSprite(String, String)}
     */
    public static Animation loadFromJson(String file, BiConsumer<String, String> spriteRegistryMethod) throws AnimationBuilder.AnimationBuilderException, FileNotFoundException {
        JsonObject json = JsonParser.parseReader(new InputStreamReader(FileUtil.resolveFileOrResource(file))).getAsJsonObject();

        // Run checks.
        check(json.has("id"), "mandatory argument not provided");
        check(json.has("frames"), "mandatory argument not provided");
        check(json.get("frames").getAsJsonArray().size() > 0, "there must be minimally one frame of animation");
        check(json.has("order"), "mandatory argument not provided");
        check(json.get("order").getAsJsonArray().size() > 0);

        String folder = new File(file).getParent() + "/"; // folder needs to end with / when merging strings later on
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

        List<Sprite<?>> images = new ArrayList<>();

        for (int i = 0; i < frames.length; i++) {
            String spriteId = id + "_frame_" + i;
            spriteRegistryMethod.accept(spriteId, folder + frames[i]);
            images.add(SpriteRegistry.getSprite(spriteId));
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
