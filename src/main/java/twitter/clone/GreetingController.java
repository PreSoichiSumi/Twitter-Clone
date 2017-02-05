package twitter.clone;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetingController {
    @RequestMapping(value = "/greeting",produces="text/plain;charset=utf-8")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    @RequestMapping(value = "/",produces="text/plain;charset=utf-8")
    public String timeline(){
        return "timeline";
    }

    @RequestMapping(value = "/login",produces="text/plain;charset=utf-8")
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/mypage",produces="text/plain;charset=utf-8")
    public String mypage(){
        return "mypage";
    }
    @RequestMapping(value = "/register",produces="text/plain;charset=utf-8")
    public String register(){
        return "register";
    }

    /*@PostMapping("/tweet")
    public String postTweet(@ModelAttribute Tweet)*/

}