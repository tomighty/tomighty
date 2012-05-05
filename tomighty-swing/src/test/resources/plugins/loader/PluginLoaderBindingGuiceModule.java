package plugins.loader;

import com.google.inject.AbstractModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dobermai
 */
public class PluginLoaderBindingGuiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(List.class).to(ArrayList.class);
    }
}
