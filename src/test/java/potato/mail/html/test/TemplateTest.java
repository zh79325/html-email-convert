package potato.mail.html.test;

import com.oracle.tools.packager.IOUtils;
import potato.mail.html.HtmlRenderTool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author zh_zhou
 * created at 2018/11/15 16:13
 * Copyright [2018] [zh_zhou]
 */
public class TemplateTest {

    public static void main(String[] args) throws Exception {
        URL url = TemplateTest.class.getResource("html-template/1.html");

        String page=HtmlRenderTool.renderHtmlPage(url);
        System.out.println(page);
    }


}
