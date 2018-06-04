package com.hwq.hwq_core.app;

import android.graphics.Bitmap;

import java.util.WeakHashMap;

//配置项
public class Configurator {
    //配置项用WeakHashMap    可以随时回收资源
    private static final WeakHashMap<String, Object> LATTE_CONFIGS = new WeakHashMap<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    //方式一
    //私有内部类安全单例模式
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();

    }

    //方式二
    //双重同步校验单例模式
   /* private volatile static Configurator configurator;

    public static Configurator getInstance() {
        if (configurator == null) {
            synchronized (Configurator.class) {
                if (configurator == null) {
                    configurator = new Configurator();
                }
            }
        }
        return configurator;
    }*/
    //获取相关配置
    final WeakHashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    //配置准备好调用
    public final void configure() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    //配置host地址
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.CONFIG_HOST.name(), host);
        return this;
    }

    //检查配置
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (isReady) {
            throw new RuntimeException("配置未完成，请先调用configure()方法");
        }
    }
    //获取配置
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T)LATTE_CONFIGS.get(key.name());
    }
}
