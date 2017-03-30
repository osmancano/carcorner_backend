package com.ironyard.repositories;

import com.ironyard.data.Car;
import com.ironyard.data.CarComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by osmanidris on 2/10/17.
 */
public interface CarCommentRepo extends PagingAndSortingRepository<CarComment,Long> {
}
