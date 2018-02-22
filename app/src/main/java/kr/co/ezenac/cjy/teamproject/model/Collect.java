package kr.co.ezenac.cjy.teamproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2018-02-22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Collect {
    private Integer id;
    private Integer heart;
    private Integer user_id;
    private Integer img_id;
    private String img_path;
    private String img_content;
}
