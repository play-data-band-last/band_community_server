package com.example.community.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunitySearchRequest {
    private Long ownerId;
    private String name;
    private String location;
    private String category;
    private String interest;
    private String description;
    private String profileImage;
    private String communityUUID;
    private Long communityId;

}
