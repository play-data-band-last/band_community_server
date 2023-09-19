package com.example.community.domain.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommunityMemberReqeust {
    private Long memberId;
    private String memberRole;
    private String memberName;
    private String memberImage;
    private String communityName;
    private String communityImage;
}
