///*
// * Copyright(C) 2013 Agree Corporation. All rights reserved.
// *
// * Contributors:
// *     Agree Corporation - initial API and implementation
// */
//package eureka;
//
//import cn.com.agree.ats.util.IComparator;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author beanlam
// * @version 1.0
// * @date 2020-02-26 19:11
// */
//public class EurekaRegistryConfig implements IComparator<EurekaRegistryConfig> {
//
//    public static final String DEFAULT_SERVERS = "http://127.0.0.1:8761/eureka/";
//    public static final String DEFAULT_PREFIX_APPLICATION_NAME = "ATS-EUREKA";
//    public static final int DEFALUT_REFRESH_INTERVAL = 30;
//
//    private String servers = null;
//    private int refreshInterval;
//    private String prefixApplicationName = null;
//
//    public EurekaRegistryConfig() {
//        this.servers = DEFAULT_SERVERS;
//        this.refreshInterval = DEFALUT_REFRESH_INTERVAL;
//        this.prefixApplicationName = DEFAULT_PREFIX_APPLICATION_NAME;
//    }
//
//    public String getServers() {
//        return servers;
//    }
//
//    public void setServers(String servers) {
//        this.servers = servers;
//    }
//
//    public int getRefreshInterval() {
//        return refreshInterval;
//    }
//
//    public void setRefreshInterval(int refreshInterval) {
//        this.refreshInterval = refreshInterval;
//    }
//
//    public String getPrefixApplicationName() {
//        return prefixApplicationName;
//    }
//
//    public void setPrefixApplicationName(String prefixApplicationName) {
//        this.prefixApplicationName = prefixApplicationName;
//    }
//
//    @Override
//    public Set<String> compare(EurekaRegistryConfig other) {
//        Set<String> changedKeys = new HashSet<>();
//        if ( !other.getServers().equals(this.servers) ){
//            changedKeys.add("servers");
//        }
//        if ( other.getRefreshInterval() != this.refreshInterval ) {
//            changedKeys.add("refreshInterval");
//        }
//        if ( !other.getPrefixApplicationName().equals(this.getPrefixApplicationName()) ) {
//            changedKeys.add("prefixApplicationName");
//        }
//        return changedKeys;
//    }
//}
