通常情况下我们需要向用户发送一张包含HTML页面的邮件，但是很多邮箱客户端都不支持CSS显示导致页面的样式变得混乱，具体原因及分析可参考

### 阮一峰的网络日志 HTML Email 编写指南

  http://www.ruanyifeng.com/blog/2013/06/html_email.html

该工具的目的就是让开发者可以直接在HTML页面中使用CSS来编写基本样式，然后通过该工具转换成邮箱客户端认识的HTML样式

## 简单样例：使用URL地址转换
```
 public static void main(String[] args) throws Exception {
        URL url = TemplateTest.class.getResource("html-template/1.html");

        String page=HtmlRenderTool.renderHtmlPage(url);
        System.out.println(page);
 }
 ```
 
 一般情况下都是使用一些模版引擎先生成好HTML内容，此时可以调用如下方法进行转换
 ```
 
 ```
