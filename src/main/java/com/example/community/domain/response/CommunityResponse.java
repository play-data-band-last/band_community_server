package com.example.community.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityResponse {
    private Long id;
    private Long ownerId;
    private String name;
    private String category;
    private String interest;
    private String description;
    private String profileImage;

}
