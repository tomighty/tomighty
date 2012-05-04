package org.tomighty.plugin.integration;

import com.google.inject.*;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginPack;
import org.tomighty.plugin.impl.DefaultPluginLoader;
import plugins.childinjector.ChildinjectorPluginMain;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dobermai
 */
public class PluginChildInjectorTest {

    private static final String PLUGIN_JAR_FILENAME = "plugin-test.jar";
    private JavaArchive deployment;
    private File actualPluginFile;

    private class TestModule extends AbstractModule {

        @Override
        protected void configure() {
            try {
                deployment = createArchive();
                saveJarFileToDisk(PLUGIN_JAR_FILENAME);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            bind(PluginLoader.class).to(DefaultPluginLoader.class);
            bind(ChildinjectorPluginMain.class);
        }

    }

    @Test
    public void testWhenNoBindingModuleDefinedUseStandardModuleWithNoBindings() throws Exception {

        Injector injector = Guice.createInjector(new TestModule());

        DefaultPluginLoader defaultPluginLoader = injector.getInstance(DefaultPluginLoader.class);

        Map<Key<?>, Binding<?>> allBindingsBeforePluginLoad = injector.getAllBindings();

        defaultPluginLoader.load(createPluginPack());

        Map<Key<?>, Binding<?>> allBindingsAfterPluginLoad = injector.getAllBindings();

        Assert.assertEquals(allBindingsBeforePluginLoad.size(), allBindingsAfterPluginLoad.size());
    }

    private JavaArchive createArchive() throws URISyntaxException {

        File file = new File(this.getClass().getResource("/plugins/childinjector/tomighty-plugin.properties").toURI());
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "plugin-test.jar")
                .addClass(ChildinjectorPluginMain.class).add(new FileAsset(file), "tomighty-plugin.properties");
        System.out.println(jar.toString(true));
        return jar;
    }

    private PluginPack createPluginPack() throws MalformedURLException {
        PluginPack pluginPack = mock(PluginPack.class);
        when(pluginPack.jars()).thenReturn(new URL[]{actualPluginFile.toURI().toURL()});
        return pluginPack;
    }

    private void saveJarFileToDisk(String fileName) {
        actualPluginFile = new File(computeTestDataRoot(getClass()), fileName);
        deployment.as(ZipExporter.class).exportTo(actualPluginFile, true);
    }

    public static File computeTestDataRoot(Class anyTestClass) {
        final String clsUri = anyTestClass.getName().replace('.', '/') + ".class";
        final URL url = anyTestClass.getClassLoader().getResource(clsUri);
        final String clsPath = url.getPath();
        final File root = new File(clsPath.substring(0, clsPath.length() - clsUri.length()));
        final File clsFile = new File(root, clsUri);
        return new File(root.getParentFile(), "test-classes");
    }
}
