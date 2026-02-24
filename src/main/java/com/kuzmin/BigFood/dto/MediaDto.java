package com.kuzmin.BigFood.dto;

public class MediaDto {

    private Long id;
    private String url;
    private boolean main;

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean isMain() {
        return main;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}
