package com.example.community.controller;

import com.example.community.domain.request.CommunityReqeust;
import com.example.community.domain.response.CommunityResponse;
import com.example.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping
    public void save(@RequestBody CommunityReqeust communityReqeust) throws Exception {
        communityService.save(communityReqeust);
    }

    @GetMapping("/{communityId}")
    public CommunityResponse findById(@PathVariable("communityId") Long communityId){
        return communityService.findById(communityId);
    }

    //단일 흥미 반환(메인 각 흥미 클릭 시)
    @GetMapping("/interest")
    public Page<CommunityResponse> findByInterest(@RequestParam(name = "interest") String interest,
                               @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
                               ){

        return communityService.findAllByInterest(interest, PageRequest.of(page,size));
    }

    //전체 흥미 반환(메인에서 유저 흥미리스트 보내면 가능)
    @PostMapping("/allinterest")
    public Page<CommunityResponse> findByAllInterest(
            @RequestBody List<String> interests,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size

    ){

        return communityService.findByAllInterest(interests,PageRequest.of(page,size));
    }

    //메인 신규 커뮤니티 반환
    @GetMapping("/generateorder")
    public Page<CommunityResponse> findByGenerateOrder(
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ){
        return communityService.findByGenerateOrder(PageRequest.of(page,size));
    }


    //이거는 구현 할지 말지 고민 중(일단은 delete로 구현했는데, 다른 서버에 결과가 반영 안됨)
    @DeleteMapping("/{communityId}")
    public void deleteById(@PathVariable("communityId") Long communityId){
        communityService.deleteById(communityId);
    }

    //커뮤니티 업데이트(다른 디비에 트랜잭션 처리 필요함)
    @PutMapping("/{updateById}")
    public void updateById(@PathVariable("updateById") Long updateById,
                           @RequestBody CommunityReqeust communityReqeust){
        communityService.updateById(updateById, communityReqeust);
    }

}
