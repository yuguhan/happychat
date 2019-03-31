package com.xuyp.happychat.friend.controller;

import com.xuyp.happychat.friend.condition.*;
import com.xuyp.happychat.friend.service.IFriendService;
import com.xuyp.happychat.util.datatransfer.JSONController;
import com.xuyp.happychat.util.datatransfer.JSONResponse;
import com.xuyp.happychat.util.logcheck.aop.LogCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/u")
public class FriendController extends JSONController {
    @Autowired
    IFriendService friendService;

    @PostMapping("/search")
    @LogCheck("查询头像")
    public Mono<JSONResponse> searchFriend( SearchFriend searchFriend) {

        return Mono.justOrEmpty(excute(friendService.searchFriend(searchFriend)));

    }

    @PostMapping("/addFriendRequest")
    @LogCheck("添加朋友请求")
    public Mono<JSONResponse> addFriend( AddFriend addFriend) {

        return Mono.justOrEmpty(excute(friendService.addFriend(addFriend)));

    }

    @PostMapping("/queryFriendRequests")
    @LogCheck("查询朋友请求")
    public Mono<JSONResponse> findFriendRequest( FindFriendRequest findFriendRequest) {
        return Mono.justOrEmpty(excute(friendService.findFriendRequest(findFriendRequest)));
    }

    @PostMapping("/operFriendRequest")
    @LogCheck("同意朋友请求")
    public Mono<JSONResponse> operFriendRequest( OperFriendRequest operFriendRequest) {
        return Mono.justOrEmpty(excute(friendService.operFriendRequest(operFriendRequest)));
    }

    @PostMapping("/findUserInfo")
    @LogCheck("获取用户信息")
    public Mono<JSONResponse> findUserInfo( FindUserInfo findUserInfo) {
        return Mono.justOrEmpty(excute(friendService.findUserInfo(findUserInfo)));
    }

    @PostMapping("/myFriends")
    @LogCheck("我的朋友列表")
    public Mono<JSONResponse> myFriends( MyFriends myFriends) {
        return Mono.justOrEmpty(excute(friendService.findFriends(myFriends)));
    }

    @PostMapping("/getUnReadMsgList")
    @LogCheck("未读消息获取")
    public Mono<JSONResponse> getUnReadMsgList(String  acceptUserId) {
        return Mono.justOrEmpty(excute(friendService.getUnReadMsgList(acceptUserId)));
    }

}
