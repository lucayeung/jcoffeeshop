package com.luca.jcoffeeshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luca.jcoffeeshop.api.ProductController;
import com.luca.jcoffeeshop.biz.ProductService;
import com.luca.jcoffeeshop.dto.MenuCategoryDTO;
import com.luca.jcoffeeshop.dto.MenuDTO;
import com.luca.jcoffeeshop.dto.ProductDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import com.luca.jcoffeeshop.util.IdUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class JcoffeeshopApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(name = "standardProductService")
	private ProductService productService;

	@Test
	void testProductMenu() throws Exception {
		List<MenuCategoryDTO> menuCategories = new ArrayList<>();
		String categoryId = IdUtils.shortUUID();
		ProductDTO product1 = ProductDTO
				.builder()
				.productId(IdUtils.shortUUID())
				.categoryId(categoryId)
				.name("可乐")
				.price(BigDecimal.valueOf(2.50))
				.stock(200)
				.createTime(new Date())
				.updateTime(new Date())
				.imgUrls(Arrays.asList("https://picsum.photos/200/300", "https://picsum.photos/200/300"))
				.build();
		ProductDTO product2 = ProductDTO
				.builder()
				.productId(IdUtils.shortUUID())
				.categoryId(categoryId)
				.name("雪碧")
				.price(BigDecimal.valueOf(3.50))
				.stock(210)
				.createTime(new Date())
				.updateTime(new Date())
				.imgUrls(Arrays.asList("https://picsum.photos/200/300"))
				.build();
		MenuCategoryDTO menuCategory = MenuCategoryDTO
				.builder()
				.categoryId(categoryId)
				.name("饮品🧃")
				.description("可乐、学霸、芬达应有尽有哦～")
				.createTime(new Date())
				.products(Arrays.asList(product1, product2))
				.build();
		menuCategories.add(menuCategory);
		long count = menuCategories
				.stream()
				.map(MenuCategoryDTO::getProducts)
				.count();
		Integer productTotal = Math.toIntExact(count);
		MenuDTO menu = MenuDTO
				.builder()
				.categories(menuCategories)
				.total(productTotal)
				.build();
		ResponseResult result = new ResponseResult("成功", 200, menu);
		ObjectMapper objectMapper = new ObjectMapper();
		String expect = objectMapper.writeValueAsString(result);

		when(productService.menu(anyInt(), anyInt(), any())).thenReturn(menu);

		this.mockMvc.perform(get("/product/menu"))
				.andExpect(status().isOk());
				// FIXMEMockMvc响应内容编码默认为ISO-8859-1
				// https://github.com/spring-projects/spring-framework/issues/23219
//				.andExpect(content().json(expect));
	}
}
