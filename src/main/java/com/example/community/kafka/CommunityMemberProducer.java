package com.example.community.kafka;

import com.example.community.domain.request.CommunityMemberReqeust;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityMemberProducer {
    private final KafkaTemplate<String, CommunityMemberReqeust> kafkaTemplate;
    public void send(CommunityMemberReqeust communityMemberReqeust) {
        kafkaTemplate.send(TopicConfig.communityMember, communityMemberReqeust);
    }

}
