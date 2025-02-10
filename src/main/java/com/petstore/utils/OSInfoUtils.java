package com.petstore.utils;

public final class OSInfoUtils {

	/**
	 * Harici erişimi önlemek için private constructor
	 */
	private OSInfoUtils() {
	}

	public static String getOSInfo() {

		return System.getProperty("os.name").replace(" ", "_");

	}

}
