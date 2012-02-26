/*
 * Copyright (c) 2010-2011 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;

public abstract class InjectedTest {

	@Before
	public void createContainer() {
        Injector injector = Guice.createInjector(new TestModule());
        injector.injectMembers(this);
	}

	protected void bind(Binder binder) {
	}

    private class TestModule extends AbstractModule {

        @Override
        protected void configure() {
            InjectedTest.this.bind(binder());
        }

    }

}
