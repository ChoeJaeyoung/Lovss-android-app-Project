package kr.co.ezenac.cjy.teamproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2018-02-05.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member_img {
    private Integer id;
    private String member_img_path;
    private String member_img_id;
}
