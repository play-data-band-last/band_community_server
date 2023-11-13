package com.example.community.domain.request;

import com.example.community.domain.entity.Community;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommunityRequest {
    private Long memberId;
    private String memberRole;
    private String memberName;
    private String memberImage;


    private String communityName;
    private String communityImage;
    private String location;
    private String category;
    private String interest;
    private String description;

    public Community toEntity(){
        return Community.builder()
                .ownerId(memberId)
                .name(communityName)
                .location(location)
                .category(category)
                .interest(interest)
                .description(description)
                .profileImage(communityImage)
                .date(OffsetDateTime.now(ZoneOffset.ofHours(9)))
                .build();
    }
}
