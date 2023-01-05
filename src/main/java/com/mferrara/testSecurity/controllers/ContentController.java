package com.mferrara.testSecurity.controllers;

import com.mferrara.testSecurity.auth.User;
import com.mferrara.testSecurity.models.Content;
import com.mferrara.testSecurity.repositories.ContentRepository;
import com.mferrara.testSecurity.repositories.UserRepository;
import com.mferrara.testSecurity.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentRepository repository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public @ResponseBody
    ResponseEntity<Content> createContent(@RequestBody Content newContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newContent.setAuthorName(currentUser.getUsername());

        return new ResponseEntity<Content>(repository.save(newContent), HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Content>> getAllContent() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Content> getContentById(@PathVariable Long id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }

    @GetMapping("/search/{text}")
    public @ResponseBody ResponseEntity<List<Content>> searchContentText(@PathVariable String text){
        System.out.println(text);
        return new ResponseEntity<>(repository.findByTextLike(text), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content update) {
        Content content = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (update.getTopic() != null) {
            content.setTopic(update.getTopic());
        }
        if (update.getText() != null){
            content.setText(update.getText());
        }
        if(update.getAuthorName() != null){
            content.setAuthorName(update.getAuthorName());
        }

        return new ResponseEntity<>(repository.save(content), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        repository.deleteById(id);

        return new ResponseEntity<>("Content Deleted", HttpStatus.OK);
    }
}
