package com.management.login.vo;


import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-28  10:13
 */
public class Nav {
    private String id;
    private String title;
    private String icon;
    private boolean spread;
    private List<Nav> children;
    private String href;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public List<Nav> getChildren() {
        return children;
    }

    public void setChildren(List<Nav> children) {
        this.children = children;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
