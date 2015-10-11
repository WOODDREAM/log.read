package jettylog.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User:huangtao
 * Date:2015-09-17
 * description：
 */
@Component
public class HttpUtil {
    //默认编码方式
    private static final String DEFAULT_ENCODE = "utf-8";

    static {
    }

    public HttpUtil() {

    }

    public static String getUrlAsString(String url) throws Exception {
        return getUrlAsString(url, null, DEFAULT_ENCODE);
    }

    public static String getUrlAsString(String url, Map<String, String> params) throws Exception {
        return getUrlAsString(url, null, DEFAULT_ENCODE);
    }

    public static String getUrlAsString(String url, Map<String, String> params, String encode) throws Exception {
        long startTime = System.currentTimeMillis();
        HttpGet httpGet = getHttpGet(url, params, encode);
        String result = executeHttpRequest(httpGet, null);
        long endTime = System.currentTimeMillis();
        return result;
    }

    public static String postUrlAsString(String url) throws Exception {
        return postUrlAsString(url, null, null, null);
    }

    public static String postUrlAsString(String url, Map<String, String> params)
            throws Exception {
        return postUrlAsString(url, params, null, null);
    }

    public static String postUrlAsString(String url, Map<String, String> params, Map<String, String> reqHeader)
            throws Exception {
        return postUrlAsString(url, params, reqHeader, null);
    }

    /**
     * post请求，以字符串形式返回
     *
     * @param url
     * @param params
     * @param reqHeader
     * @param encode
     * @return
     * @throws Exception
     */
    public static String postUrlAsString(String url, Map<String, String> params,
                                         Map<String, String> reqHeader, String encode) throws Exception {
        long startTime = System.currentTimeMillis();
        HttpPost httpPost = getHttpPost(url, params, encode);
        String result = executeHttpRequest(httpPost, reqHeader);
        long endTime = System.currentTimeMillis();
        return result;
    }

    /**
     * 获取httpGet对象
     *
     * @param url
     * @param params
     * @param encode
     * @return
     */
    private static HttpGet getHttpGet(String url, Map<String, String> params, String encode) throws Exception {
        StringBuffer paramsBuffer = new StringBuffer(url);
        if (null != params) {
            //拼接地址
            String flag = (url.indexOf('?') == -1) ? "?" : "&";
            //添加参数
            for (String name : params.keySet()) {
                paramsBuffer.append(flag);
                paramsBuffer.append(name);
                paramsBuffer.append("=");
                try {
                    String param = params.get(name);
                    if (null == param) {
                        param = "";
                    }
                    paramsBuffer.append(URLEncoder.encode(param, encode));
                } catch (UnsupportedEncodingException e) {

                    throw new Exception(e);
                }
                flag = "&";
            }
        }
        HttpGet httpGet = new HttpGet(paramsBuffer.toString());
        return httpGet;
    }

    /**
     * 获取httpPost
     *
     * @param url
     * @param params
     * @param encode
     * @return
     */
    private static HttpPost getHttpPost(String url, Map<String, String> params, String encode) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (null != params) {
            List<BasicNameValuePair> form = new ArrayList<BasicNameValuePair>();
            for (String name : params.keySet()) {
                form.add(new BasicNameValuePair(name, params.get(name)));
            }
            try {
                UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(form, encode);
                httpPost.setEntity(encodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                throw new Exception(e);
            }
        }
        return httpPost;
    }

    private static String executeHttpRequest(HttpUriRequest request, Map<String, String> reqHeader) throws Exception {
        HttpClient client = null;
        String result = null;
        try {
            //创建httpClient
            client = new DefaultHttpClient();
            //设置Socket超时时间
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3600);
            //设置去请求头
            if (null != reqHeader) {
                for (String name : reqHeader.keySet()) {
                    request.addHeader(name, reqHeader.get(name));
                }
            }
            //获得返回结果
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                StringBuffer errorBuffer = new StringBuffer();

            }
        } catch (Exception e) {
            //异常处理
            throw new Exception(e);
        } finally {
            try {
                client.getConnectionManager().shutdown();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return result;
    }

    public static void downloadFile(String path, String url) throws Exception {
        HttpClient client = null;
        try {
            if(null == url || url.isEmpty() || null == path || path.isEmpty()){
                throw new Exception("参数为空");
            }
            //创建httpClient对象
            client = new DefaultHttpClient();
            HttpGet httpGet = getHttpGet(url, null, null);
            HttpResponse response = client.execute(httpGet);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bufferWrite = null;
                try {
                    File file = new File(path);
                    //创建文件路径
                    if (file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    //写入文件
                    bufferWrite = new BufferedOutputStream(new FileOutputStream(path));
                    bufferWrite.write(result);
                } catch (Exception e) {
                    throw new Exception(e);
                } finally {
                    try {
                        if (null != bufferWrite) {
                            bufferWrite.close();
                        }
                    } catch (Exception e) {
                        throw new Exception(e);

                    }
                }
            } else {
                StringBuffer errorBufer = new StringBuffer();
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                client.getConnectionManager().shutdown();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }
}
