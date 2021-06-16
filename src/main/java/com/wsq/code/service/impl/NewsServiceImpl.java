package com.wsq.code.service.impl;

import com.wsq.code.entity.News;
import com.wsq.code.mapper.NewsMapper;
import com.wsq.code.service.NewsService;
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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

}
