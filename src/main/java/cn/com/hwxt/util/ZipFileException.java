package cn.com.hwxt.util;

import java.io.IOException;

/**
 * @author  wwwly
 */
public class ZipFileException extends IOException {
	public ZipFileException() {
		super();
	}

	public ZipFileException(String message) {
		super(message);
	}

	public ZipFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZipFileException(Throwable cause) {
		super(cause);
	}
}
