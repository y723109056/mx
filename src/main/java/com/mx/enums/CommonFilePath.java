package com.mx.enums;

public enum CommonFilePath {
	
	RECORD_PATH("/record","录音文件路径"),
	RECORD_TEMP_PATH("/temp/record","临时文件路径");
	
	CommonFilePath(String path, String name) {
        this.path = path;
        this.name = name;
    }
    
    private String path;
    private String name;

    public String getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }
}
