package coms.appsdeveloperblog.app.ws.shared;

import java.util.Random;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	private final Random RANDOM = new SecureRandom();
	private final String ALPHABETS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public String GenerateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {

			returnValue.append(ALPHABETS.charAt(RANDOM.nextInt(ALPHABETS.length())));
		}

		return new String(returnValue);
	}

}
