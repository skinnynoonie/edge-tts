package me.skinnynoonie.edgetts;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

final class EdgeTtsWebSocket extends WebSocketClient {
    private final TtsSettings ttsSettings;
    private final OutputStream outputStream;
    private final List<Consumer<Boolean>> onFinishListeners;

    EdgeTtsWebSocket(URI webSocketUri, TtsSettings ttsSettings, OutputStream outputStream) {
        super(webSocketUri);
        this.ttsSettings = ttsSettings;
        this.outputStream = outputStream;
        this.onFinishListeners = new ArrayList<>();
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        super.send(this.createPrepareWebHookData());
        super.send(this.createTtsRequestData());
    }

    private String createPrepareWebHookData() {
        return """
                Content-Type:application/json; charset=utf-8\r
                Path:speech.config\r
                \r
                {
                    "context": {
                        "synthesis": {
                            "audio": {
                                "metadataoptions": {
                                    "sentenceBoundaryEnabled": false,
                                    "wordBoundaryEnabled": true
                                },
                                "outputFormat": "{output-format}"
                            }
                        }
                    }
                }
                """.replace("{output-format}", this.ttsSettings.getFormat());
    }

    private String createTtsRequestData() {
        return """
                X-RequestId: {id}\r
                Content-Type:application/ssml+xml\r
                Path:ssml\r
                \r
                <speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xml:lang='en-US'>
                    <voice name='{voice-name}'>
                        <prosody pitch='{pitch}' rate='{rate}' volume='{volume}'>
                            {text}
                        </prosody>
                    </voice>
                </speak>
                """
                .replace("{id}", UUID.randomUUID().toString().replace("-", ""))
                .replace("{voice-name}", this.ttsSettings.getVoice().getName())
                .replace("{pitch}", this.ttsSettings.getProsodySettings().getPitchFormatted())
                .replace("{rate}", this.ttsSettings.getProsodySettings().getRateFormatted())
                .replace("{volume}", this.ttsSettings.getProsodySettings().getVolumeFormatted())
                .replace("{text}", this.ttsSettings.getText());
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        byte[] byteArray = bytes.array();
        int byteDataStart = this.findDataStartIndex(byteArray);
        if (byteDataStart != -1) {
            try {
                this.outputStream.write(byteArray, byteDataStart, byteArray.length - byteDataStart);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final byte[] AUDIO_DATA_START_INDICATOR = "Path:audio\r\n".getBytes();

    private int findDataStartIndex(byte[] bytes) {
        boolean match = false;
        for (int i = 0; i < bytes.length - AUDIO_DATA_START_INDICATOR.length; i++) {
            for (int lookAheadIndex = 0; lookAheadIndex < AUDIO_DATA_START_INDICATOR.length; lookAheadIndex++) {
                if (AUDIO_DATA_START_INDICATOR[lookAheadIndex] != bytes[i + lookAheadIndex]) {
                    match = false;
                    break;
                }

                match = true;
            }

            if (match) {
                return i + AUDIO_DATA_START_INDICATOR.length;
            }
        }

        return -1;
    }

    @Override
    public void onMessage(String message) {
        if (message.contains("Path:turn.end")) {
            super.close();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.onFinishListeners.forEach(listener -> listener.accept(code == CloseFrame.NORMAL));
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    void registerFinishListener(Consumer<Boolean> listener) {
        this.onFinishListeners.add(listener);
    }
}
