package com.archilog.reservationservice.client;

import com.archilog.reservationservice.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class MemberClient {

    private static final String MEMBER_SERVICE_URL = "http://member-service/api/members";

    private final RestTemplate restTemplate;

    public MemberClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMemberById(Long memberId) {
        try {
            return restTemplate.getForObject(MEMBER_SERVICE_URL + "/" + memberId, Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new BusinessException("Membre introuvable avec l'id : " + memberId);
        }
    }

    public boolean isMemberSuspended(Long memberId) {
        Map<String, Object> member = getMemberById(memberId);
        return Boolean.TRUE.equals(member.get("suspended"));
    }

    public int getMemberMaxBookings(Long memberId) {
        Map<String, Object> member = getMemberById(memberId);
        return (int) member.get("maxConcurrentBookings");
    }
}
