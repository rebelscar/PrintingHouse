package com.citb408.publications.solid;

import com.citb408.publications.abstraction.PageSize;
import com.citb408.publications.abstraction.PaperType;
import com.citb408.publications.abstraction.Publication;

public class Newspaper extends Publication {
    public Newspaper(String title, PageSize pageSize, int pages) {
        super(title, pageSize, PaperType.NEWSPAPER, pages);
    }

    public Newspaper(String title, int pages) {
        this(title, PageSize.A4, pages);
    }
}
