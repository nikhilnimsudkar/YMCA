/**
 * @author gpatwa
 * Fusion Item writer template 
 */
package com.serene.job.writer;

import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class FusionItemWriter extends AbstractFusionItemWriter implements ItemWriter<Map<String,Object>> {

}
