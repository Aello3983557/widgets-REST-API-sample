package com.aello.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.aello.constants.WidgetConstants.*;

@ApiModel(value = "Widget", description = WIDGET_DESCRIPTION)
@Builder
@Data
public class Widget {
    @ApiModelProperty(value = "Unique identifier", example = UUID_EXAMPLE)
    private String uuid;
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
    private Integer width;
    @ApiModelProperty(value = "Widget height", example = INTEGER_EXAMPLE)
    @Min(value = 1)
    private Integer height;
    @ApiModelProperty(hidden = true)
    @EqualsAndHashCode.Exclude
    private String dateModified;
}
