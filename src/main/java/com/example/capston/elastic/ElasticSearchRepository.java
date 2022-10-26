package com.example.capston.elastic;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ElasticSearchRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
}
