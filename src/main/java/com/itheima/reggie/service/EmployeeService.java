package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;

/**
 * @program: reggie_take_out
 * @description: *
 * @return:
 **/
public interface EmployeeService extends IService<Employee> {
    public R<Employee> login(String username, String password);

    public R<Employee> saveUser(Employee employee);
}
