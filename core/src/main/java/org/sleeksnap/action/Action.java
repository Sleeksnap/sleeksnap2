package org.sleeksnap.action;

import java.util.function.Consumer;

/**
 * An Action to be executed.
 *
 * @author Nikki
 */
public interface Action<T> {

	/**
	 * Execute the specified action.
	 *
	 * @param consumer The consumer.
	 * @throws Exception If an error occurred during execution.
	 */
	public void execute(Consumer<T> consumer) throws Exception;
}
