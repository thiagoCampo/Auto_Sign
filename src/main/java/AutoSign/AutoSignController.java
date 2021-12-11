package AutoSign;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AutoSignController {
    /**GetMapping associates the URL / to the method home()**/
    @GetMapping("/" )
    public String Main(){
        /** returning the logical name of the html to be viewed, here home.html**/
        return "home";
    }

    @PostMapping("/")
    /** Model is an object that takes data from the controller to the view page **/
    /**Attributes that are placed in the model will be copied into the servlet response attributes**/
    public String URLsubmit(@ModelAttribute HomeView home, Model model) throws JSONException {
        model.addAttribute("home", home);
        home.sendURL(home.getURL());
        System.out.println(home.getURL());
        return "result";
    }


    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/history")
    public String History(){
        return "history";
    }

    @GetMapping("/contactus")
    public String ContactUs(){
        return "contactus";
    }

    @GetMapping("/aboutus")
    public String AboutUs(){
        return "aboutus";
    }
    }





