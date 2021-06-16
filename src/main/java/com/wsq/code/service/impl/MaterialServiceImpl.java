package com.wsq.code.service.impl;

import com.wsq.code.entity.Material;
import com.wsq.code.mapper.MaterialMapper;
import com.wsq.code.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wsq
 * @since 2021-06-16
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

}
