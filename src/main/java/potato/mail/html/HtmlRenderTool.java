package potato.mail.html;

import cz.vutbr.web.css.*;
import cz.vutbr.web.domassign.Analyzer;
import cz.vutbr.web.domassign.StyleMap;
import org.apache.commons.io.IOUtils;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * @author zh_zhou
 * created at 2018/11/15 11:46
 * Copyright [2018] [zh_zhou]
 */
public class HtmlRenderTool {

    static Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
    static MediaSpec media = new MediaSpec("screen");

    static {
        media.setDimensions(1366, 768);
    }

    static HtmlCleaner cleaner = new HtmlCleaner();

    /**
     * Convert CSS Html Page to inline style
     *
     * @param html     html source page
     * @param fileBase html page parent url
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws CSSException
     */
    public static String renderHtmlPage(String html, URL fileBase) throws IOException, ParserConfigurationException, CSSException {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        long level = 0;
        int index = 0;
        Map<String, Element> elementMap = new HashMap<String, Element>();
        String parentKey = "$r";
        for (Element element : doc.children()) {
            buildKey(parentKey, level, index, element, elementMap);
            index++;
        }
        String tagdHtml = doc.html();

        TagNode node = cleaner.clean(tagdHtml);
        DomSerializer ser = new DomSerializer(cleaner.getProperties());
        Document myW3cDoc = ser.createDOM(node);

        Elements els = doc.select("link,style");
        StringBuffer cssBuffer = new StringBuffer();
        for (Element el : els) {
            String tag = el.tagName();
            String rawStyleRules = null;
            if ("link".equalsIgnoreCase(tag)) {
                rawStyleRules = loadCssFromLink(fileBase, el);
            } else {
                rawStyleRules = el.getAllElements().get(0).data();
            }
            if (rawStyleRules != null && rawStyleRules.trim().length() > 0) {
                String styleRules = rawStyleRules
                        .replaceAll("\n", "").trim()
                        .replaceAll("/\\*.+?\\*/", "");
                cssBuffer.append(styleRules);
            }
            el.remove();
        }
        String css = cssBuffer.toString();
        String cssHash = HashUtil.MD5(css);
        Analyzer analyzer = analyzerMap.get(cssHash);
        if (analyzer == null) {
            StyleSheet styleSheet = CSSFactory.parseString(css, null);
            analyzer = new Analyzer(styleSheet);
            analyzerMap.put(cssHash, analyzer);
        }
        StyleMap styleMap = analyzer.evaluateDOM(myW3cDoc, media, true);
        for (org.w3c.dom.Element element : styleMap.keySet()) {
            String key = element.getAttribute("mcd-custom-key");
            Element target = elementMap.get(key);
            if (target == null) {
                continue;
            }
            NodeData style = styleMap.get(element);
            CSSProperty.Width mm = style.getProperty("width");
            String styleStr = style.toString();
            target.attr("style", styleStr);
        }

        for (Element element : elementMap.values()) {
            element.removeAttr("mcd-custom-key");
        }
        String result = doc.toString();
        return result;
    }

    private static String loadCssFromLink(URL fileBase, Element el) throws MalformedURLException {
        String rawStyleRules;
        String href = el.attr("href");
        URL cssUrl = null;
        if (href.startsWith("http")) {
            cssUrl = new URL(href);
        } else {
            if (fileBase != null) {
                cssUrl = new URL(fileBase, href);
            }
        }
        rawStyleRules = readStringFromURL(cssUrl);
        return rawStyleRules;
    }

    private static void buildKey(String parentKey, long level, int index, Element element, Map<String, Element> elementMap) {
        String key = String.format("%s_%d.%d", parentKey, level, index);
        element.attr("mcd-custom-key", key);
        elementMap.put(key, element);
        int n = 0;
        for (Element child : element.children()) {
            String attr = child.attr("mcd-custom-key");
            if (attr == null || attr.trim().equals("")) {
                buildKey(key, level + 1, n, child, elementMap);
            } else {
                System.out.println("finish");
            }
            n++;
        }
    }


    public static String readStringFromURL(URL requestURL) {
        String content = null;
        try {
            content = IOUtils.toString(requestURL, "utf-8");
        } catch (IOException e) {
            return content;
        }
        return content;
    }

    public static String renderHtmlPage(URL url) throws Exception {
        String html = readStringFromURL(url);
        URI uri = url.toURI();
        URI parent = uri.getPath().endsWith("/") ? uri.resolve("..") : uri.resolve(".");
        URL pUrl = new URL(parent.toString());
        return renderHtmlPage(html, pUrl);
    }
}
