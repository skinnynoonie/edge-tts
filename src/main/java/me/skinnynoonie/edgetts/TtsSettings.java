package me.skinnynoonie.edgetts;

import org.jetbrains.annotations.NotNull;

/**
 * Settings used to generate speech.
 */
public final class TtsSettings {
    private String text;
    private String format;
    private Voice voice;
    private ProsodySettings prosodySettings;

    /**
     * Instantiates settings used to generate speech.
     *
     * @param text The text that will be converted into speech.
     * @param format The output format.
     * @param voice The voice used in the speech.
     * @param prosodySettings The prosody settings for the voice.
     */
    public TtsSettings(
            @NotNull String text,
            @NotNull String format,
            @NotNull Voice voice,
            @NotNull ProsodySettings prosodySettings
    ) {
        this.text = Checks.notNullArg(text, "text");
        this.format = Checks.notNullArg(format, "format");
        this.voice = Checks.notNullArg(voice, "voice");
        this.prosodySettings = Checks.notNullArg(prosodySettings, "prosodySetting");
    }

    /**
     * Gets the text.
     *
     * @return The text that will be converted into speech.
     */
    public @NotNull String getText() {
        return this.text;
    }

    /**
     * Sets the text.
     *
     * @param text The text that will be converted into speech.
     */
    public void setText(String text) {
        this.text = Checks.notNullArg(text, "text");
    }

    /**
     * Gets the output format.
     *
     * @return The format that the speech will be outputted in.
     */
    public @NotNull String getFormat() {
        return this.format;
    }

    /**
     * Sets the output format.
     *
     * @param format The format that the speech will be outputted in.
     */
    public void setFormat(String format) {
        this.format = Checks.notNullArg(format, "format");
    }

    /**
     * Gets the voice.
     *
     * @return The voice used for the speech.
     */
    public @NotNull Voice getVoice() {
        return this.voice;
    }

    /**
     * Sets the voice.
     *
     * @param voice The voice used for the speech.
     */
    public void setVoice(Voice voice) {
        this.voice = Checks.notNullArg(voice, "voice");
    }

    /**
     * Gets the prosody settings.
     *
     * @return The prosody settings for the voice.
     */
    public @NotNull ProsodySettings getProsodySettings() {
        return this.prosodySettings;
    }

    /**
     * Sets the prosody settings.
     *
     * @param prosodySettings The prosody settings for the voice.
     */
    public void setProsodySettings(ProsodySettings prosodySettings) {
        this.prosodySettings = Checks.notNullArg(prosodySettings, "prosodySettings");
    }
}
