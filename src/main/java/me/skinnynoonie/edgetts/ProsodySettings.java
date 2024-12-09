package me.skinnynoonie.edgetts;

import org.jetbrains.annotations.NotNull;

/**
 * Settings that involve adjusting the TTS prosody.
 * Everything is represented in percentages.
 */
public final class ProsodySettings {
    /**
     * Creates with default settings of 0% rate, 0% volume, and 0% pitch.
     *
     * @return Default settings.
     */
    public static @NotNull ProsodySettings createDefault() {
        return new ProsodySettings(0, 0, 0);
    }

    private int rate;
    private int volume;
    private int pitch;

    /**
     * Instantiates new prosody settings with the provided arguments.
     *
     * @param rate The rate as a percentage, where 1 is +1%, and -1 is -1%.
     * @param volume The volume as a percentage, where 1 is +1%, and -1 is -1%.
     * @param pitch The pitch as a percentage, where 1 is +1%, and -1 is -1%.
     */
    public ProsodySettings(int rate, int volume, int pitch) {
        this.rate = rate;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Gets the rate.
     *
     * @return The rate, represented as a percentage.
     */
    public int getRate() {
        return this.rate;
    }

    /**
     * Returns the rate formatted like so:<br>
     * If the rate is negative: {@code -{rate}%}<br>
     * If the rate is positive: {@code +{rate}%}<br>
     *
     * @return The rate formatted.
     */
    public @NotNull String getRateFormatted() {
        return (this.rate >= 0 ? "+" : "") + this.rate + "%";
    }

    /**
     * Sets the rate.
     *
     * @param rate The rate as a percentage, supporting negative values.
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    /**
     * Gets the volume.
     *
     * @return The volume, represented as a percentage.
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * Returns the volume formatted like so:<br>
     * If the volume is negative: {@code -{volume}%}<br>
     * If the volume is positive: {@code +{volume}%}<br>
     *
     * @return The volume formatted.
     */
    public @NotNull String getVolumeFormatted() {
        return (this.volume >= 0 ? "+" : "") + this.volume + "%";
    }

    /**
     * Sets the volume.
     *
     * @param volume The volume as a percentage, supporting negative values.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Gets the pitch.
     *
     * @return The pitch, represented as a percentage.
     */
    public int getPitch() {
        return this.pitch;
    }

    /**
     * Returns the pitch formatted like so:<br>
     * If the pitch is negative: {@code -{pitch}%}<br>
     * If the pitch is positive: {@code +{pitch}%}<br>
     *
     * @return The pitch formatted.
     */
    public @NotNull String getPitchFormatted() {
        return (this.pitch >= 0 ? "+" : "") + this.pitch + "%";
    }

    /**
     * Sets the pitch.
     *
     * @param pitch The pitch as a percentage, supporting negative values.
     */
    public void setPitch(int pitch) {
        this.pitch = pitch;
    }
}
