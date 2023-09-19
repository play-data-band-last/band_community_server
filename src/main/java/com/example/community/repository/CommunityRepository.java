package com.example.community.repository;

import com.example.community.domain.entity.Community;
import com.example.community.domain.response.CommunityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("select new com.example.community.domain.response.CommunityResponse(c.id,c.ownerId, c.name, c.category, c.interest, c.description, c.profileImage ) " +
            "from Community c " +
            "where c.id = :communityId ")
    CommunityResponse findByCommunityId(@Param("communityId") Long communityId);

    @Query("select new com.example.community.domain.response.CommunityResponse(c.id,c.ownerId , c.name, c.category, c.interest, c.description, c.profileImage ) " +
            "from Community c " +
            "where c.interest like :interest " +
            "order by rand()")
    Page<CommunityResponse> findAllByInterest(@Param("interest") String interest, PageRequest pageRequest);


    @Query("select new com.example.community.domain.response.CommunityResponse(c.id,c.ownerId , c.name, c.category, c.interest, c.description, c.profileImage ) " +
            "from Community c " +
            "where c.interest in :interests " +
            "order by rand()")
    Page<CommunityResponse> findByAllInterest(@Param("interests")List<String> interests, PageRequest pageRequest);

    @Query("select new com.example.community.domain.response.CommunityResponse(c.id,c.ownerId , c.name, c.category, c.interest, c.description, c.profileImage ) " +
            "from Community c " +
            "order by c.id desc ")
    Page<CommunityResponse> findByGenerateOrder( PageRequest pageRequest);

}
