package com.nts.connect.pjt3.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.nts.connect.pjt3.dto.Product;
import com.nts.connect.pjt3.mapper.ProductMapper;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {
	@Mock
	private ProductMapper mapper;

	@InjectMocks
	private ProductService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void 카테고리_테스트() {

		//given(categoryMapper.selectCategoriesAll()).willReturn();
		Product temp = new Product();
		temp.setId(1);
		temp.setContent("목테스트");
		temp.setEmail("test@gmail.com");

		when(service.getProductByDisplayInfoId(1)).thenReturn(temp);

		final Product p = service.getProductByDisplayInfoId(1);

		verify(mapper, atLeastOnce()).selectProductWithCommentByDisplayInfotId(Mockito.anyInt());

		assertThat(p.getId(), is(1));
		assertThat(p.getContent(), is("목테스트"));
		assertThat(p.getEmail(), is("test@gmail.com"));
	}

}
