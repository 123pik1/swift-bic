package pik.Exceptions;

public class WrongSwiftCodeException extends RuntimeException {
	public WrongSwiftCodeException() {
		super("Invalid SWIFT code provided.");
	}

	public WrongSwiftCodeException(String message) {
		super(message);
	}
}
