package jettylog.utils;

import java.io.Serializable;

/**
 * User:huangtao
 * Date:2015-10-12
 * description：
 */
public class JsonResult implements Serializable {
    private String code;
    private String message;
    private Object data;

    public static JsonResult returnSuccess(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode("1");
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult returnSuccessMsg(String msg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode("1");
        jsonResult.setMessage(msg);
        return jsonResult;
    }

    public static JsonResult returnFail(String resultCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(resultCode);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult returnFail(String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode("0");
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
