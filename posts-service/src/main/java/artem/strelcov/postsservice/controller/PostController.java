package artem.strelcov.postsservice.controller;

import artem.strelcov.postsservice.dto.PostDto;
import artem.strelcov.postsservice.model.ImageModel;
import artem.strelcov.postsservice.model.Post;
import artem.strelcov.postsservice.service.PostService;
import artem.strelcov.postsservice.util.PostSort;
import io.minio.errors.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping
    public ResponseEntity<List<Post>> getAllPostsExceptMy(Principal user) {

        return new ResponseEntity<List<Post>>(postService.getAllPostsExceptMy(user), HttpStatus.OK);
    }
    @GetMapping("/pagination")
    public Page<PostDto> getPostsBySubscriptionsWithPagination(
            HttpServletRequest request,
            Principal user,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(required = false, value = "sort") PostSort sort

    ) {
        return postService.getPostsBySubscriptionsWithPagination(request,user,offset,limit, sort);
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<List<PostDto>>(postService.getPostsByUsername(username), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Integer id,
                                           @RequestPart("post") Post post,
                                           @RequestPart("images") MultipartFile [] images,
                                           Principal user
                                           ) {
        return new ResponseEntity<Post>(postService.updatePost(id,post,images, user), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer id) {
        postService.deletePost(id);
        return new ResponseEntity<String>("Пост успешно удален", HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestPart("post") Post post,
            @RequestPart("images") MultipartFile [] images,
            Principal user){

        Post createdPost = postService.createPost(images,post, user);
        return new ResponseEntity<Post>(createdPost, HttpStatus.CREATED);
    }

}
