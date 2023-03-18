package com.citb408.publications.abstraction;

import java.io.Serializable;

public abstract class Publication implements Cost, Serializable {
    public static final long serialVersionUID = 123L;
    private final String title;
    private final PageSize pageSize;
    private final PaperType paperType;
    private int pages;

    protected Publication(String title, PageSize pageSize, PaperType paperType, int pages) {
        this.title = title;
        this.pageSize = pageSize;
        this.paperType = paperType;
        setPages(pages);
    }

    public String getTitle() {
        return title;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public int getPages() {
        return pages;
    }

    // without the total pages into account
    public double singlePageCost() {
        return pageSize.getCost() + paperType.getCost();
    }

    @Override
    public double getCost() {
        return pages * singlePageCost();
    }

    private void setPages(int pages) {
        this.pages = Math.max(pages, 0);
    }

    @Override
    public String toString() {
        return title + ", pageSize=" + pageSize +
                ", paperType=" + paperType +
                ", pages=" + pages;
    }


}
