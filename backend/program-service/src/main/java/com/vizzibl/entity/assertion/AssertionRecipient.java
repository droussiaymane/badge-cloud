package com.vizzibl.entity.assertion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssertionRecipient {
    private String type;
    //  private String salt;
    private boolean hashed;
    private String identity;
}
