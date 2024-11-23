package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @program: reggie_take_out
 * @description: *
 * @return:
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R<Employee> login(String username, String password){
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee dbEmployee = employeeMapper.selectOne(queryWrapper);
        if(dbEmployee == null){
            return R.error("Login fail");
        }
        if(!dbEmployee.getPassword().equals(password)){
            return R.error("Password is wrong");
        }
        if(dbEmployee.getStatus() == 0){
            return R.error("Account has been banned");
        }
        return R.success(dbEmployee);
    }

    @Override
    @Transactional
    public R<Employee> saveUser(Employee employee) {
        if(employeeMapper.selectById(employee.getUsername()) != null){
            return R.error("user exist");
        }else{
            saveOrUpdate(employee);
            return R.success(employee);
        }

    }


}
