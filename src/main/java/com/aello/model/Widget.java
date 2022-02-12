package com.aello.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.aello.constants.CommonConstants.DATE_PATTERN;
import static com.aello.constants.WidgetConstants.*;

@ApiModel(value = "Widget", description = WIDGET_DESCRIPTION)
@Builder
@Data
public class Widget {
    @ApiModelProperty(value = "Unique identifier", example = UUID_EXAMPLE)
    private UUID uuid;
    @ApiModelProperty(value = Z_INDEX_DESCRIPTION, example = INTEGER_EXAMPLE)
    private Integer zIndex;
    @ApiModelProperty(value = "X coordinate", example = INTEGER_EXAMPLE)
    @NotNull
    private Integer x;
    @ApiModelProperty(value = "Y coordinate", example = INTEGER_EXAMPLE)
    @NotNull
    private Integer y;
    @ApiModelProperty(value = "Widget width", example = INTEGER_EXAMPLE)
    @Min(value = 1)
    private int width;
    @ApiModelProperty(value = "Widget height", example = INTEGER_EXAMPLE)
    @Min(value = 1)
    private int height;
    @ApiModelProperty(hidden = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_PATTERN)
    @EqualsAndHashCode.Exclude
    private LocalDateTime dateModified;
}
