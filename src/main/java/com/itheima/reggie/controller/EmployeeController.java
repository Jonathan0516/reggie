package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @program: reggie_take_out
 * @description: *
 * @return:
 **/
@Controller
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        HttpSession requestSession = request.getSession();
        String username = employee.getUsername();
        String password = employee.getPassword();

        R<Employee> result = employeeService.login(username, password);
        if(result.getCode() != null && result.getCode() == 1){
            requestSession.setAttribute("employee", result.getData());
            log.info("Session set attribute employee: {}", result.getData());

        }
        return result;
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        HttpSession requestSession = request.getSession(false); // 获取当前 Session，但不创建新的
        log.info("Session employee: {}", requestSession != null ? requestSession.getAttribute("employee") : "null");
        if (requestSession != null) {
            requestSession.removeAttribute("employee"); // 移除用户信息
            requestSession.invalidate(); // 销毁整个 Session
        }
        return R.success("Logout successful");
    }
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Employee employee1 = (Employee) request.getSession().getAttribute("employee");
        log.info("Session employee: {}", request.getSession().getAttribute("employee"));

        long emId1 = employee1.getId();
        employee.setCreateUser(emId1);
        employee.setUpdateUser(emId1);
        R<Employee> result = employeeService.saveUser(employee);
        if(result.getCode() != null && result.getCode() == 1){
            return R.success("add successful");
        }else{
            return R.error("add fail");
        }
    }
}
