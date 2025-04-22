package com.strato.hidrive.player.error;

public class ChromecastUnsupportedFileException extends Exception {
	public ChromecastUnsupportedFileException() {
        super("Chromecast doesn't support this file");
    }
}
