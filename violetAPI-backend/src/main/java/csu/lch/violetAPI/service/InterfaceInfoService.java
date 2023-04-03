package csu.lch.violetAPI.service;

import com.baomidou.mybatisplus.extension.service.IService;
import csu.lch.violetAPI.model.entity.InterfaceInfo;

/**
* @author violetRcl
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-27 18:39:33
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
