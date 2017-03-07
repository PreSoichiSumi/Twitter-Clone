package yoyoyousei.twitter.clone.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import yoyoyousei.twitter.clone.app.FileUploadController;
import yoyoyousei.twitter.clone.domain.model.User;
import yoyoyousei.twitter.clone.domain.service.TwitterCloneUserDetails;
import yoyoyousei.twitter.clone.domain.service.upload.FileSystemStorageService;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.security.Principal;

/**
 * Created by s-sumi on 2017/03/02.
 */
//component service repository controllerは基本どれも同じで、
//クラスをDIコンテナにbeanとして登録する
public class Util {
    private FileSystemStorageService tmpService;
    private static String noIcon;

    @Autowired
    public Util(FileSystemStorageService tmpService) {
        this.tmpService = tmpService;
    }

    //このアノテーションが付いたメソッドはDI後に実行される
    @PostConstruct
    public void init(){
        noIcon= MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,"serveFile","noicon").build().toString();
    }



    public static User getUserDataFromPrincipal(Principal principal){
        Authentication authentication=(Authentication)principal;
        TwitterCloneUserDetails userDetails=TwitterCloneUserDetails.class.cast(authentication.getPrincipal());
        return userDetails.getuser();
        /*TwitterCloneUserDetails userDetails=(TwitterCloneUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getuser();*/
    }
    public static void updateAuthenticate(Principal principal, User newUser) {
        Authentication oldAuth= (Authentication) principal;
        Authentication newAuth=new UsernamePasswordAuthenticationToken(new TwitterCloneUserDetails(newUser),oldAuth.getCredentials(),oldAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public static String getNoIcon() {return noIcon;}
}
