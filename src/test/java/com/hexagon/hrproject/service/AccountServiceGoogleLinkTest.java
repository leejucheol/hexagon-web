package com.hexagon.hrproject.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.hexagon.hrproject.domain.Account;
import com.hexagon.hrproject.mapper.AccountMapper;

public class AccountServiceGoogleLinkTest {

	private static final String SUB = "google-sub-1";
	private static final String EMAIL = "user@example.com";

	// 매퍼 호출을 기록하고 정해 둔 값을 돌려주는 스텁
	private Account bySub;
	private List<Account> byEmail = Collections.emptyList();
	private int updatedRows = 1;
	private final List<String> calls = new ArrayList<>();

	private AccountService service() throws Exception {
		AccountMapper mapper = (AccountMapper) Proxy.newProxyInstance(
				AccountMapper.class.getClassLoader(),
				new Class<?>[] { AccountMapper.class },
				(proxy, method, args) -> {
					calls.add(method.getName());
					switch (method.getName()) {
					case "selectByGoogleSub":
						return bySub;
					case "selectByEmpEmail":
						return byEmail;
					case "updateGoogleSub":
						return updatedRows;
					default:
						throw new UnsupportedOperationException(method.getName());
					}
				});

		AccountService service = new AccountService();
		Field field = AccountService.class.getDeclaredField("accountMapper");
		field.setAccessible(true);
		field.set(service, mapper);
		return service;
	}

	private static Account account(int accountId, String googleSub) {
		Account account = new Account();
		account.setAccountId(accountId);
		account.setGoogleSub(googleSub);
		account.setEnabled(1);
		return account;
	}

	@Test
	public void sub로_찾으면_이메일_조회와_저장_없이_그_계정() throws Exception {
		bySub = account(1, SUB);

		Account linked = service().linkGoogleAccount(SUB, EMAIL, true);

		assertSame(bySub, linked);
		assertFalse(calls.contains("selectByEmpEmail"));
		assertFalse(calls.contains("updateGoogleSub"));
	}

	@Test
	public void 처음_연결하면_sub를_저장하고_계정에_반영한다() throws Exception {
		Account account = account(1, null);
		byEmail = Collections.singletonList(account);

		Account linked = service().linkGoogleAccount(SUB, EMAIL, true);

		assertSame(account, linked);
		assertEquals(SUB, linked.getGoogleSub());
		assertEquals(1, Collections.frequency(calls, "updateGoogleSub"));
	}

	@Test
	public void 저장_직전에_다른_요청이_연결했으면_account_not_linked() throws Exception {
		byEmail = Collections.singletonList(account(1, null));
		updatedRows = 0;

		try {
			service().linkGoogleAccount(SUB, EMAIL, true);
			fail("GoogleLoginException이 나야 한다");
		} catch (GoogleLoginException e) {
			assertEquals(GoogleLoginError.ACCOUNT_NOT_LINKED, e.getError());
		}
	}

	@Test
	public void 판정이_실패하면_사유를_담아_던진다() throws Exception {
		try {
			service().linkGoogleAccount(SUB, EMAIL, true);
			fail("GoogleLoginException이 나야 한다");
		} catch (GoogleLoginException e) {
			assertEquals(GoogleLoginError.ACCOUNT_NOT_LINKED, e.getError());
		}
	}
}
