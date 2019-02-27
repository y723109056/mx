package com.mx.dao;

import com.mx.entity.CityInfo;

public interface CityInfoMapper {
    int deleteByPrimaryKey(Integer cityId);

    int insert(CityInfo record);

    int insertSelective(CityInfo record);

    CityInfo selectByPrimaryKey(Integer cityId);

    int updateByPrimaryKeySelective(CityInfo record);

    int updateByPrimaryKey(CityInfo record);

    String queryCityIdByCityName(String cityId);
}