package com.play.java.string;

import java.util.regex.Matcher;

public class RegexTest {
	
	public static void testReplace() {
//		String template = "test $TEST $TEST";

		//String template = "test $CLASS_NAME_HOLDER";
		String template ="--class-name \n$CLASS_NAME_HOLDER\n\n--query\n$SQOOP_QUERY_HOLDER\n\n-m\n1";
//		String replacement = Matcher.quoteReplacement("$TEST");
		String replacement = Matcher.quoteReplacement("$CLASS_NAME_HOLDER");
		String replacement2 = Matcher.quoteReplacement("$SQOOP_QUERY_HOLDER");
		String str = template.replaceAll(replacement, "test").replaceAll(replacement2, "$sqoop");
		System.out.println(str);
		
		
	}
	public static void main(String[] args) {
		String template = "select $FLAG_CONDITION, p.ID, p.PRODUCT_CNAME as cn_name,p.PRODUCT_LIST_PRICE * 100 as list_price,p.product_code,p.PRODUCT_STATUS,p.CREATE_TIME as create_time,c.id as category_id,c.CATEGORY_SEARCH_NAME as category,b.brand_name as brand,decode(b.id, null, 0, b.id) as brand_id,p.name_subtitle,p.product_type,c.is_visible as search_visible,c.mc_site_id as mc_site_id,p.special_type,p.special_tag,p.product_is_gift,p.EAN13,p.product_spell, p.color, p.product_size, p.product_name_prefix  from prod_data2.product p left join prod_data2.Brand b on b.id = p.product_brand_id inner join prod_data2.category c2 on c2.id = p.category_id inner join prod_data2.mc_site_CATEGORY c on c.category_id = p.category_id where c.is_delete=0 and c.is_visible =1 and c.CATEGORY_SEARCH_NAME is not null and p.is_deleted=0 and $CONDITIONS";
		String str = template.replaceAll(Matcher.quoteReplacement("$FLAG_CONDITION,"), "").replaceAll(Matcher.quoteReplacement("$CONDITIONS"), "p.ID in (913, 914, 915)");
		System.out.println(str);
	}
}
