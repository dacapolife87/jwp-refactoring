package kitchenpos.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import kitchenpos.common.BaseControllerTest;
import kitchenpos.common.TestDataUtil;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;

@DisplayName("MenuRestController 테스트")
class MenuRestControllerTest extends BaseControllerTest {

	@DisplayName("Menu 생성 요청")
	@Test
	void create() throws Exception {
		long expectedId = 7L;
		int price = 20000;
		Long menuGroupId = 1L;
		String name = "후라이드 한마리 + 양념 한마리";

		List<MenuProduct> menuProducts = Arrays.asList(
			TestDataUtil.createMenuProduct(1L, 1),
			TestDataUtil.createMenuProduct(2L, 1)
		);

		Menu menu = TestDataUtil.createMenu(name, price, menuGroupId, menuProducts);

		mockMvc.perform(post("/api/menus")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(menu)))
			.andDo(print())
			.andExpect(header().string("Location", "/api/menus/" + expectedId))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedId))
			.andExpect(jsonPath("$.name").value(name))
			.andExpect(jsonPath("$.price").value(price))
			.andExpect(jsonPath("$.menuGroupId").value(menuGroupId))
			.andExpect(jsonPath("$.menuProducts", Matchers.hasSize(2)));
	}

	@DisplayName("Menu 목록 조회")
	@Test
	void list() throws Exception {
		mockMvc.perform(get("/api/menus"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.hasSize(6)));
	}
}