package com.mx.sql.config;

import com.mx.spring.SpringContext;
import com.mx.sql.SqlDriverException;
import com.mx.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * 数据库配置工厂
 */
public class DefaultConfigFactory implements IDbConfigFactory {
    /**
     * 默认的数据库配置
     */
    private IDbConfig defaultConfig;
    private static Log log = LogFactory.getLog(DefaultConfigFactory.class);
    private Map<String,IDbConfig> configs;
    private Set<String> filterConfigs;

    public void add(IDbConfig config){
        if(!this.configs.containsKey(config.getName())){
            this.configs.put(config.getName(), config);
            if(defaultConfig==null || (defaultConfig!=null && !defaultConfig.isDefault() && config.isDefault()))defaultConfig=config;
        }
    }

    /**
     *
     */
    public void remove(String name){
        this.configs.remove(name);
        this.defaultConfig = this.getDefaultConfig();
    }

    /**
     * 得到默认的数据库配置
     * @return
     */
    private IDbConfig getDefaultConfig(){
        if(this.defaultConfig!=null)return this.defaultConfig;
        else{
            List<String> names = this.getConfigBeanNames();
            for(String name : names){
                IDbConfig config = (IDbConfig) SpringContext.instance().get(name);
                config.setName(name);
                if(config.isDefault()){
                    this.defaultConfig = config;
                    break;
                }
            }
            for(IDbConfig config : this.configs.values()){
                if(config.isDefault()){
                    this.defaultConfig = config;
                    break;
                }
            }
            if(this.defaultConfig==null && names.size()>0){
                this.defaultConfig = (IDbConfig)SpringContext.instance().get(names.get(0));
            }
            if(this.defaultConfig==null && this.configs.values().size()>0){
                this.defaultConfig = this.configs.values().iterator().next();
            }
        }
        return this.defaultConfig;
    }

    private List<String> getConfigBeanNames(){
        List<String> list = new ArrayList<String>();
        String[] names = SpringContext.instance().getNames(IDbConfig.class);
        for(String name : names){
            if(!this.filterConfigs.contains(name))list.add(name);
        }
        return list;
    }

    public DefaultConfigFactory(){
        this.defaultConfig = null;
        this.configs = new HashMap<String,IDbConfig>();
        this.filterConfigs = new HashSet<String>();
        this.filterConfigs.add("dbConfig");
        this.filterConfigs.add("driverDbConfig");
    }

    /**
     * 得到默认的配置接口
     */
    public IDbConfig getConfig() {
        IDbConfig config = this.getDefaultConfig();
        if(config==null)throw new SqlDriverException("找不到默认的数据库配置项");
        return config;
    }

    /**
     * 通得数据库配置名称得到配置接口
     */
    public IDbConfig getConfig(String name){
        if(SpringContext.instance().contains(name)){
            IDbConfig config = (IDbConfig)SpringContext.instance().get(name);
            config.setName(name);
            return config;
        }else if(configs.containsKey(name)){
            return configs.get(name);
        }else{
            log.warn(StringUtil.format("不存在该数据库配置[0]", name));
            return null;
        }
    }

    /**
     * 得到所有配置接口
     */
    public List<IDbConfig> getConfigs(){
        List<String> names = this.getConfigBeanNames();
        List<IDbConfig> configs = new ArrayList<IDbConfig>();
        for(String name : names){
            configs.add(this.getConfig(name));
        }
        for(IDbConfig config : this.configs.values()){
            configs.add(config);
        }
        Collections.sort(configs);
        return configs;
    }

}
