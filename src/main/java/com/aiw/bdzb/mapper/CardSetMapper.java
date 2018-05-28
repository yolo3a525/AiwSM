package com.aiw.bdzb.mapper;

import com.aiw.bdzb.entity.CardSet;
import com.aiw.mapper.BaseMapper;

/** 
 * 说明：bdzb.cardset
 * 创建人：aiw
 * 创建时间：2017-10-19
 */

public interface CardSetMapper extends BaseMapper<CardSet> {

	
    public CardSet getCardSetByVipLevel(Integer vip);
    
}
