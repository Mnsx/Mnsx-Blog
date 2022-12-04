package top.mnsx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Integer pageNum;
    private Integer pageSize;
    private Long categoryId;
}
