package com.Clubbr.Clubbr.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class registerRequest {

        private String userID;
        private String password;
        private String name;
        private String surname;
        private String country;
        private String address;
        private String email;
        private String phone;
        private String role;
}
