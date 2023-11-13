package com.example.community.kafka;

import com.example.community.domain.request.CommunitySearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunitySearchProducer {
    private final ProducerFactory<String, CommunitySearchRequest> producerFactory;

    public void send(CommunitySearchRequest communitySearchRequest) {
        KafkaProducer<String, CommunitySearchRequest> kafkaProducer = new KafkaProducer<>(producerFactory.getConfigurationProperties());

        kafkaProducer.send(new ProducerRecord<>(TopicConfig.communitySearch, communitySearchRequest), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                log.info("Kafka 전송 성공: {}", metadata);

                log.error("Kafka 전송 중 오류 발생: {}", exception);
                // exception 에 대한 핸들링..
            }
        });
    }
}
