package com.t3h.controller;
import com.t3h.model.dto.RoleDto;
import com.t3h.model.dto.UserDto;
import com.t3h.service.IUserService;
import com.t3h.service.impl.UserServiceImpl;
import com.t3h.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "login";
    }

    @GetMapping(value = "/process-after-login")
    public String processAfterLogin(){

        // lấy ra quyền của user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // nếu là role admin
        UserDto userDto= userService.findUserByUsername(username);
        if (CollectionUtils.isEmpty(userDto.getRoleDtos())){
            return "redirect:/logout";
        }
        boolean isAdmin = false;
        boolean isUser = false;
        for (int i = 0; i < userDto.getRoleDtos().size(); i++) {
            RoleDto roleDto = userDto.getRoleDtos().get(i);
            if (Constant.ROLE_ADMIN.equalsIgnoreCase(roleDto.getName())){
                isAdmin=true;
            }
            if (Constant.ROLE_USER.equalsIgnoreCase(roleDto.getName())){
                isUser=true;
            }
        }
        if (isAdmin){
            return "redirect:/admin";
        }
        if (isUser){
            return "redirect:/views/home/index";
        }
        return "redirect:/logout";
    }
}
