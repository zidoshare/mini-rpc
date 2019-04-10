package site.zido.rpc.common.extension;

import org.junit.Assert;
import org.junit.Test;
import site.zido.rpc.common.extension.demo.ext1.SimpleExt;
import site.zido.rpc.core.extensions.ExtensionLoader;

public class ExtensionLoaderTest {
    @Test
    public void testGetExtensionLoaderNull(){
        try {
            ExtensionLoader.getExtensionLoader(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(e.getMessage().contains("Extension type == null"));
        }
    }

    @Test
    public void testGetExtensionLoaderNotInterface(){
        try{
            ExtensionLoader.getExtensionLoader(ExtensionLoaderTest.class);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(e.getMessage().contains("Extension type (class site.zido.rpc.common.extension.ExtensionLoaderTest)" +
                    " is not an interface!"));
        }
    }

    @Test
    public void testGetExtensionLoaderNotSpiAnnotation(){
        try{
            ExtensionLoader.getExtensionLoader(NoSpiExt.class);
        }catch (IllegalArgumentException e){
            System.out.println(NoSpiExt.class.getTypeName());
            Assert.assertTrue(e.getMessage().contains(NoSpiExt.class.getTypeName()));
            Assert.assertTrue(e.getMessage().contains("is not an extension"));
            Assert.assertTrue(e.getMessage().contains("NOT annotated with @SPI"));
        }
    }

    @Test
    public void testGetDefaultExtension(){
//        SimpleExt ext = ExtensionLoader.getExtensionLoader(SimpleExt.class);
    }
}
