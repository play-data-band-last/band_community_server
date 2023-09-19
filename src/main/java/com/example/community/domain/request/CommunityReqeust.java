package com.example.community.domain.request;

import com.example.community.domain.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class  CommunityReqeust {
    private Long ownerId;
    private String name;
    private String location;
    private String category;
    private String interest;
    private String description;
    private String profileImage;


    public Community toEntity(){
        return Community.builder()
                .name(name)
                .ownerId(ownerId)
                .location(location)
                .category(category)
                .interest(interest)
                .description(description)
                .profileImage(profileImage)
                .build();
    }
}
