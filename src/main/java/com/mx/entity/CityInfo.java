package com.mx.entity;

public class CityInfo {
    private Integer cityId;

    private String cityPy;

    private String cityName;

    private String country;

    private String province;

    private String firstCity;

    private String latitude;

    private String longitude;

    public CityInfo(Integer cityId, String cityPy, String cityName, String country, String province, String firstCity, String latitude, String longitude) {
        this.cityId = cityId;
        this.cityPy = cityPy;
        this.cityName = cityName;
        this.country = country;
        this.province = province;
        this.firstCity = firstCity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getCityPy() {
        return cityPy;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getFirstCity() {
        return firstCity;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}