package com.test.springboot.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.test.springboot.model.Category;
import com.test.springboot.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	public void createCategory(Category category) {
		categoryRepository.save(category);
	}

	public List<Category> listCategory() {
		return categoryRepository.findAll();
	}

	public String updateCategory(Long id, Category category) {
		Optional<Category> category2 = categoryRepository.findById(id);
		if (category2 != null) {
			category = new Category(id, category.getName(), category.getDescription());
			categoryRepository.save(category);
			return "ok";
		} else {
			return "fail";
		}

	}

	public String deleteCategory(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category != null) {
			categoryRepository.deleteById(id);
			return "xóa ok";
		} else {
			return "k tìm thấy id";
		}
	}

	private static final String USER = "thai";
	private static final String SECRET = "hey Mr Thai the secrect length must be at least 256 bits"
			+ " please no reveal!";

	public String login(Category category) {
		String token = null;
		Category category2 = categoryRepository.findByNameAndDescription(category.getName(), category.getDescription());
		if (category2 != null) {
			try {
				JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
				builder.claim(USER, category2);
				builder.expirationTime(generateExpirationDate());
				JWTClaimsSet claimsSet = builder.build();
				SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
				JWSSigner signer = new MACSigner(SECRET.getBytes());
				signedJWT.sign(signer);
				token = signedJWT.serialize();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			return "loi";
		}
		return token;
	}

	public Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + 864000000);
	}

	public Category giaima(String token) throws Exception {
		try {
			JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
			SignedJWT jwsObject = SignedJWT.parse(token);
			if (jwsObject.verify(verifier)) {
				JSONObject jsonObject2 = new JSONObject(jwsObject.getPayload().toJSONObject());
				Category catesssss =  new ObjectMapper().readValue(jsonObject2.getAsString("thai"), Category.class);;
				return catesssss;
			} else {
				System.out.println("khong");
			}
		} catch (Exception e) {
			throw new Exception("Could not extract application id from id token");
		}
		return null;
	}
}
