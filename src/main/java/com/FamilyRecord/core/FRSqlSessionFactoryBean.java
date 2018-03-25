package com.FamilyRecord.core;

import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by yuan on 2018/3/24.
 */
public class FRSqlSessionFactoryBean  extends SqlSessionFactoryBean {
    Logger logger = LoggerFactory.getLogger("appsLoger");

    public FRSqlSessionFactoryBean() {
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        String[] typeAliasPackageArray = StringUtils.tokenizeToStringArray(typeAliasesPackage, ",; \t\n");
        StringBuffer sb = new StringBuffer();
        String[] tempTypeAliasPackageArray = typeAliasPackageArray;
        int length = typeAliasPackageArray.length;

        for(int i = 0; i < length; ++i) {
            String packageToScan = tempTypeAliasPackageArray[i];
            this.logger.info("scan package name : " + packageToScan);
            HashSet sc = new HashSet();
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

            try {
                String ex = "classpath*:" + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packageToScan)) + "/**/*.class";
                Resource[] resources = resourcePatternResolver.getResources(ex);
                MetadataReader metadataReader;

                for(int j = 0; j < resources.length; ++j) {
                    Resource resource = resources[j];
                    if(resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        try {
                            sc.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                        } catch (ClassNotFoundException var9) {
                            var9.printStackTrace();
                        }

                    }
                }

            } catch (IOException var10) {
                throw new BeanDefinitionStoreException("I/O failure during classpath scanning", var10);
            }
            Iterator it = sc.iterator();

            while(it.hasNext()) {
                Class cls = (Class)it.next();
                String p = cls.getPackage().getName();
                this.logger.info("append package name : " + p);
                sb.append(p + ",");
            }
        }

        this.logger.debug(sb.toString());
        if(sb.length() > 0) {
            super.setTypeAliasesPackage(sb.toString().substring(0,sb.length()-1));
        } else {
            this.logger.warn("参数typeAliasesPackage:" + typeAliasesPackage + "，未找到任何包");
        }

    }

    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        super.setTypeHandlers(typeHandlers);
    }
}
