/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.plugin;

/**
 * A plugin for Tomighty.
 * <p/>
 * <p>There are two things you need to do if you want to create a plugin:</p>
 * <p/>
 * <ol>
 * <li>Implement this interface,</li>
 * <li>
 * Put your plugin's fully-qualified class name in a file named <code>tomighty-plugin.properties</code>
 * at the root of your plugin's jar file.
 * </li>
 * </ol>
 * <p/>
 * <p>Example of a <code>tomighty-plugin.properties</code>:</p>
 * <p/>
 * <p>
 * <code>
 * class=org.tomighty.plugin.helloworld.HelloWorldPlugin
 * </code>
 * </p>
 * <p/>
 * <p>Note that fields annotated with {@link javax.inject.Inject} are injected by the IoC container.</p>
 */
public interface Plugin {

    String getName();

}
