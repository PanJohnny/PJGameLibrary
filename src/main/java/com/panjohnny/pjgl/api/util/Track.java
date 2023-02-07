package com.panjohnny.pjgl.api.util;

import com.panjohnny.pjgl.api.PJGL;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Class for playing music
 */
@SuppressWarnings("unused")
public class Track {
    private final String name, id, file;
    private Clip clip;

    /**
     * Creates track object.
     */
    public Track(String file, String name, String id) {
        this.name = name;
        this.id = id;
        this.file = file;
    }

    public void load() throws TrackException {
        try {
            if (clip == null)
                clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(PJGL.class.getResourceAsStream(file))));
            clip.addLineListener(myLineEvent -> {
                if (myLineEvent.getType() == LineEvent.Type.STOP)
                    clip.close();
            });
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to load/open clip for track {0} {1}", getId(), toString());
            throw new TrackException(e);
        }
    }

    /**
     * @throws TrackException If clip needs to be loaded and fails to do so.
     * @see #load()
     * @implNote load() method will be called automatically if needed.
     */
    public void play() throws TrackException {
        if (clip == null || !clip.isOpen())
            load();

        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    /**
     * Makes the track loop continuously
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setPlaybackSpeed(float speed) {
        clip.setMicrosecondPosition((long) (clip.getMicrosecondLength() * speed));
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (!MathUtil.between(volume, 0f, 1f))
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * Sets the frame position to 0 effectively rewinding the track to the start.
     */
    public void reset() {
        clip.setFramePosition(0);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Clip getClip() {
        return clip;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return String.format("Track(name=%s, id=%s, file=%s)", getName(), getId(), getFile());
    }

    public static class TrackException extends Exception {
        public TrackException(Throwable reason) {
            super(reason);
        }
    }
}
