package com.ninjawhocodes.fileupload.exception;
/**
 * @author NinjaWhoCodes
 *
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
