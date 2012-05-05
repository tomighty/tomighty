package org.tomighty.plugin.integration;

import com.google.inject.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginPack;
import org.tomighty.plugin.impl.DefaultPluginLoader;
import plugins.childinjector.ChildinjectorPluginMain;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dobermai
 */
public class PluginChildInjectorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final String PLUGIN_JAR_FILENAME = "plugin-test.jar";
    private JavaArchive deployment;
    private File actualPluginFile;

    private class TestModule extends AbstractModule {

        @Override
        protected void configure() {

            bind(PluginLoader.class).to(DefaultPluginLoader.class);
            bind(ChildinjectorPluginMain.class);
        }
    }

    @Before
    public void setUp() throws Exception {
        deployment = createArchive();
    }

    @Test
    public void testWhenBindingModuleDefinedWithBinding() throws Exception {

        File tomightyProperties = new File(this.getClass().getResource("/plugins/childinjector/tomighty-plugin-validmodule.properties").toURI());

        addPropertiesFileToArchive(tomightyProperties);

        Injector injector = Guice.createInjector(new TestModule());

        DefaultPluginLoader defaultPluginLoader = injector.getInstance(DefaultPluginLoader.class);

        Map<Key<?>, Binding<?>> allBindingsBeforePluginLoad = injector.getAllBindings();

        defaultPluginLoader.load(createPluginPack());

        Map<Key<?>, Binding<?>> allBindingsAfterPluginLoad = injector.getAllBindings();

        Assert.assertEquals(allBindingsBeforePluginLoad.size() + 1, allBindingsAfterPluginLoad.size());
    }

    @Test
    public void testWhenNoBindingModuleDefinedUseStandardModuleWithNoBindings() throws Exception {
        File tomightyProperties = new File(this.getClass().getResource("/plugins/childinjector/tomighty-plugin-nomoduledefined.properties").toURI());

        addPropertiesFileToArchive(tomightyProperties);

        Injector injector = Guice.createInjector(new TestModule());

        DefaultPluginLoader defaultPluginLoader = injector.getInstance(DefaultPluginLoader.class);

        Map<Key<?>, Binding<?>> allBindingsBeforePluginLoad = injector.getAllBindings();

        defaultPluginLoader.load(createPluginPack());

        Map<Key<?>, Binding<?>> allBindingsAfterPluginLoad = injector.getAllBindings();

        Assert.assertEquals(allBindingsBeforePluginLoad.size(), allBindingsAfterPluginLoad.size());
    }


    private void addPropertiesFileToArchive(final File tomightyProperties) throws Exception {
        deployment = deployment.add(new FileAsset(tomightyProperties), "tomighty-plugin.properties");

        saveJarFileToDisk(PLUGIN_JAR_FILENAME);
    }

    private JavaArchive createArchive() throws Exception {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, PLUGIN_JAR_FILENAME)
                .addClass(ChildinjectorPluginMain.class);
        return jar;
    }

    private PluginPack createPluginPack() throws MalformedURLException {
        PluginPack pluginPack = mock(PluginPack.class);
        when(pluginPack.jars()).thenReturn(new URL[]{actualPluginFile.toURI().toURL()});
        return pluginPack;
    }

    private void saveJarFileToDisk(String fileName) throws Exception {
        actualPluginFile = temporaryFolder.newFile(fileName);
        deployment.as(ZipExporter.class).exportTo(actualPluginFile, true);
    }
}
