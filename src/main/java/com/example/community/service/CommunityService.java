package com.example.community.service;


import com.example.community.domain.entity.Community;
import com.example.community.domain.request.CommunityMemberReqeust;
import com.example.community.domain.request.CommunityRequest;
import com.example.community.domain.request.CommunitySearchRequest;
import com.example.community.domain.response.CommunityResponse;

import com.example.community.kafka.CommunityMemberProducer;
import com.example.community.kafka.CommunitySearchProducer;
import com.example.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMemberProducer communityMemberProducer;
    private final CommunitySearchProducer communitySearchProducer;

    @Transactional
    public void save(CommunityRequest communityRequest) throws Exception {
//        try {
        Community save = communityRepository.save(communityRequest.toEntity());

        CommunityMemberReqeust communityMemberReqeust = CommunityMemberReqeust.builder()
                    .memberId(communityRequest.getMemberId())
                    .memberRole("모임장")
                    .memberName(communityRequest.getMemberName())
                    .communityName(communityRequest.getDescription())
                    .communityImage(communityRequest.getCommunityImage())
                    .communityId(save.getId())
                    .memberImage(communityRequest.getMemberImage())
                    .ownerId(communityRequest.getMemberId())
                    .build();


        CommunitySearchRequest communitySearchRequest = CommunitySearchRequest.builder()
                .name(save.getName())
                .ownerId(save.getOwnerId())
                .profileImage(communityRequest.getCommunityImage())
                .description(save.getDescription())
                .category(save.getCategory())
                .interest(save.getInterest())
                .communityId(save.getId())
                .build();


            communitySearchProducer.send(communitySearchRequest);

            communityMemberProducer.send(communityMemberReqeust);


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
    public void updateById(Long communityId, CommunityRequest communityRequest){
        Community community = communityRepository.findById(communityId).get();
        community.setCategory(communityRequest.getCategory());
        community.setInterest(communityRequest.getInterest());
        community.setName(communityRequest.getCommunityName());
        community.setLocation(communityRequest.getLocation());
        community.setProfileImage(communityRequest.getCommunityImage());
        community.setDescription(communityRequest.getDescription());

        // communityMemberTableUpdate
//        communityMemberClient.updateCommunityInCommunityMember(
//                new CommunityMemberReqeust(null,null,null,null,
//                        communityReqeust.getName(),communityReqeust.getProfileImage()),communityId);
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
