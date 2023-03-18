package com.citb408.publications.solid;

import com.citb408.publications.abstraction.PageSize;
import com.citb408.publications.abstraction.PaperType;
import com.citb408.publications.abstraction.Publication;

public class Poster extends Publication {
    public static final int POSTER_TAX = 3;

    public Poster(String title, PageSize pageSize, int pages) {
        super(title, pageSize, PaperType.GLOSSY, pages);
    }

    public Poster(String title, int pages) {
        this(title, PageSize.A5, pages);
    }

    @Override
    public double getCost() {
        return super.getCost() + POSTER_TAX;
        // I feel like there should be a bigger tax for printing a whole poster,
        // so I've decided to add a constant to the cost of a poster
    }
}
