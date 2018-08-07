package org.code4everything.ichat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pantao
 * @since 2018-08-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserVO {

    private String uid;

    private String username;

    private String avatar;

    private String bio;
}
