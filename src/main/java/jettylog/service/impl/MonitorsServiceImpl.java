package jettylog.service.impl;


import jettylog.Constants;
import jettylog.bean.MonitorsDetail;
import jettylog.dao.IMonitorsDao;
import jettylog.service.IMonitorsService;
import jettylog.utils.CombineString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User:huangtao
 * Date:2015-09-14
 * description：
 */
@Service
public class MonitorsServiceImpl implements IMonitorsService {

    @Autowired
    private CombineString combineString;

    @Autowired
    private IMonitorsDao monitorsDao;


    @Override
    public void createMonitorsTable(String monitorName, String date) throws Exception {
        try {
            //创建表
            monitorsDao.createMonitorsTable(monitorName, date);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void insertMonitors(String string, String monitorName, String date) throws Exception {
        if (string.isEmpty() || monitorName.isEmpty()) {
            throw new Exception("参数为空");
        } else {

            String[] tempString = string.split(" \\{");
            MonitorsDetail monitorsDetail = new MonitorsDetail();
            monitorsDetail.setRunDate(date);
            String errorCode = null, endTime = null, loggerMarker = null, method = null;
            for (String temp : tempString) {
                if (combineString.recognizeStartWithErrorCode(temp.toString().trim())) {
                    String[] headList = temp.split(" ");
                    int length = headList.length;
                    for (int i = 0; i < length; i++) {
                        String str = headList[i].toString().trim();
                        if (!str.isEmpty()) {
                            if (combineString.recognizeStartWithErrorCode(str)) {
//                                monitorsDetail.setErrorCode(str);
                                errorCode = str;
                                continue;
                            }
                            if (combineString.recognizeEndTime(str)) {
//                                monitorsDetail.setEndTime(str.substring(0, 7));
                                endTime = str.substring(0, 7);
                                continue;
                            }
                            if (str.contains(".java")) {
                                if (monitorName.equals(Constants.Task.SYS_MONITOR) || monitorName.equals(Constants.Task.PAYMENT_MONITOR)) {
                                    String markerStr = str.split(":")[0];
//                                    monitorsDetail.setLoggerMarker(markerStr.toString().trim());
                                    loggerMarker = markerStr.toString().trim();
                                    continue;
                                }
                            }
                            if (i == length - 1) {
//                                    monitorsDetail.setMethod(str);
                                method = str;
                                continue;
                            }
                        }
                    }
                }
                if (combineString.recognizeStartWithData(temp.toString().trim())) {
//                        monitorsDetail.setEndTime(temp.toString().trim().substring(11, 19));
                    endTime = temp.toString().trim().substring(11, 19);
                    if (monitorName.equals(Constants.Task.OAUTH_MONITOR)) {
                        String[] headList2 = temp.split(" ");
                        for (String marker : headList2) {
                            if (marker.contains(".java")) {
                                String[] strings = marker.toString().trim().split(":");
                                loggerMarker = strings[0].substring(1, strings[0].length());
//                                    monitorsDetail.setLoggerMarker(markerStr.substring(0, markerStr.length()));
                            }
                        }
                        method = headList2[headList2.length - 1];
                    }
                } else {
                    monitorsDetail = combineSubordinateTable(monitorName, temp.trim());
                }
                monitorsDetail.setRunDate(date);
                monitorsDetail.setMonitorName(monitorName);
                if (null == monitorsDetail.getMethod()) {
                    monitorsDetail.setMethod(method);
                }
                monitorsDetail.setEndTime(endTime);
                monitorsDetail.setLoggerMarker(loggerMarker);
                monitorsDetail.setErrorCode(errorCode);
            }
            monitorsDao.insertMonitors(monitorsDetail);
        }

    }

    /**
     * 组装monitors_detail表实体
     *
     * @param tempString
     * @return
     */

    private MonitorsDetail combineSubordinateTable(String monitorName, String tempString) {
        MonitorsDetail monitorDetails = new MonitorsDetail();
        String[] detailsList = tempString.trim().split(",");
        StringBuffer strBuffer = new StringBuffer();
        String[] childStr;
        for (String detailStr : detailsList) {
            if (detailStr.contains("method")) {
                if (monitorName.endsWith(Constants.Task.STOP_WATCH_MONITOR)) {
                    continue;
                }
                childStr = detailStr.trim().split("\":");
                if (childStr.length > 1) {
                    if (!childStr[1].contains("POST") && !childStr[1].contains("GET")) {
                        monitorDetails.setMethod(childStr[1].substring(1, childStr[1].length() - 1));
                    }
                    continue;
                }
            }
            if (detailStr.contains("pro_time")) {
                childStr = detailStr.trim().split("\":");
                if (childStr.length > 1) {
                    if (childStr[1].contains("}")) {
                        strBuffer.append(childStr[1].substring(0, childStr[1].length() - 1));
                    } else {
                        strBuffer.append(childStr[1].trim());
                    }
                    monitorDetails.setProTime(Integer.parseInt(strBuffer.toString()));
                    strBuffer.delete(0, strBuffer.length());
                    continue;
                }
            }
            if (detailStr.contains("opt_time")) {
                childStr = detailStr.trim().split("\":\"");
                if (childStr.length > 1) {
                    strBuffer.append(childStr[1].trim());
                    monitorDetails.setRunDate(strBuffer.substring(0, 10).toString().trim());
                    monitorDetails.setOptTime(strBuffer.substring(strBuffer.length() - 10, strBuffer.length() - 1).toString().trim());
                    strBuffer.delete(0, strBuffer.length());
                    continue;
                }
            }

            if (detailStr.contains("request_path")) {

                childStr = detailStr.trim().split(":");
                if (childStr.length > 1) {
                    if (childStr[1].contains("/weixin-meal/menus/v2/")) {
                        if (childStr[1].contains("list")) {
                            monitorDetails.setRequestPath("/weixin-meal/menus/v2/{entity_id}/list");
                            continue;
                        } else {
                            monitorDetails.setRequestPath("/weixin-meal/menus/v2/{entity_id}/{id}");
                            continue;
                        }
                    }
                    if (childStr[1].contains("/weixin-meal/call_service/v1/call/")) {
                        monitorDetails.setRequestPath("/weixin-meal/call_service/v1/call/{type}");
                        continue;
                    }
                    if (childStr[1].contains("/weixin-meal/call_service/v2/call/")) {
                        monitorDetails.setRequestPath("/weixin-meal/call_service/v2/call/{type}");
                        continue;
                    }
                    if (childStr[1].contains("/weixin-meal/call_service/v2/pay/")) {
                        monitorDetails.setRequestPath("/weixin-meal/call_service/v2/pay/{type}");
                        continue;
                    }
                    if (childStr[1].contains("/weixin-meal/g/")) {
                        monitorDetails.setRequestPath("/weixin-meal/g/{globalCode}");
                        continue;
                    }
                    if (childStr[1].contains("/weixin-meal/s/")) {
                        String[] s = childStr[1].split("/");
                        if (s.length == 6) {
                            monitorDetails.setRequestPath("/weixin-meal/s/{entityId}/{seatCode}/{signKey}");
                            continue;
                        }
                        if (s.length == 5) {
                            monitorDetails.setRequestPath("/weixin-meal/s/{entityId}/{signKey}");
                            continue;
                        }
                    }
                    if (childStr[1].contains("/weixin-meal/oauth/callback/")) {
                        monitorDetails.setRequestPath("/weixin-meal/oauth/callback/{entity_id}/{seat_code}");
                        continue;
                    }
                    if (childStr[1].contains("/weixin-meal/shop/v1/")) {
                        if (childStr[1].contains("moment")) {
                            monitorDetails.setRequestPath("/weixin-meal/shop/v1/{entity_id}/moment");
                            continue;
                        }
                        if (childStr[1].contains("all_info")) {
                            monitorDetails.setRequestPath("/weixin-meal/shop/v1/{entity_id}/all_info");
                            continue;
                        }
                        if (childStr[1].contains("share")) {
                            monitorDetails.setRequestPath("/weixin-meal/shop/v1/{entity_id}/share");
                            continue;
                        }
                        monitorDetails.setRequestPath("/weixin-meal/shop/v1/{entityId}");
                        continue;
                    }
                    strBuffer.append(childStr[1].trim());
                    strBuffer.delete(0, 1);
                    strBuffer.delete(strBuffer.length() - 1, strBuffer.length());
                    monitorDetails.setRequestPath(strBuffer.toString().trim());
                    strBuffer.delete(0, strBuffer.length());
                    continue;
                }
            }
        }
        return monitorDetails;
    }
}
