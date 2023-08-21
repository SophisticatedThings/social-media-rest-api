package artem.strelcov.subscriptionsservice.controller;

import artem.strelcov.subscriptionsservice.dto.UserDTO;
import artem.strelcov.subscriptionsservice.model.User;
import artem.strelcov.subscriptionsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {

    private final UserService userService;

    @PostMapping("/replicate")
    public void replicateUser(@RequestBody UserDTO userDTO) {
        userService.replicateUser(userDTO.getUsername());

    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }
    @PutMapping("/subscribe/{authorId}")
    public ResponseEntity<String> subscribe(Principal user, @PathVariable Integer authorId) {
        userService.subscribe(user, authorId);
        return new ResponseEntity<String>("вы успешно подписались", HttpStatus.OK);
    }
    @PutMapping("/unsubscribe/{author_id}")
    public ResponseEntity<String> unsubscribe(Principal user,
                                              @PathVariable("author_id") Integer authorId) {
        userService.unsubscribe(user, authorId);
        return new ResponseEntity<String>("Вы успешно отписались", HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Set<User>> getSubscriptions(Principal user) {
        return new ResponseEntity<Set<User>>(userService.getSubscriptions(user), HttpStatus.OK);
    }

    @PutMapping("/send-friendship-request/{author_id}")
    public ResponseEntity<String> sendFriendshipRequest(Principal user, @PathVariable("author_id") Integer authorId) {
        userService.sendFriendshipRequest(user,authorId);
        return new ResponseEntity<String>("Вы отправили заявку в друзья", HttpStatus.OK);

    }
    @GetMapping("/friendship-requests")
    public ResponseEntity<Set<User>> getFriendshipRequests(Principal user) {
        return new ResponseEntity<Set<User>>(userService.getFriendshipRequests(user), HttpStatus.OK);
    }
    @DeleteMapping("/friendships/{friend_id}")
    public ResponseEntity<String> deleteFromFriends(Principal user, @PathVariable("friend_id") Integer friendId) {
        userService.deleteFromFriends(user,friendId);
        return new ResponseEntity<String>("Пользователь успешно удален из списка друзей", HttpStatus.OK);
    }
    @PutMapping("/friendship-requests/{sender_id}")
    public ResponseEntity<Boolean> acceptOrDeclineRequest(Principal user, @RequestParam("acceptRequest") Boolean acceptRequest,
    @PathVariable("sender_id") Integer senderId) {
        return new ResponseEntity<Boolean>(userService.acceptOrDeclineRequest(user,acceptRequest,senderId), HttpStatus.OK);
    }

    @GetMapping("/chat/{companion_id}")
    public ResponseEntity<String> getChat(Principal user, @PathVariable("companion_id") Integer companionId) {
        userService.createChat(user,companionId);
        return new ResponseEntity<String>("Чат успешно создан", HttpStatus.OK);
    }
}