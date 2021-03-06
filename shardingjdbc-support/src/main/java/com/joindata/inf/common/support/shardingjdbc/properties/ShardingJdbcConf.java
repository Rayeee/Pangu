package com.joindata.inf.common.support.shardingjdbc.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.joindata.inf.common.support.disconf.util.DisconfUtil;
import com.joindata.inf.common.support.mybatis.properties.inner.ShardingDataSourceProperties;
import com.joindata.inf.common.support.shardingjdbc.properties.inner.ShardingWithGroupByColumnRule;
import com.joindata.inf.common.support.shardingjdbc.properties.inner.ShardingWithGroupRule;
import com.joindata.inf.common.util.basic.BeanUtil;

import lombok.Data;

@Data
@Service
@Scope("singleton")
@DisconfFile(filename = ShardingJdbcConf.FILENAME)
public class ShardingJdbcConf implements InitializingBean
{
    static final String FILENAME = "shardingjdbc.json";

    /** 数据源列表 */
    private ShardingDataSourceProperties[] dataSources;

    /** 用库分组、用表取模规则列表 */
    private ShardingWithGroupRule[] shardingWithGroupRule;

    /** 用库分组、用表取模并通过多个字段名路由的规则列表 */
    private ShardingWithGroupByColumnRule[] shardingWithGroupByColumnRule;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        this.build();
    }

    private void build()
    {
        ShardingJdbcConf conf = DisconfUtil.readJson(FILENAME, ShardingJdbcConf.class);
        BeanUtil.copyProperties(conf, this);
    }
}