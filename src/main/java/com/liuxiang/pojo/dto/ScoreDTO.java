package com.liuxiang.pojo.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO{
    private Integer studentId;
    private Integer courseId;
    private BigDecimal score;
}
