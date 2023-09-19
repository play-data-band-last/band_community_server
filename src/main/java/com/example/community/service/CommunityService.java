package com.example.community.service;


import com.example.community.api.CommunityMemberClient;
import com.example.community.domain.entity.Community;
import com.example.community.domain.request.CommunityMemberReqeust;
import com.example.community.domain.request.CommunityReqeust;
import com.example.community.domain.response.CommunityResponse;
import com.example.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMemberClient communityMemberClient;

    @Transactional
    public void save(CommunityReqeust communityReqeust) throws Exception {
        try {
            Community save = communityRepository.save(communityReqeust.toEntity());

            CommunityMemberReqeust communityMemberReqeust = CommunityMemberReqeust.builder()
                    .memberId(communityReqeust.getOwnerId())
                    .memberRole("모임장")
                    .memberName(communityReqeust.getName())
                    .communityName(communityReqeust.getDescription())
                    .communityImage(communityReqeust.getProfileImage())
                    .build();
            System.out.println(save.getId());
            communityMemberClient.saveCommunityMember(save.getId(), communityMemberReqeust);
        }catch (Exception e){
            throw new Exception("Community Save Failed");
        }
    };

    public Page<Community> findAllByMemberId(String memberId){
        // 커뮤니티 구성원 테이블 이용해서 맴버 개인 해당 커뮤니티 리턴=> 이 로직은 이 테이블에서 할게 아니다
        return null;
    }

    // 관심사 커뮤니티 리턴(추천 시스템)
    public Page<CommunityResponse> findAllByInterest(String interest, PageRequest pageRequest){
        return communityRepository.findAllByInterest("%"+interest+"%", pageRequest);
    }

    public CommunityResponse findById(Long communityId){
        return communityRepository.findByCommunityId(communityId);
    }

    public void deleteById(Long communityId){
        //여기서 communityMember 테이블에 커뮤니티 가입한 멤버도 싹 다 날려야함
        //원한다면 멤버에게 커뮤니티 삭제됐다고 알리는 로직도 필요함
        communityRepository.deleteById(communityId);
    }

    @Transactional
    public void updateById(Long communityId, CommunityReqeust communityReqeust){
        Community community = communityRepository.findById(communityId).get();
        community.setCategory(communityReqeust.getCategory());
        community.setInterest(communityReqeust.getInterest());
        community.setName(communityReqeust.getName());
        community.setLocation(communityReqeust.getLocation());
        community.setProfileImage(communityReqeust.getProfileImage());
        community.setDescription(communityReqeust.getDescription());

        // communityMemberTableUpdate
        communityMemberClient.updateCommunityInCommunityMember(
                new CommunityMemberReqeust(null,null,null,null,
                        communityReqeust.getName(),communityReqeust.getProfileImage()),communityId);
        //

    }


    public Page<CommunityResponse> findByAllInterest(
            List<String> interests, PageRequest pageRequest
    ){
        return communityRepository.findByAllInterest(interests, pageRequest);
    }

    public Page<CommunityResponse> findByGenerateOrder(
            PageRequest pageRequest
    ){
        return communityRepository.findByGenerateOrder(pageRequest);
    }

}
