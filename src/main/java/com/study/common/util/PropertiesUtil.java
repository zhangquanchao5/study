package com.study.common.util;

import com.study.common.StudyLogger;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Level;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * The type Properties util.
 */
public final class PropertiesUtil {
    private PropertiesUtil() {
    }

    /**
     * The constant DEFAULT_RESOURCE_PATTERN.
     */
    public static final String DEFAULT_RESOURCE_PATTERN = "classpath:*.properties";

    private static final Map<Object, Object> properties = new HashMap<Object, Object>();


    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public static String getString(String key) {
        return (String) properties.get(key);
    }

    static {
        loadProperties();
        startFileMonitor();
    }

    /**
     * Start file monitor.
     */
    public static void startFileMonitor(){
        long interval = TimeUnit.SECONDS.toMillis(5);
        // 创建一个文件观察器用于处理文件的格式
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(DEFAULT_RESOURCE_PATTERN);
            FileAlterationObserver observer = new FileAlterationObserver(
                    resources[0].getFile().getParent(),
                    FileFilterUtils.and(
                            FileFilterUtils.fileFileFilter(),
                            FileFilterUtils.suffixFileFilter(".properties")),  //过滤文件格式
                    null
            );
            observer.addListener(new FileListener()); //设置文件变化监听器
            //创建文件变化监听器
            FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
            // 开始监控
            monitor.start();
            StudyLogger.recBusinessLog("Properties Monitor has start!");
        }catch (Exception e){
            StudyLogger.recBusinessLog(Level.ERROR, e.getMessage(),e);
        }
    }

    /**
     * The type File listener.
     */
    static class FileListener extends FileAlterationListenerAdaptor {
        @Override
        public void onFileChange(File file) {
            StudyLogger.recBusinessLog("properties changed,reload properties");
            properties.clear();
            loadProperties();
            StudyLogger.recBusinessLog("reload properties done");
        }
    }

    /**
     * Load properties.
     */
    public static void loadProperties(){
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(DEFAULT_RESOURCE_PATTERN);
            if (resources != null) {
                for (Resource r : resources) {
                    Reader in = new InputStreamReader(r.getInputStream(), "UTF-8");
                    Properties p = new Properties();
                    p.load(in);
                    properties.putAll(p);
                    in.close();
                }
            }
        } catch (IOException e) {
            StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
        }
    }


}
