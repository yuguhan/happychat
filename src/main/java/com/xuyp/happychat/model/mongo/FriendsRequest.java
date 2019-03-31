package com.xuyp.happychat.model.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("friendsRequest")
@Builder
@Data
public class FriendsRequest {
    @Id
    private String id;
    private String sendUserId;
    private String acceptUserId;
    private long requestDateTime;
}
