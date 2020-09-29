///*
// * Copyright(C) 2013 Agree Corporation. All rights reserved.
// *
// * Contributors:
// *     Agree Corporation - initial API and implementation
// */
//package eureka;
//
//import com.netflix.appinfo.EurekaInstanceConfig;
//import com.netflix.appinfo.MyDataCenterInstanceConfig;
//
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// *
// * 注册信息（instance）配置类
// *
// * @author zhiwei
// * @date 2019年6月19日 下午3:11:40
// * @version 1.0
// *
// */
//public class CustomEurekaInstanceConfig extends MyDataCenterInstanceConfig
//        implements EurekaInstanceConfig {
//
//    /**
//     * 服务名
//     */
//    private String applicationName;
//    /**
//     * 注册信息id
//     */
//    private String instanceId;
//    /**
//     * 注册信息中ip地址
//     */
//    private String ipAddress;
//    /**
//     * 注册信息中port端口
//     */
//    private int port = -1;
//    /**
//     * 用于存放额外的注册参数，例如type,port
//     */
//    private final  Map<String, String> metadataMap = new ConcurrentHashMap<>();
//
//    @Override
//    public String getInstanceId() {
//        if (StringUtils.isNullOrEmpty(instanceId)) {
//            return super.getInstanceId();
//        }
//        return instanceId;
//    }
//
//    @Override
//    public String getIpAddress() {
//        if (StringUtils.isNullOrEmpty(ipAddress)) {
//            return super.getIpAddress();
//        }
//        return ipAddress;
//    }
//
//    @Override
//    public int getNonSecurePort() {
//        if (port == -1) {
//            return super.getNonSecurePort();
//        }
//        return port;
//    }
//
//    @Override
//    public String getAppname() {
//        if (StringUtils.isNullOrEmpty(applicationName)) {
//            return super.getAppname();
//        }
//        return applicationName;
//    }
//
//    @Override
//    public Map<String, String> getMetadataMap() {
//        Map<String, String> metadataMap = super.getMetadataMap();
//        for(Entry<String,String>entry : this.metadataMap.entrySet() ){
//            metadataMap.put(entry.getKey(), entry.getValue());
//        }
//        return metadataMap;
//    }
//
//    public void setInstanceId(String instanceId) {
//        this.instanceId = instanceId;
//    }
//
//    public void setIpAddress(String ipAddress) {
//        this.ipAddress = ipAddress;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public void setApplicationName(String applicationName) {
//        this.applicationName = applicationName;
//    }
//
//    public void setMetadataMap(Map<String, String> metadataMap) {
//        for(Entry<String,String>entry : metadataMap.entrySet()){
//            this.metadataMap.put(entry.getKey(), entry.getValue());
//        }
//    }
//
//}
