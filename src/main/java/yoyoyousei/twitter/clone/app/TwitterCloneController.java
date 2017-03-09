package yoyoyousei.twitter.clone.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoyoyousei.twitter.clone.domain.model.Tweet;
import yoyoyousei.twitter.clone.domain.model.User;
import yoyoyousei.twitter.clone.domain.service.*;
import yoyoyousei.twitter.clone.domain.service.upload.FileSystemStorageService;
import yoyoyousei.twitter.clone.util.Util;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

//import yoyoyousei.twitter.clone.domain.service.UserService;

/**
 * Created by s-sumi on 2017/02/28.
 */
@Controller
//@SessionAttributes(value = {"userinfo"})
public class TwitterCloneController {

    public static final Logger log = LoggerFactory.getLogger(TwitterCloneController.class);

    private TweetService tweetService;
    private UserService userService;
    private TwitterCloneUserDetailsService userDetailsService;
    private FileSystemStorageService fileSystemStorageService;

    //各フィールドに@autowiredしてもいいが、推奨されてない(nulpoが起きやすくなるらしい)
    @Autowired
    public TwitterCloneController(TweetService tweetService,
                                  UserService userService,
                                  TwitterCloneUserDetailsService userDetailsService,
                                  FileSystemStorageService fileSystemStorageService) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @GetMapping(value = "/")
    String timeline(Principal principal, Model model) {
        model.addAttribute("tweetForm", new TweetForm());    //attribute can be omitted.

        //default attribute name is Classname whose first letter is lower case.
        model.addAttribute("tweets", tweetService.findAllDesc());

        User loginUser = Util.getLoginuserFromPrincipal(principal);
        model.addAttribute("userinfo", loginUser);

        model.addAttribute("recommend", getUnFollowing10Users(loginUser));

        log.info("util.noicon: "+Util.getNoIcon());


        return "timeline";
    }


    @PostMapping(value = "/")
    String tweet(Principal principal, @Validated TweetForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Set<String> err = new HashSet<>();
            bindingResult.getAllErrors().forEach(e -> err.add(e.getDefaultMessage()));
            model.addAttribute("errors", err);
            return timeline(principal, model);
            //return "redirect:/";
        }
        Tweet tweet = new Tweet(form.getContent(),Util.getLoginuserFromPrincipal(principal));

        //tweetService.save(tweet);
        try {
            tweetService.save(tweet);
        } catch (Exception e) {
            Set<String> err = new HashSet<>();
            err.add("an error occured. try again.");
            model.addAttribute("errors", err);
            //return timeline(principal,model);
            return "redirect:/";
        }

        return "redirect:/";
    }

    //register
    @GetMapping(value = "/register")
    String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping(value = "/register")
    String register(@Validated RegisterForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("user:" + form.getUserId());
            log.info("pass:" + form.getPassword());
            log.info("scr:" + form.getScreenName());
            Set<String> err = new HashSet<>();
            bindingResult.getAllErrors().forEach(e -> err.add(e.getDefaultMessage()));
            model.addAttribute("errors", err);
            return "register";
        }

        log.info("user:" + form.getUserId());
        log.info("pass:" + form.getPassword());
        log.info("scr:" + form.getScreenName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(form.getUserId(), encoder.encode(form.getPassword()), form.getScreenName());
        try {
            userService.create(user);
        } catch (UserIdAlreadyExistsException e) {
            Set<String> errors = new HashSet<>();
            errors.add(e.getMessage());
            model.addAttribute("errors", errors);
            return "register";
        } catch (Exception e) {

            Set<String> errors = new HashSet<>();
            errors.add("unexpected error occured. try again.");
            model.addAttribute("errors", errors);

            log.info(e.toString());
            return "register";
        }
        return "redirect:/loginForm";
    }

    @GetMapping("/modify")
    String modifyUserDataPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        //model.addAttribute("uploadForm",new UploadFileForm());
        return "mypage";
    }

    @PostMapping("/modify")
    String modifyUserData(Principal principal, @Validated UserForm form, BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            Set<String> err = new HashSet<>();
            bindingResult.getAllErrors().forEach(e -> err.add(e.getDefaultMessage()));
            model.addAttribute("errors", err);
            //return modifyUserDataPage(model);
            return "mypage"; //多分上と同義
        }

        try {
            User newUser = userService.find(Util.getLoginuserFromPrincipal(principal).getUserId());
            if (!Objects.equals(form.getScreenName(), ""))
                newUser.setScreenName(form.getScreenName());
            if (!Objects.equals(form.getBiography(), ""))
                newUser.setBiography(form.getBiography());
            userService.update(newUser);

            Util.updateAuthenticate(principal, newUser);

            model.addAttribute("userinfo", newUser);
        } catch (UserIdNotFoundException e) {
            Set<String> errors = new HashSet<>();
            errors.add(e.getMessage());
            model.addAttribute("errors", errors);
            return "mypage";
        } catch (Exception e) {
            Set<String> errors = new HashSet<>();
            errors.add("unexpected error occured. try again.");
            model.addAttribute("errors", errors);
            log.info(e.getMessage());
            return "mypage";
        }
        return "redirect:/";
    }

    //TODO: urlにuseridを出さない
    @PostMapping(value = "/follow/{userid}")
    String follow(Principal principal, @PathVariable("userid") String userid, RedirectAttributes attributes){
        User loginUser=Util.getLoginuserFromPrincipal(principal);
        try {
            User target = userService.find(userid);
            loginUser.getFollowing().add(target);
            userService.update(loginUser);  //DBに反映
            Util.updateAuthenticate(principal, loginUser);  //セッション情報を更新
        }catch (Exception e) {
            Set<String> errors = new HashSet<>();
            errors.add("unexpected error occured. try again.");
            attributes.addFlashAttribute("errors", errors);
            log.info(e.getMessage());
        }
        return "redirect:/";
    }


    //------Util--------------------------------------

    @GetMapping("/debug")
    String debug() {
        List<User> users = userService.findAll();
        for (User u : users) {
            log.info(u.toString());
        }
        return "redirect:/";
    }

    List<User> getUnFollowing10Users(User loginUser){
        log.info("loginuser is: " + loginUser.toString());

        List<User> alluser=userService.findAll();
        List<User> following=loginUser.getFollowing();
        List<User> unFollowing10Users=alluser.stream()
                                    .limit(10)
                                    .filter(u->!(following.contains(u) || u.equals(loginUser) ))
                                    .collect(Collectors.toList());
        return unFollowing10Users;
    }
}
