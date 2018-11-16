package potato.mail.html;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

/**
 * @author zh_zhou
 * created at 2018/11/16 13:27
 * Copyright [2018] [zh_zhou]
 */
public class ClassPathCssLoader implements CssLoader {
    final URL baseUrl;

    public ClassPathCssLoader(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String loadCss(String url) throws Exception {
        URL requestURL= new URL(baseUrl,url);
        String content = null;
        try {
            content = IOUtils.toString(requestURL, "utf-8");
        } catch (IOException e) {
            return content;
        }
        return content;
    }



}
