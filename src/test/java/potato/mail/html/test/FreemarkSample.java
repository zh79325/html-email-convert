package potato.mail.html.test;

import cz.vutbr.web.css.CSSException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import potato.mail.html.HtmlRenderTool;
import potato.mail.html.HttpCssLoader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.*;

/**
 * @author zh_zhou
 * created at 2018/11/16 12:49
 * Copyright [2018] [zh_zhou]
 */
public class FreemarkSample {

    public static void main(String[] args) throws Exception {
        Map<String,Object> data=new HashMap<>();
        data.put("title","Title-"+new Date().toString());
        List<Map<String,String>> items=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String,String> item=new HashMap<>();
            item.put("id",i+"");
            item.put("name","name_"+i);
            item.put("tag","tag_"+i);
            items.add(item);
        }
        data.put("items",items);
        Writer writer = new StringWriter();
        Configuration conf = new Configuration(Configuration.VERSION_2_3_23);
        conf.setDefaultEncoding("UTF-8");
        conf.setClassForTemplateLoading(FreemarkSample.class,"freemark");
        Template tpl = conf.getTemplate("freemark.ftl");
        tpl.process(data, writer);
        String content = writer.toString();

        content= HtmlRenderTool.renderHtmlPage(content,new HttpCssLoader());
        System.out.println(content);
    }
}
