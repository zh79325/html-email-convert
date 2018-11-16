package potato.mail.html;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * @author zh_zhou
 * created at 2018/11/16 14:18
 * Copyright [2018] [zh_zhou]
 */
public class HttpCssLoader implements  CssLoader {
    @Override
    public String loadCss(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }
}
