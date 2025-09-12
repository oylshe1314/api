package com.sk.op.api.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

@Getter
@Schema(title = "分页查询结果")
public class PageResultDto<R> {

    @Schema(name = "pageNo", title = "总数", description = "从1开始")
    private final int pageNo;

    @Schema(name = "pageSize", title = "单页数目", description = "值范围(10-50)")
    private final int pageSize;

    @Schema(name = "total", title = "总数")
    private final long total;

    @Schema(name = "pages", title = "总页数")
    private final long pages;

    @Schema(name = "results", title = "结果数据")
    private final List<R> results;

    public PageResultDto(Pageable pageable, Page<R> page) {
        this.pageNo = pageable.getPageNumber() + 1;
        this.pageSize = pageable.getPageSize();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.results = page.getContent();
    }

    public <S> PageResultDto(Pageable pageable, Page<S> page, Function<S, R> mapper) {
        this.pageNo = pageable.getPageNumber() + 1;
        this.pageSize = pageable.getPageSize();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.results = page.getContent().stream().map(mapper).toList();
    }
}
