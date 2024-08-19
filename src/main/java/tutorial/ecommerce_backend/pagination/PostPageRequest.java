package tutorial.ecommerce_backend.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PostPageRequest implements Pageable {
    private static final int DEFAULT_LIMIT = 12;
    private final int limit;
    private final int index;
    private final Sort sort;

    public PostPageRequest(int index, Sort sort) {
        this.limit = DEFAULT_LIMIT;
        this.index = index;
        this.sort = sort;
    }

    private int calculateOffset() {
        return index * limit;
    }

    @Override
    public int getPageNumber() {
        return index;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return calculateOffset();
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new PostPageRequest(index + 1, getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new PostPageRequest(index - 1, getSort()) : first();
    }

    @Override
    public Pageable first() {
        return new PostPageRequest(0, getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new PostPageRequest(pageNumber, getSort());
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }
}
