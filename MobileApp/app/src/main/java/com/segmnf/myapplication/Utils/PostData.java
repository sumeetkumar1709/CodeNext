package com.segmnf.myapplication.Utils;

import com.segmnf.myapplication.ApiHandler;

public class PostData
{
    private String clientId;
    private String clientSecret;
    private String script;
    private String stdin;

    private String language;
    private String versionIndex;

    public PostData(String script,String stdin, String language, String versionIndex) {
        this.script = script;
        this.clientId = ApiHandler.API_ID;
        this.clientSecret = ApiHandler.API_SECRET;
        this.stdin = stdin;

        this.language = language;
        this.versionIndex = versionIndex;
    }
}