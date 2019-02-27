package com.mx.enums;

/**
 * @author 小米线儿
 * @time 2019/2/26 0026
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public enum IndexBgGifEnum {
    SUNSHINE(0,"bg_sunshine.gif","晴天"),
    RAIN(1,"bg_rain.gif","下雨"),
    WIND(2,"bg_wind.gif","微风"),
    SNOW(3,"bg_snow.gif","下雪")
    ;

    public int code;

    public String gif;

    public String memo;

    IndexBgGifEnum(int code, String gif, String memo) {
        this.code = code;
        this.gif = gif;
        this.memo = memo;
    }

    public static IndexBgGifEnum valueOfCode(int code){
        for(IndexBgGifEnum gifEnum : IndexBgGifEnum.values()){
            if(gifEnum.code==code){
                return gifEnum;
            }
        }
        return null;
    }
}
