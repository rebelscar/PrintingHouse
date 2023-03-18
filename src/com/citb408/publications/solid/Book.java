package com.citb408.publications.solid;

import com.citb408.publications.abstraction.PageSize;
import com.citb408.publications.abstraction.PaperType;
import com.citb408.publications.abstraction.Publication;

public class Book extends Publication {
    public Book(String title, PageSize pageSize, int pages) {
        super(title, pageSize, PaperType.REGULAR,pages);
    }

    public Book(String title, int pages) {
        this(title, PageSize.A3, pages);
    }
}
