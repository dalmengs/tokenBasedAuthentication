package com.baseproject.tokenbasedauthentication.Dto.Authentication;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDto {

    private String accessToken;
    private String username;

}
