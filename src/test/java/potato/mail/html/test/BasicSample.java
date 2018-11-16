package potato.mail.html.test;

import org.apache.commons.io.IOUtils;
import potato.mail.html.ClassPathCssLoader;
import potato.mail.html.HtmlRenderTool;

import java.net.URL;

/**
 * @author zh_zhou
 * created at 2018/11/15 16:13
 * Copyright [2018] [zh_zhou]
 */
public class BasicSample {

    public static void main(String[] args) throws Exception {
        URL htmlUrl = BasicSample.class.getResource("html-template/1.html");
        URL baseUrl = BasicSample.class.getResource("html-template");
        String html= IOUtils.toString(htmlUrl,"utf-8");
        String page=HtmlRenderTool.renderHtmlPage(html,new ClassPathCssLoader(baseUrl));
        System.out.println(page);
    }




}
