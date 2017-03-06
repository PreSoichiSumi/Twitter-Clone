package yoyoyousei.twitter.clone.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoyoyousei.twitter.clone.domain.model.User;
import yoyoyousei.twitter.clone.domain.service.UserIdNotFoundException;
import yoyoyousei.twitter.clone.domain.service.UserService;
import yoyoyousei.twitter.clone.domain.service.upload.StorageFileNotFoundException;
import yoyoyousei.twitter.clone.domain.service.upload.StorageService;
import yoyoyousei.twitter.clone.util.Util;

import java.io.IOError;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by s-sumi on 2017/03/06.
 */
@Controller
@RequestMapping("files")
public class FileUploadController {

    public static final Logger log= LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private UserService userService;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService){this.storageService=storageService;}

    /*@GetMapping("/")
    public String listUploadedFiles(Model model)throws IOException{
        model.addAttribute("files",storageService
            .loadAll().map(path-> MvcUriComponentsBuilder
                                    .fromMethodName(FileUploadController.class,"servFile",path.getFileName().toString()).build().toString())
            .collect(Collectors.toList()));
        return "uploadForm";
    }*/

    @GetMapping("/icon/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file=storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
    @PostMapping("/icon/upload")
    public String handleFileUpload(Principal principal, @RequestParam("icon") MultipartFile file,
                                   RedirectAttributes redirectAttributes){
        String filename=storageService.store(file);

        User user = Util.getUserDataFromPrincipal(principal);
        user.setIconname(filename);
        try{
            userService.update(user);
        }catch (UserIdNotFoundException e){
            Set<String> errors=new HashSet<>();
            errors.add(e.getMessage());
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:/modify";
        }catch (Exception e){
            Set<String> errors=new HashSet<>();
            errors.add("unexpected error occured. try again.");
            log.info(e.getMessage());
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:/modify";
        }

        Util.updateAuthenticate(principal,user);

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
    }

}
