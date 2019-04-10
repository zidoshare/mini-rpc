package site.zido.rpc.common.utils;

import org.junit.Assert;
import org.junit.Test;
import site.zido.rpc.utils.StringUtils;

public class StringUtilsTest {
    @Test
    public void testContainersIgnoreCase(){
        Assert.assertTrue(StringUtils.contains("qwfegrtwffeg","wff"));
        Assert.assertFalse(StringUtils.contains("adwefognrgub","oihfer"));
    }
}
