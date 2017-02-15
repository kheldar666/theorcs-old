package org.libermundi.theorcs.utils;

import java.util.List;

/**
 * Interface describing an externally sorted and paginated list. 
 *
 */
public interface PaginatedList<E> extends Iterable<E> {
    int getPageIndex();

    int getPageSize();
    
    int getActualPageSize();

    boolean isFirstPage();

    boolean isLastPage();

    boolean isNextPageAvailable();

    boolean isPreviousPageAvailable();

    int getTotalPages();

    int getTotalItems();

    void setTotalItems(int totalItems);

    void setPageList(List<E> pageList);

    List<E> getPageList();
    
    E[] toArray(E[] arr);
}
