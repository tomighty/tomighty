package org.tomighty.plugin;

/**
 * A plugin for Tomighty.
 *
 * <p>There are two things you need to do if you want to create a plugin:</p>
 *
 * <ol>
 *     <li>Implement this interface,</li>
 *     <li>
 *         Put your plugin's fully-qualified class name in a file named <code>tomighty-plugin.properties</code>
 *         at the root of your plugin's jar file.
 *     </li>
 * </ol>
 *
 * <p>Example of a <code>tomighty-plugin.properties</code>:</p>
 *
 * <p>
 *     <code>
 *         class=org.tomighty.plugin.helloworld.HelloWorldPlugin
 *     </code>
 * </p>
 *
 * <p>Note that fields annotated with {@link org.tomighty.ioc.Inject} are injected by the IoC container.</p>
 */
public interface Plugin {
}
