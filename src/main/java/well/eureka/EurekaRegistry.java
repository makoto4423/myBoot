///*
// * Copyright(C) 2013 Agree Corporation. All rights reserved.
// *
// * Contributors:
// *     Agree Corporation - initial API and implementation
// */
//package eureka;
//
//import eureka.*;
//
//import com.netflix.appinfo.ApplicationInfoManager;
//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
//import com.netflix.config.ConfigurationManager;
//import com.netflix.discovery.*;
//import com.netflix.discovery.shared.Application;
//import com.netflix.discovery.shared.Applications;
//
//import java.util.*;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// *
// *
// *
// * @author zhiwei
// * @date 2019年7月2日 下午5:02:45
// * @version 1.0
// *
// */
//public class EurekaRegistry {
//
//
//    public static final String EUREKA_CONFIG_SERVER_URL_KEY = "eureka.serviceUrl.default";
//    public static final String EUREKA_CONFIG_REFRESH_KEY = "eureka.client.refresh.interval";
//    public static final String EUREKA_CONFIG_SHOULD_REGISTER = "eureka.registration.enabled";
//    public static final String EUREKA_APPLICATION_PREFIX_APPLICATION = "application";
//
//    private static final String EUREKA_PATH_SPLIT_CHAR = "-";
//    private static final String DEFAULT_APPLICATION = "default";
//    private static final String EUREKA_METADATAS_URL_VALUE_KEY = "url";
//    private static final int EUREKA_REFRESH_INTERVAL = 5;
//    private static final int MAP_INITIAL_CAPACITY = 8;
//
//    private String prefixApplicationName;
//    private boolean subscribeListener = false;
//    private ApplicationInfoManager applicationInfoManager;
//
//    private EurekaClient eurekaClient = null;
//    private EurekaEventListener eurekaEventListener = null;
//    private final ConcurrentMap<String, List<IRegistryListener>> CLIENT_LISTENER = new ConcurrentHashMap<>();
//    private final ConcurrentMap<String, Boolean> listenerDataChange = new ConcurrentHashMap<String, Boolean>();
//
//    /**
//     * 初始化EurekaClient存放的配置
//     */
//    private EurekaRegistryConfig config;
//
//    /**
//     * 一个Instance(注册信息所需要的相关配置)
//     */
//    private CustomEurekaInstanceConfig instanceConfig = null;
//    /**
//     * 用于存储服务对应的注册信息<>
//     */
//    private ConcurrentMap<String, List<String>> clusterAddressMap;
//
//    public EurekaRegistry(EurekaRegistryConfig config) {
//        this.clusterAddressMap = new ConcurrentHashMap<>(MAP_INITIAL_CAPACITY);
//        this.instanceConfig = new CustomEurekaInstanceConfig();
//        this.config = config;
//        this.prefixApplicationName = config.getPrefixApplicationName();
//    }
//
//    @Override
//    public void register(String ip, int port, String clusterId,
//            Map<String, String> arguments) throws RegistryException {
//        String applicationName = null;
//        checkAddress(ip, port);
//        instanceConfig.setIpAddress(ip);
//        instanceConfig.setPort(port);
//        applicationName = generateApplicationName(clusterId);
//        instanceConfig.setApplicationName(applicationName);
//        instanceConfig.setInstanceId(getInstanceId());
//        generateMetaData(arguments);
//        getEurekaClient(true);
//        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
//        logger.info(
//                "Successfully register in the eureka's Registry,the register application name is {}",
//                applicationName);
//
//    }
//
//    @Override
//    public void unregister(String ip, int port, String clusterId)
//            throws RegistryException {
//        if (eurekaClient == null) {
//            return;
//        }
//        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
//        logger.info("Successfully unregister in the eureka's Registry");
//
//    }
//
//    @Override
//    public void subscribe(String workId, IRegistryListener listener)
//            throws RegistryException {
//        String applicationName = generateApplicationName(workId);
//        List<IRegistryListener> listeners = null;
//        clusterAddressMap.putIfAbsent(applicationName, new ArrayList<String>());
//        if (!subscribeListener) {
//            subscribe(workId, new EurekaEventListener() {
//                @Override
//                public void onEvent(EurekaEvent event) {
//                    try {
//                        if (event instanceof CacheRefreshedEvent) {
//                            if (cheekChange()) {
//                                refreshCluster();
//                                for (Entry<String, Boolean> entry : listenerDataChange
//                                        .entrySet()) {
//                                    if (entry.getValue()) {
//                                        logger.info("the ats's server service list changge ,the application name is {}",entry.getKey());
//                                        List<IRegistryListener> listeners = CLIENT_LISTENER
//                                                .get(entry.getKey());
//                                        for (IRegistryListener listener : listeners) {
//                                            listener.noity();
//                                        }
//                                        listenerDataChange.put(entry.getKey(), false);
//                                    }
//                                }
//                                if (logger.isDebugEnabled()) {
//                                    logger.debug(
//                                            "the server's data change refresh eureka local cached service message successfully!");
//                                }
//                            }
//
//                        }
//
//                    } catch (Exception e) {
//                        logger.error("Eureka event listener refreshCluster error!", e);
//                    }
//                }
//            });
//        }
//        listeners = CLIENT_LISTENER.get(applicationName);
//        if (listeners == null) {
//            listeners = new ArrayList<>();
//            CLIENT_LISTENER.put(applicationName, listeners);
//        }
//        if (!listenerDataChange.containsKey(applicationName)) {
//            listenerDataChange.put(applicationName, false);
//        }
//        listeners.add(listener);
//        logger.info("Successfully subscribe in the eureka's Registry");
//    }
//
//    @Override
//    public void unsubscribe(String workId) throws RegistryException {
//        if (eurekaClient == null) {
//            return;
//        }
//        if (subscribeListener) {
//            subscribeListener = false;
//            getEurekaClient(false).unregisterEventListener(eurekaEventListener);
//        }
//
//    }
//
//    @Override
//    public List<String> lookup(String workId) throws RegistryException {
//        if (null == workId) {
//            return null;
//        }
//        String applicationName = generateApplicationName(workId);
//        if (!subscribeListener) {
//            refreshCluster();
//
//        }
//        List<String> result = clusterAddressMap.get(applicationName.toUpperCase());
//        return result == null ? Collections.emptyList() : result;
//    }
//
//    private void subscribe(String workId, EurekaEventListener listener)
//            throws RegistryException {
//        subscribeListener = true;
//        getEurekaClient(false).registerEventListener(listener);;
//
//    }
//
//    @Override
//    public void close() throws RegistryException {
//        if (eurekaClient != null) {
//            eurekaClient.shutdown();
//        }
//        clean();
//    }
//
//    @Override
//    public RegistryType getRegistryType() {
//        return RegistryType.EUREKA;
//    }
//
//    private EurekaClient getEurekaClient(boolean needRegister) throws RegistryException {
//        // TODO 没有双重检验，能否避免并发问题？
//        if (eurekaClient == null) {
//            synchronized (EurekaRegistry.class) {// TODO 锁住整个 class?
//                try {
//                    Properties props = generatePropsFromConfig();
//                    if (!needRegister) {
//                        // 刷新instanceConfig，，说明并不需要刚才设置的注册信息
//                        instanceConfig = new CustomEurekaInstanceConfig();
//                        props.setProperty(EUREKA_CONFIG_SHOULD_REGISTER, "false");
//                    }
//                    // 在这里实现相关的注册功能
//                    ConfigurationManager.loadProperties(props);
//                    InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(
//                            instanceConfig).get();
//                    applicationInfoManager = new ApplicationInfoManager(instanceConfig,
//                            instanceInfo);
//                    eurekaClient = new DiscoveryClient(applicationInfoManager,
//                            new DefaultEurekaClientConfig());
//                } catch (Exception e) {
//                    clean();
//                    throw new RegistryException("register eureka is error!", e);
//                }
//            }
//        }
//        return eurekaClient;
//    }
//
//    private Properties generatePropsFromConfig() {
//        Properties props = new Properties();
//        props.setProperty(EUREKA_CONFIG_SERVER_URL_KEY, config.getServers());
//        props.setProperty(EUREKA_CONFIG_REFRESH_KEY, String.valueOf(config.getRefreshInterval()));
//        props.setProperty(EUREKA_APPLICATION_PREFIX_APPLICATION, config.getPrefixApplicationName());
//        return props;
//    }
//
//    private void checkAddress(String ip, int port) throws RegistryException {
//        if (null == ip || 0 >= port) {
//            throw new RegistryException("invalid address:" + ip + ":" + port);
//        }
//    }
//
//    private String getInstanceId() {
//        return String.format(":%s:%d", instanceConfig.getIpAddress(),
//                instanceConfig.getNonSecurePort());
//    }
//
//    private void clean() {
//        eurekaClient = null;
//        applicationInfoManager = null;
//        instanceConfig = null;
//    }
//
//    /**
//     * 刷新本地缓存数据
//     *
//     * @throws RegistryException
//     */
//    private void refreshCluster() throws RegistryException {
//        Applications applications = getEurekaClient(false).getApplications();
//        List<Application> list = applications.getRegisteredApplications();
//        if (list == null || list.isEmpty()) {
//            clusterAddressMap.clear();
//            return;
//        }
//        for (Application app : list) {
//            List<String> childClusterData = new ArrayList<>();
//            List<InstanceInfo> instances = app.getInstances();
//            String applicationNmae = app.getName();
//            if (instances == null || instances.isEmpty()) {
//                childClusterData = clusterAddressMap.get(applicationNmae);
//                if (childClusterData != null && !childClusterData.isEmpty()) {
//                    childClusterData.clear();
//                }
//                continue;
//            }
//            for (InstanceInfo instance : instances) {
//                StringBuilder url = new StringBuilder(instance.getIPAddr());
//                String value = null;
//                url.append(URL_IP_PORT_SPLIT_CHAR);
//                url.append(instance.getPort());
//                url.append(URL_PORT_ARGUMENT_SPLIT_CHAR);
//                Map<String, String> metaData = instance.getMetadata();
//                value = metaData.get(EUREKA_METADATAS_URL_VALUE_KEY);
//                url.append(value);
//                childClusterData.add(url.toString());
//            }
//            clusterAddressMap.put(app.getName(), childClusterData);
//        }
//    }
//
//    private void generateMetaData(Map<String, String> arguments) {
//        StringBuilder values = new StringBuilder("");
//        boolean flag = false;
//        for (Entry<String, String> entry : arguments.entrySet()) {
//            if (!flag) {
//                flag = true;
//            } else {
//                values.append(URL_ARGUMENT_SPLIT_CHAR);
//            }
//            values.append(entry.getKey() + URL_ARGUMENT_EQUAL_CHAR + entry.getValue());
//        }
//        arguments.clear();
//        arguments.put(EUREKA_METADATAS_URL_VALUE_KEY, values.toString());
//        instanceConfig.setMetadataMap(arguments);
//    }
//
//    private String generateApplicationName(String workId) {
//        return this.prefixApplicationName + EUREKA_PATH_SPLIT_CHAR + workId;
//    }
//
//    /**
//     * 查看本地数据与EurekaClient缓存的服务器数据是否有变化，若有变化，则返回true，若没有则返回false
//     *
//     * @return
//     * @throws RegistryException
//     */
//    private boolean cheekChange() throws RegistryException {
//        boolean needChange = false;
//        Applications applications = getEurekaClient(false).getApplications();
//        List<Application> list = applications.getRegisteredApplications();
//        if (list == null || list.isEmpty()) {
//            if (clusterAddressMap == null || clusterAddressMap.isEmpty()) {
//                return false;
//            } else {
//                for (String applicationName : CLIENT_LISTENER.keySet()) {
//                    List<String> instances = clusterAddressMap.get(applicationName);
//                    if (!(instances == null || instances.isEmpty()))
//                        listenerDataChange.putIfAbsent(applicationName, true);
//                }
//                return true;
//
//            }
//        }
//        for (Application app : list) {
//            List<String> childClusterData = clusterAddressMap.get(app.getName());
//            List<InstanceInfo> instances = app.getInstances();
//            List<String> deleteClusterData = null;
//            // 假如childClusterData为null，说明本地缓存中没有缓存该目录下的地址信息，说明客户端并不需要使用到该目录的地址信息
//            // 所以不管它怎么变换，都不影响到客户端的使用
//            if (childClusterData == null) {
//                continue;
//            } else {
//                deleteClusterData = copyClusterData(childClusterData);
//            }
//            if (instances == null || instances.isEmpty()) {
//                if (childClusterData.isEmpty()) {
//                    continue;
//                } else {
//                    needChange = true;
//                    if (CLIENT_LISTENER.containsKey(app.getName())) {
//                        listenerDataChange.putIfAbsent(app.getName(), true);
//                    }
//                    continue;
//                }
//            }
//            for (InstanceInfo instance : instances) {
//                StringBuilder url = new StringBuilder(instance.getIPAddr());
//                String value = null;
//                url.append(URL_IP_PORT_SPLIT_CHAR);
//                url.append(instance.getPort());
//                url.append(URL_PORT_ARGUMENT_SPLIT_CHAR);
//                Map<String, String> metaData = instance.getMetadata();
//                value = metaData.get(EUREKA_METADATAS_URL_VALUE_KEY);
//                url.append(value);
//                if (!childClusterData.contains(url.toString())) {
//                    if (CLIENT_LISTENER.containsKey(app.getName())) {
//                        listenerDataChange.put(app.getName(), Boolean.valueOf(true));
//                    }
//                    needChange = true;
//                    deleteClusterData.clear();
//                    break;
//                } else {
//                    deleteClusterData.remove(url.toString());
//                }
//            }
//            // 如果不是空的，说明EurekaServer上的一些注册信息被删除了
//            if (!deleteClusterData.isEmpty()) {
//                if (CLIENT_LISTENER.containsKey(app.getName())) {
//                    listenerDataChange.put(app.getName(), Boolean.valueOf(true));
//                }
//                needChange = true;
//            }
//        }
//
//        return needChange;
//    }
//
//    private List<String> copyClusterData(List<String> clusterData) {
//        return new ArrayList<String>(clusterData);
//    }
//
//    public EurekaRegistryConfig getConfig() {
//        return config;
//    }
//}
