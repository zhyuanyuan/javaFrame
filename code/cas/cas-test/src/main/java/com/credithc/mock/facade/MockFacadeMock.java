package com.credithc.mock.facade;

import org.springframework.stereotype.Service;

import com.credithc.mock.facade.dto.MockReq;
import com.credithc.mock.facade.dto.MockRes;

@Service
public class MockFacadeMock implements MockFacade {

	@Override
	public MockRes mockCall(MockReq req) {
		MockRes r = new MockRes();
		r.setRespCode("mock code");
		r.setRespMsg("mock msg:mockChain is:" + req.getRequestChain());
		return r;
	}

}
