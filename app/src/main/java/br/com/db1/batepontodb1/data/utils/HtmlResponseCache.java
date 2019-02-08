package br.com.db1.batepontodb1.data.utils;

public class HtmlResponseCache {
    String htmlResponse;
    static HtmlResponseCache mInstance;

    private HtmlResponseCache() {
        mInstance = null;
    }

    public static HtmlResponseCache getInstance(){
        if (mInstance==null){
            mInstance = new HtmlResponseCache();
        }
        return mInstance;
    }

    public String getHtmlResponse() {
        return htmlResponse;
    }

    public void setHtmlResponse(String htmlResponse) {
        this.htmlResponse = htmlResponse;
    }



}
