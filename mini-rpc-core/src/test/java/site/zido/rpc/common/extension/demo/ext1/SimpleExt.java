package site.zido.rpc.common.extension.demo.ext1;

import site.zido.rpc.core.extensions.Adaptive;
import site.zido.rpc.core.extensions.SPI;

import java.net.URL;

@SPI("impl1")
public interface SimpleExt {
    @Adaptive
    String echo(URL url, String s);

    @Adaptive({"key1","key2"})
    String yell(URL url,String s);

    String bang(URL url, int i);
}
