package com.sk.op.api.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@Schema(title = "分页查询参数")
public class PageRequestDto<Q> {

    @NotNull
    @Min(value = 1, message = "页码最小从1开始)")
    @Schema(name = "pageNo", title = "页码", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Integer pageNo;

    @NotNull
    @Range(min = 10, max = 100, message = "页数量须在10-100之间")
    @Schema(name = "pageSize", title = "页数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Integer pageSize;

    @Schema(name = "query", title = "查询参数")
    private final Q query;

    @JsonCreator
    public PageRequestDto(@JsonProperty("pageNo") Integer pageNo, @JsonProperty("pageSize") Integer pageSize, @JsonProperty("query") Q query) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.query = query;
    }
}
