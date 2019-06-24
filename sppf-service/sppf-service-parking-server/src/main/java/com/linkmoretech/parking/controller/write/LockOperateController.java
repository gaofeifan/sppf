package com.linkmoretech.parking.controller.write;

import com.linkmoretech.parking.service.LockOperateService;
import com.linkmoretech.parking.vo.request.LockOperateRequest;
import com.linkmoretech.parking.vo.request.ReqLockIntall;
import com.linkmoretech.parking.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @Author: GFF
 * @Description:
 */
@RestController
@RequestMapping(value = "/lock/operate")
@Slf4j
@Api(tags = "锁操作API", value = "LockOperateController" )
public class LockOperateController {

    @Autowired
    LockOperateService lockOperateService;


    @ApiOperation(value = "操作锁", notes = "控制锁升降")
    @PostMapping(value = "operate")
    public Boolean operate(HttpServletRequest request, @RequestBody LockOperateRequest lockOperate){
        return this.lockOperateService.operate(request,lockOperate);
    }


    @ApiOperation(value = "绑定网关", notes = "绑定网关")
    @GetMapping(value = "/bind-group")
    @ResponseBody
    public Boolean bindGroup(HttpServletRequest request, @ApiParam(value="车区id",required=true) @NotNull(message="车区id不能为空") @RequestParam(value = "preId",required= true) Long preId ,
                                             @ApiParam(value="网关编号",required=true) @NotBlank(message="网关编号不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber
    ){
        Boolean falg = this.lockOperateService.bindGroup(preId,serialNumber,request);
        return falg;
    }

    @ApiOperation(value = "删除(解除)绑定网关", notes = "删除绑定网关")
    @GetMapping(value = "/unbind-group")
    @ResponseBody
    public Boolean unBindGroup(HttpServletRequest request, @ApiParam(value="分组编号",required=true) @NotNull(message="分组编号不能为空") @RequestParam(value = "groupCode",required= true) String groupCode ,
                                               @ApiParam(value="网关编号",required=true) @NotBlank(message="网关编号不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber
    ){
        Boolean falg = this.lockOperateService.unBindGroup(groupCode,serialNumber,request);
        return falg;
    }

    @ApiOperation(value = "删除(解除)网关绑定的锁", notes = "删除(解除)网关绑定的锁")
    @GetMapping(value = "/unbind-lock")
    @ResponseBody
    public Boolean unBindLock(HttpServletRequest request ,
                                              @ApiParam(value="网关编号",required=true) @NotBlank(message="编号不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber ,
                                              @ApiParam(value="锁编号",required=true) @NotBlank(message="锁编号") @RequestParam(value = "lockSn",required= true) String lockSn
    ){
        Boolean falg = this.lockOperateService.removeLock(lockSn,request);
        return falg;
    }

    @ApiOperation(value = "查询网关list", notes = "查询网关list")
    @GetMapping(value = "/find-gateway-group")
    @ResponseBody
    public List<ResGateway> findGatewayGroup(HttpServletRequest request, @ApiParam(value="车区id",required=true) @NotNull(message="车区id不能为空") @RequestParam(value = "preId",required= true) Long preId
    ){
        List<ResGateway> group = this.lockOperateService.findGatewayGroup(preId,request);
        return group;
    }

    @ApiOperation(value = "查询网关(扫一扫)", notes = "查询网关(扫一扫")
    @GetMapping(value = "/find-gateway-serialNumber")
    @ResponseBody
    public com.linkmoretech.parking.vo.response.ResGatewayDetails getGatewayDetails(HttpServletRequest request, @ApiParam(value="网关编号",required=true) @NotNull(message="网关编号不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber
    ){
        com.linkmoretech.parking.vo.response.ResGatewayDetails details = lockOperateService.getGatewayDetails(serialNumber,request);
        return details;
    }

    @ApiOperation(value = "加载锁", notes = "加载锁")
    @GetMapping(value = "/load-lock")
    @ResponseBody
    public Boolean loadAllLock(HttpServletRequest request, @ApiParam(value="网关编号",required=true) @NotNull(message="网关不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber	){
        Boolean falg = lockOperateService.loadLock(request,serialNumber);
        return falg;
    }

    @ApiOperation(value = "重启网关", notes = "重启网关")
    @GetMapping(value = "/restart-gateway")
    @ResponseBody
    public Boolean restartGateway(HttpServletRequest request, @ApiParam(value="网关编号",required=true) @NotNull(message="网关不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber	){
        Boolean falg = lockOperateService.restartGateway(request,serialNumber);
        return falg;
    }
    @ApiOperation(value = "查询锁绑定的网关/未绑定的网关(用于展示批量更新车位锁网关的列表)", notes = "重启网关")
    @GetMapping(value = "/find-lock-gateways")
    @ResponseBody
    public List<ResLockGatewayList> findLockGateways(HttpServletRequest request, @ApiParam(value="锁编号编号",required=true) @NotNull(message="锁编号不能为空") @RequestParam(value = "lockSn",required= true) String lockSn	,
                                                     @ApiParam(value="车区id",required=true) @NotNull(message="车区id不能为空") @RequestParam(value = "preId",required= true) Long preId	){
        List<ResLockGatewayList> gateways = lockOperateService.findLockGateways(request,lockSn,preId);
        return gateways;
    }

    @ApiOperation(value = "更新车位锁绑定的网关(批量更新)", notes = "更新车位锁绑定的网关(批量更新)")
    @PutMapping(value = "/edit-lock-bind-gateway")
    @ResponseBody
    public Boolean editLockBindGateway(HttpServletRequest request, @ApiParam(value="锁编号",required=true) @NotNull(message="锁编号不能为空") @RequestParam(value = "lockSn",required= true) String lockSn
            ,@ApiParam("网关编号 多个网关编号,分隔('网关编号1','网关编号2')") @NotNull(message="网关编号不能为空") @RequestParam(value = "serialNumbers",required= true) String serialNumbers
    ){
        Boolean falg = lockOperateService.editLockBindGateway(request,serialNumbers,lockSn);
        return falg;
    }
    @ApiOperation(value = "确认绑定", notes = "确认绑定")
    @GetMapping(value = "/confirm")
    @ResponseBody
    public Boolean confirm(HttpServletRequest request, @ApiParam(value="网关编号",required=true) @NotNull(message="网关编号不能为空") @RequestParam(value = "serialNumber",required= true) String serialNumber
    ){
        Boolean flag = lockOperateService.confirm(serialNumber,request);
        return flag;
    }


    @ApiOperation(value = "地锁安装2.0.1", notes = "地锁安装")
    @RequestMapping(value = "/v2.0.1/installLock", method = RequestMethod.POST)
    @ResponseBody
    public Boolean installLockTwo(HttpServletRequest request, @Validated @RequestBody ReqLockIntall reqLockIntall) {
            Boolean flag= this.lockOperateService.installLock(reqLockIntall,request);
        return flag;
    }
    @ApiOperation(value = "删除车位锁", notes = "删除车位锁")
    @RequestMapping(value = "/remove-stall-lock", method = RequestMethod.POST)
    @ResponseBody
    public Boolean removeStallLock(HttpServletRequest request, @ApiParam(value="车位id",required=true) @NotNull(message="车位id不能为空") @RequestParam(value = "stallId",required= true) Long stallId ) {
            return this.lockOperateService.removeStallLock(stallId,request);
    }

    @RequestMapping(value="/lock­signal­history",method=RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询车位锁在一定时间内的信号强度", notes = "查询车位锁在一定时间内的信号强度", consumes = "application/json")
    public ResSignalHistory lockSignalHistory(HttpServletRequest request, @ApiParam("车位锁编号") @NotNull(message="sn") @RequestParam("sn") String sn) {
        ResSignalHistory history = this.lockOperateService.lockSignalHistory(request,sn);
        return history ;
    }
}
