package org.sleeksnap.util;

/**
 * @author Nikki
 */
public class ClassUtils {

	/**
	 * Check if a class has an interface
	 *
	 * @param cl          The class to check
	 * @param interfaceCl The interface to check for
	 * @return True, if the interface was found.
	 */
	public static boolean hasInterface(Class<?> cl, Class<?> interfaceCl) {
		for (Class<?> c : cl.getInterfaces()) {
			if (c == interfaceCl) {
				return true;
			}
		}
		return false;
	}
}
