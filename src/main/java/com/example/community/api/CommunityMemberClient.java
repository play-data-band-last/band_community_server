package com.example.community.api;

import com.example.community.domain.request.CommunityMemberReqeust;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("BAND-COMMUNITY-MEMBER-SERVICE")
public interface CommunityMemberClient {
    @PutMapping("api/v1/communitymember/updatecommunity/{communityId}")
    void updateCommunityInCommunityMember(
            @RequestBody CommunityMemberReqeust communityMemberReqeust,
            @PathVariable("communityId") Long communityId);

    @PostMapping("/api/v1/communitymember/{communityId}")
    void saveCommunityMember(@PathVariable("communityId") Long communityId,
                                    @RequestBody CommunityMemberReqeust communityMemberReqeust);

}