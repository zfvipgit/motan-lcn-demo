import com.lorne.core.framework.utils.encode.MD5Util;

/**
 * <p>类说明</p>
 *
 * @author 张峰 zfvip_it@163.com
 * @createTime: 2017/12/4 11:58
 */
public class MainTest {


    public static void main(String[] args){
        System.out.println(MD5Util.md5(("aaaaaaaaaaaaaaaaaaaaaa").getBytes()));
        System.out.println(MD5Util.md5(("aaaaaaaaaaaaaaaaaaaaaa").getBytes()));
        System.out.println(MD5Util.md5(("aaaaaaaaaaaaaaaaaaaaaa").getBytes()));
        System.out.println(MD5Util.md5(("aaaaaaaaaaaaaaaaaaaaaa").getBytes()));
        System.out.println(MD5Util.md5(("aaaaaaaaaaaaaaaaaaaaaa").getBytes()));
        System.out.println(MD5Util.md5(("aaaaaaaaaaaaaaaaaaaaaa").getBytes()));
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
