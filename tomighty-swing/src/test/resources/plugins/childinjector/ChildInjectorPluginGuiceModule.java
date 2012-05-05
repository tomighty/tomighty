package plugins.childinjector;

import com.google.inject.AbstractModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dobermai
 */
public class ChildInjectorPluginGuiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(List.class).to(ArrayList.class);
    }
}
